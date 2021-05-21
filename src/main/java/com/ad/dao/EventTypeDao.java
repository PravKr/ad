package com.ad.dao;

import com.ad.models.BaseEntity;
import com.ad.models.DigitalAsset;
import com.ad.models.EventType;
import org.jdom.Document;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("EventTypeDao")
public class EventTypeDao extends EntitiesDao {

    public void convertXMLtoJSON(String inResponse, Map<String, List<BaseEntity>> inEntityMap) {
        List<BaseEntity> digitalAssetList = new ArrayList<>();
        try {
            Document document = xmlUtil1.stringToXmlDocument(inResponse);
            if(document != null) {
                Element element = document.getRootElement();
                List<Element> elements = element.getChildren("event-type");
                int elementIndex = 0;
                for (Element ele: elements) {
                    EventType eventType = new EventType();
                    eventType.setGkey(++elementIndex);
                    eventType.setId(ele.getAttributeValue("id"));
                    eventType.setAppliesTo(ele.getAttributeValue("applies-to"));
                    eventType.setDescription(ele.getAttributeValue("description"));
                    digitalAssetList.add(eventType);
                    String xmlFile = controllerr.ENTITY_XML_DIR + File.separator + ENTITY_NAME + File.separator + elementIndex + XML_EXTENSION;
                    saveDataToFS(xmlFile, xmlUtil1.convertToString(ele, true), Boolean.FALSE);
                    String jsonFile = controllerr.ENTITY_JSON_DIR + File.separator + ENTITY_NAME + File.separator + elementIndex + JSON_EXTENSION;
                    saveDataToFS(jsonFile, eventType, Boolean.FALSE);
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
        List<String> allFiles = getAllFileNames(jsonFile);
        List<BaseEntity> records = new ArrayList<>();
        for(String file: allFiles) {
            EventType eventType = getDataFromFS(jsonFile + File.separator + file, EventType.class);
            records.add(eventType);
        }
        return records;
    }

    public List<BaseEntity> allRecordsFromEntityByWildcardChar(String wildcardString){
        String jsonFile = controllerr.ENTITY_JSON_DIR + File.separator + ENTITY_NAME;
        List<String> allFiles = getAllFileNames(jsonFile);
        List<BaseEntity> records = new ArrayList<>();
        for(String file: allFiles) {
            EventType eventType = getDataFromFS(jsonFile + File.separator + file, EventType.class);
            if(isMatch(eventType.getId(), wildcardString)) {
                records.add(eventType);
            }
        }
        return records;
    }

    private static final String ENTITY_NAME = "EventType";
    private static final Logger LOGGER = LoggerFactory.getLogger(EventTypeDao.class);
}
