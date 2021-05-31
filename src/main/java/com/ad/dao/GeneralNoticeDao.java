package com.ad.dao;

import com.ad.constants.CommonConstants;
import com.ad.models.BaseEntity;
import com.ad.models.EventType;
import com.ad.models.GeneralNotice;
import org.jdom.Document;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("NoticeRequestDao")
public class GeneralNoticeDao extends EntitiesDao {

    public void convertXMLtoJSON(String inResponse, Map<String, List<BaseEntity>> inEntityMap) {
        List<BaseEntity> digitalAssetList = new ArrayList<>();
        try {
            Document document = xmlUtil1.stringToXmlDocument(inResponse);
            if(document != null) {
                Element element = document.getRootElement();
                List<Element> elements = element.getChildren("notice-request");
                int elementIndex = 0;
                for (Element ele: elements) {
                    GeneralNotice generalNotice = new GeneralNotice();
                    generalNotice.setGkey(++elementIndex);
                    generalNotice.setAction(ele.getAttributeValue("action"));
                    generalNotice.setBusinessEntity(ele.getAttributeValue("business-entity"));
                    generalNotice.setEventType(ele.getAttributeValue("event-type"));
                    digitalAssetList.add(generalNotice);
                    String xmlFile = controllerr.ENTITY_XML_DIR + File.separator + ENTITY_NAME + File.separator + elementIndex + CommonConstants.XML_EXTENSION;
                    saveDataToFS(xmlFile, xmlUtil1.convertToString(ele, true), Boolean.FALSE);
                    String jsonFile = controllerr.ENTITY_JSON_DIR + File.separator + ENTITY_NAME + File.separator + elementIndex + CommonConstants.JSON_EXTENSION;
                    saveDataToFS(jsonFile, generalNotice, Boolean.FALSE);
                }
            }
        } catch (Exception e1) {
            LOGGER.error("Error generating export report map" + e1.getMessage());
            e1.printStackTrace();
        }
        inEntityMap.put(ENTITY_NAME, digitalAssetList);
    }

    public List<BaseEntity> allRecordsFromEntity(){
        String jsonFile = controllerr.ENTITY_JSON_DIR + File.separator + ENTITY_NAME;
        List<String> allFiles = getAllFileNames(jsonFile, CommonConstants.JSON_EXTENSION);
        List<BaseEntity> records = new ArrayList<>();
        for(String file: allFiles) {
            GeneralNotice generalNotice = getDataFromFS(jsonFile + File.separator + file, GeneralNotice.class);
            records.add(generalNotice);
        }
        return records;
    }

    public List<BaseEntity> allRecordsFromEntityByWildcardChar(String wildcardString){
        String jsonFile = controllerr.ENTITY_JSON_DIR + File.separator + ENTITY_NAME;
        List<String> allFiles = getAllFileNames(jsonFile, CommonConstants.JSON_EXTENSION);
        List<BaseEntity> records = new ArrayList<>();
        for(String file: allFiles) {
            GeneralNotice generalNotice = getDataFromFS(jsonFile + File.separator + file, GeneralNotice.class);
            if(isMatch(generalNotice.getAction(), wildcardString)) {
                records.add(generalNotice);
            }
        }
        return records;
    }

    private static final String ENTITY_NAME = "GeneralNotice";
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneralNoticeDao.class);
}
