package com.ad.request.handler;

import com.ad.constants.CommonConstants;
import com.ad.models.Argo;
import com.ad.util.FileUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/*
 * SoapCallHandler
 *
 * @author <a href="mailto:pravin.kumar@navis.com">Pravin Kumar</a>
 *
 * Date: 15-06-2020
 *
 * Called from: About Button
 *
 * Description: This will control all Soap api call
 *
 */
@Component
@PropertySource("classpath:groovy-extension.txt")
public class SoapCallHandler {

    @Autowired
    FileUtil fileUtil;

    /**
     * To initilize all the required required n4DetailsProp
     * @param inArgo
     */
    public void initilizeTopology(Argo inArgo) {
        operator = inArgo.getOperator();
        complex = inArgo.getComplex();
        facility = inArgo.getFacility();
        yard = inArgo.getYard();

        endpoint = inArgo.getEndPoint();
        username = inArgo.getUsername();
        password = inArgo.getPassword();
        try {
            soapResponseDom = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            logger.error("Parser Configuration Exception while creating DocumentBuilder: " + e);
        }
    }

    /**
     * To execute Soap Call
     * @param soapMap
     * @return
     */
    public String ping(Map<String,String> soapMap) {
        readSoapXMLFile("classpath:ping.txt");
        substituteVariables(soapMap);
        prepareSoapRequest();
        postSoapMessage();
        return extractResponse();
    }

    /**
     * To execute Soap Call
     * @param soapMap
     * @return
     */
    public String executeSoapCall(Map<String,String> soapMap) {
        String errorMsg = readSoapXMLFile("classpath:groovy-extension.txt");
        if(errorMsg != null) {
            return CommonConstants.ERROR_START_WITH + errorMsg;
        }

        errorMsg = substituteVariables(soapMap);
        if(errorMsg != null) {
            return CommonConstants.ERROR_START_WITH + errorMsg;
        }

        errorMsg = prepareSoapRequest();
        if(errorMsg != null) {
            return CommonConstants.ERROR_START_WITH + errorMsg;
        }

        errorMsg = postSoapMessage();
        if(errorMsg != null) {
            return CommonConstants.ERROR_START_WITH + errorMsg;
        }

        return extractResponse();
    }

    /**
     * To read Soap xml
     */
    private String readSoapXMLFile(String inSoapFormat) {
        //Read xml file
        File file = fileUtil.getFileFromResource(inSoapFormat);
        if (file != null) {
            try {
                soapRequestFileContent = FileUtils.readFileToString(file);
            }catch(IOException ex){
                logger.warn(ex.getMessage(), ex);
                return ex.getMessage();
            }
        } else {
            logger.debug("argoservice soap request file not found in resource ");
            return "argoservice soap request file not found in resource ";
        }
        return null;
    }

    /**
     * To substitute variables
     * @param variables
     */
    private String substituteVariables(Map<String,String> variables) {
        //Substitute values for variables
        for(Map.Entry<String,String> entry : variables.entrySet()) {
            try{
                soapRequestFileContent = soapRequestFileContent.replaceAll("#" + entry.getKey().toString() + "#", entry.getValue());
            } catch(Exception e) {
                logger.debug("There was a proble while substitute");
                e.printStackTrace();
                return "There was a proble while substitute";
            }
        }

        soapRequestFileContent = soapRequestFileContent.replaceAll("#operator#", operator);
        soapRequestFileContent = soapRequestFileContent.replaceAll("#complex#", complex);
        soapRequestFileContent = soapRequestFileContent.replaceAll("#facility#", facility);
        soapRequestFileContent = soapRequestFileContent.replaceAll("#yard#", yard);

        return null;
    }

    /**
     * To prepare soap request
     */
    private String prepareSoapRequest()
    {
        try {
            MessageFactory factory = MessageFactory.newInstance();
            soapMessage = factory
                    .createMessage(
                            new MimeHeaders(),
                            new ByteArrayInputStream(soapRequestFileContent.getBytes(StandardCharsets.UTF_8)));
            ByteArrayOutputStream requestBytes = new ByteArrayOutputStream();
            soapMessage.writeTo(requestBytes);
            logger.debug("Soap Request: " + requestBytes);
        }catch(SOAPException soex){
            logger.warn("Soap Exception occurred while preparing request: " + soex);
            return soex.getMessage();
        }catch(IOException iex){
            logger.warn("IO Exception occurred while preparing request: " + iex);
            return iex.getMessage();
        }

        String authorization = new sun.misc.BASE64Encoder().encode((username+":"+password).getBytes());
        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("Authorization", "Basic " + authorization);
        return null;
    }

    /**
     * To post soap message
     */
    private String postSoapMessage() {
        //Post message
        try {
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();
            String url = endpoint;
            soapResponse = soapConnection.call(soapMessage, url);
            soapConnection.close();
        } catch(SOAPException soex){
            logger.warn("Soap Exception occurred while posting request: " + soex);
            return soex.getMessage();
        } catch (Exception e) {
            logger.warn("Soap Exception occurred while posting request: " + e.getMessage());
            return e.getMessage();
        }
        return null;
    }

    /**
     * To extract the response
     * @return
     */
    private String extractResponse() {
        ByteArrayOutputStream byteResponse = new ByteArrayOutputStream();
        try {
            soapResponse.writeTo(byteResponse);
            logger.debug("Response from Soap: " + byteResponse);
            try {
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(new String(byteResponse.toByteArray())));
                Document doc = soapResponseDom.parse(is);
                //Check for credential failure
                if (doc.getElementsByTagName("faultstring").getLength() > 0) {
                    String errorMessage = doc.getElementsByTagName("faultstring").item(0).getFirstChild().getNodeValue();
                    logger.error("Error processing QC job. Message returned by server: " + errorMessage);
                    return null;
                }

                //Check for errors thrown at message level
                int messageNodes = doc.getElementsByTagName("ns1:SeverityLevel").getLength();
                if (messageNodes > 0) {
                    String errorMessage = "";
                    boolean error = false;
                    for (int i = 0; i < messageNodes; i++) {
                        String severityLevel = doc.getElementsByTagName("ns1:SeverityLevel").item(i).getFirstChild().getNodeValue();
                        if (severityLevel.equals("SEVERE")) {
                            errorMessage += doc.getElementsByTagName("ns1:Message").item(i).getFirstChild().getNodeValue() + " ";
                            error = true;
                        }
                    }
                    if (error) {
                        logger.error("Error processing job. Message returned by server: " + errorMessage);
                        return null;
                    }
                }

                //Parse payload
                if (doc.getElementsByTagName("ns1:Result").getLength() > 0) {
                    String payload = doc.getElementsByTagName("ns1:Result").item(0).getFirstChild().getNodeValue();
                    return payload;
                }
            } catch (SAXException saex) {
                logger.warn("SAX Exception occurred while preparing request: " + saex);
            }
        } catch (SOAPException e) {
            e.printStackTrace();
            return CommonConstants.ERROR_START_WITH + e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            return CommonConstants.ERROR_START_WITH + e.getMessage();
        }
        return null;
    }


    private String soapRequestFileContent;
    private String operator;
    private String complex;
    private String facility;
    private String yard;
    private String endpoint;
    private String username;
    private String password;
    private SOAPMessage soapMessage;
    private SOAPMessage soapResponse;
    private DocumentBuilder soapResponseDom;
    private final Logger logger = LoggerFactory.getLogger(SoapCallHandler.class);
}
