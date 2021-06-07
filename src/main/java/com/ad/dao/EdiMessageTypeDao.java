package com.ad.dao;

import com.ad.constants.CommonConstants;
import com.ad.models.BaseEntity;
import com.ad.models.DigitalAsset;
import com.ad.models.EdiMessageType;
import org.jdom.Document;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("EdiMessageTypeDao")
public class EdiMessageTypeDao extends EntitiesDao {

    public void convertXMLtoJSON(String inResponse, Map<String, List<BaseEntity>> inEntityMap) {
        List<BaseEntity> ediMessageTypeList = new ArrayList<>();
        try {
            Document document = xmlUtil1.stringToXmlDocument(inResponse);
            if(document != null) {
                Element element = document.getRootElement();
                List<Element> elements = element.getChildren("edi-message-type");
                if(elements.size() == 0) {
                    return;
                }
                int elementIndex = 0;
                for (Element ele: elements) {
                    EdiMessageType ediMessageType = new EdiMessageType();
                    ediMessageType.setGkey(++elementIndex);
                    ediMessageType.setId(ele.getAttributeValue("id"));
                    ediMessageType.setClasss(ele.getAttributeValue("class"));
                    ediMessageType.setVersion(ele.getAttributeValue("version"));
                    ediMessageType.setReleaseNbr(ele.getAttributeValue("release-nbr"));
                    ediMessageType.setStandard(ele.getAttributeValue("standard"));
                    ediMessageTypeList.add(ediMessageType);
                    String xmlFile = controllerr.ENTITY_XML_DIR + File.separator + ENTITY_NAME + File.separator + elementIndex + CommonConstants.XML_EXTENSION;
                    saveDataToFS(xmlFile, xmlUtil1.convertToString(ele, true), Boolean.FALSE);
                    String jsonFile = controllerr.ENTITY_JSON_DIR + File.separator + ENTITY_NAME + File.separator + elementIndex + CommonConstants.JSON_EXTENSION;
                    saveDataToFS(jsonFile, ediMessageType, Boolean.FALSE);
                }
            }
        } catch (Exception e1) {
            LOGGER.error("Error generating export report map" + e1.getMessage());
            e1.printStackTrace();
        }
        inEntityMap.put(ENTITY_NAME, ediMessageTypeList);
    }

    public List<BaseEntity> allRecordsFromEntity(){
        String jsonFile = controllerr.ENTITY_JSON_DIR + File.separator + ENTITY_NAME;
        List<String> allFiles = getAllFileNames(jsonFile, CommonConstants.JSON_EXTENSION);
        List<BaseEntity> records = new ArrayList<>();
        for(String file: allFiles) {
            EdiMessageType digitalAsset = getDataFromFS(jsonFile + File.separator + file, EdiMessageType.class);
            records.add(digitalAsset);
        }
        return records;
    }

    public List<BaseEntity> allRecordsFromEntityByWildcardChar(String wildcardString){
        String jsonFile = controllerr.ENTITY_JSON_DIR + File.separator + ENTITY_NAME;
        List<String> allFiles = getAllFileNames(jsonFile, CommonConstants.JSON_EXTENSION);
        List<BaseEntity> records = new ArrayList<>();
        for(String file: allFiles) {
            EdiMessageType digitalAsset = getDataFromFS(jsonFile + File.separator + file, EdiMessageType.class);
            if(isMatch(digitalAsset.getId(), wildcardString)) {
                records.add(digitalAsset);
            }
        }
        return records;
    }

    private static final String ENTITY_NAME = "EdiMessageType";
    private static final Logger LOGGER = LoggerFactory.getLogger(EdiMessageTypeDao.class);
}
