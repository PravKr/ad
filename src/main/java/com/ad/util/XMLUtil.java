package com.ad.util;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jdom.*;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.transform.JDOMSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.List;

/*
 * XMLUtil
 *
 * @author <a href="mailto:pravin.kumar@navis.com">Pravin Kumar</a>
 * Date: 15-06-2020
 *
 * Called from: Various classes
 *
 * Description: This will control XML handling relate stuff
 *
 */
@Component
public class XMLUtil {

    /**
     * Convert Document to String
     * @param doc
     * @return String
     */
    public String convertDocumentToString(Document doc) {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = tf.newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new JDOMSource(doc), new StreamResult(writer));
            String output = writer.getBuffer().toString();
            return output;
        } catch (TransformerException e) {
            LOGGER.error("Error while converting DOM to XML");
        }
        return null;
    }

    /**
     * Convert Document to String
     * @param inNode
     * @return String
     *//*
    public String convertNodeToString(Node inNode) {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = tf.newTransformer();
            StringWriter writer = new StringWriter();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.transform(new DOMSource(inNode), new StreamResult(writer));
            String output = writer.getBuffer().toString();
            return output;
        } catch (TransformerException e) {
            LOGGER.error("Error while converting Node to XML");
        }
        return null;
    }*/

    /**
     * Parses an xml String into a jdom Document
     *
     * @param inString xml text
     * @return document, null if errors
     */
    public static Document stringToXmlDocument(String inString) {
        Document xmlDocument = null;
        SAXBuilder builder = new SAXBuilder();
        try {
            xmlDocument = builder.build(new StringReader(inString));
        }
        catch (IOException ioe) {
            LOGGER.error(ExceptionUtils.getStackTrace(ioe));
        }
        catch (JDOMException jdom) {
            LOGGER.error(ExceptionUtils.getStackTrace(jdom));
        }
        return xmlDocument;
    }


    /**
     * Convert String to Document
     * @param inXmlString
     * @return Document
     *//*
    public Document stringToXmlDocument(String inXmlString) {
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            return docBuilder.parse(new InputSource(new StringReader(inXmlString)));
        } catch (ParserConfigurationException | SAXException | IOException e1) {
            LOGGER.error("Error while parsing String to Document");
            return null;
        }
    }*/

    /**
     * Convert an xml element to a string
     *
     * @param inElement   the JDom Element to be converted
     * @param inFormatted true  - the string representation will be indented and have linefeeds false - the string representation will have no
     *                    formatting constants PLAIN and FORMATTED can be used
     * @return the string representation of the the element
     */
    //2008-03-22 kramu v1.5.G ARGO-10546 set the default encoding as UTF-8; takes care of Korean characters
    @Nullable
    public String convertToString(Element inElement, boolean inFormatted) {
        XMLOutputter xout;
        if (inFormatted) {
            xout = new XMLOutputter(Format.getPrettyFormat());
        } else {
            Format format = Format.getRawFormat();
            format.setEncoding(DEFAULT_ENCODING);
            xout = new XMLOutputter(format);
        }
        if (inElement == null) {
            return null;
        } else {
            return xout.outputString(inElement);
        }
    }

    public Content convertToElement(String inputString) {
        try {
            StringReader stringReader = new StringReader(inputString);
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(stringReader);
            Element ele = doc.getRootElement();
            return ele.detach();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Ensures that an xml string starts with the xml header
     *
     * @param inString content
     * @return content with header if needed
     */
    public static String ensureHeader(String inString) {
        if (inString != null && !inString.startsWith("?xml ")) {
            return XML_HEADER + inString;
        } else {
            return inString;
        }
    }

    /**
     * Export the SNX from spcific entity exporter
     * @return String
     */
    public String convertListToSNX(List<String> inEntityItem) {
        Element root = createRootElement(E_ROOT, SNX_SCHEMA_URL);

        for(String entity: inEntityItem) {
            root.addContent(convertToElement(entity));
        }

        String exportedXML = null;
        if (root != null) {
            exportedXML = convertToString(root, true);
        }

        return ensureHeader(exportedXML);
    }

    /**
     *
     * @param inRootTag root tag
     * @param inSchemaUrl schema url
     * @return root xml element
     */
    public static Element createRootElement(String inRootTag, String inSchemaUrl) {
        Element root = new Element(inRootTag);

        root.addNamespaceDeclaration(XSI_NAMESPACE);
        root.setNamespace(ARGO_NAMESPACE);
        root.setAttribute(new Attribute(SCHEMA_LOCATION, inSchemaUrl, XSI_NAMESPACE));

        return root;
    }

    public static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    String SNX_NAMESPACE_URI = "http://www.navis.com/argo";
    String E_ROOT = "snx";
    String SNX_SCHEMA_URL = SNX_NAMESPACE_URI + " snx.xsd";
    public static final Namespace XSI_NAMESPACE = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
    public static final Namespace ARGO_NAMESPACE = Namespace.getNamespace("argo", "http://www.navis.com/argo");
    public static final String SCHEMA_LOCATION = "schemaLocation";
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final Logger LOGGER = LoggerFactory.getLogger(XMLUtil.class);
}
