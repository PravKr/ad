package com.ad.dao;

import com.ad.constants.CommonConstants;
import com.ad.models.BaseEntity;
import com.ad.models.ExtensionTrigger;
import org.jdom.Document;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;

@Component("ExtensionInjectionDao")
public class ExtensionTriggerDao extends EntitiesDao {

    public void convertXMLtoJSON(String inResponse, Map<String, List<BaseEntity>> inEntityMap) {
        List<BaseEntity> extensionTriggerList = new ArrayList<>();
        try {
            Document document = xmlUtil1.stringToXmlDocument(inResponse);
            if(document != null) {
                Element element = document.getRootElement();
                List<Element> elements = element.getChildren("extension-injection");
                if(elements.size() == 0) {
                    return;
                }
                int elementIndex = 0;
                for (Element ele: elements) {
                    ExtensionTrigger extensionTrigger = new ExtensionTrigger();
                    extensionTrigger.setGkey(++elementIndex);
                    extensionTrigger.setEntityName(ele.getAttributeValue("entity-name"));
                    extensionTrigger.setType(ele.getAttributeValue("type"));
                    extensionTrigger.setScope(ele.getAttributeValue("scope"));
                    extensionTriggerList.add(extensionTrigger);
                    String xmlFile = controllerr.ENTITY_XML_DIR + File.separator + ENTITY_NAME + File.separator + elementIndex + CommonConstants.XML_EXTENSION;
                    saveDataToFS(xmlFile, xmlUtil1.convertToString(ele, true), Boolean.FALSE);
                    String jsonFile = controllerr.ENTITY_JSON_DIR + File.separator + ENTITY_NAME + File.separator + elementIndex + CommonConstants.JSON_EXTENSION;
                    saveDataToFS(jsonFile, extensionTrigger, Boolean.FALSE);
                }
            }
        } catch (Exception e1) {
            LOGGER.error("Error generating export report map" + e1.getMessage());
            e1.printStackTrace();
        }
        inEntityMap.put(ENTITY_NAME, extensionTriggerList);
    }

    public List<BaseEntity> allRecordsFromEntity(){
        String jsonFile = controllerr.ENTITY_JSON_DIR + File.separator + ENTITY_NAME;
        List<String> allFiles = getAllFileNames(jsonFile, CommonConstants.JSON_EXTENSION);
        List<BaseEntity> records = new ArrayList<>();
        for(String file: allFiles) {
            ExtensionTrigger extensionTrigger = getDataFromFS(jsonFile + File.separator + file, ExtensionTrigger.class);
            records.add(extensionTrigger);
        }
        return records;
    }

    public List<BaseEntity> allRecordsFromEntityByWildcardChar(String wildcardString){
        String jsonFile = controllerr.ENTITY_JSON_DIR + File.separator + ENTITY_NAME;
        List<String> allFiles = getAllFileNames(jsonFile, CommonConstants.JSON_EXTENSION);
        List<BaseEntity> records = new ArrayList<>();
        for(String file: allFiles) {
            ExtensionTrigger extensionTrigger = getDataFromFS(jsonFile + File.separator + file, ExtensionTrigger.class);
            if(isMatch(extensionTrigger.getEntityName(), wildcardString)) {
                records.add(extensionTrigger);
            }
        }
        return records;
    }

    private static final String ENTITY_NAME = "ExtensionTrigger";
    private static final Logger LOGGER = LoggerFactory.getLogger(ExtensionTriggerDao.class);
}
