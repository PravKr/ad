package com.ad.dao;

import com.ad.constants.CommonConstants;
import com.ad.models.BaseEntity;
import com.ad.models.DatabaseVariform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.*;

@Component("ExtensionTriggerDao")
public class ExtensionTriggerDao extends EntitiesDao {

    public void convertXMLtoJSON(String inResponse, Map<String, List<BaseEntity>> inEntityMap) {
        List<BaseEntity> extensionList = new ArrayList<>();
        try {
            /*Document document = xmlUtil1.stringToXmlDocument(inResponse);
            if(document != null) {
                NodeList nodeList = document.getElementsByTagName("extension");
                if(nodeList == null) {
                    //return null;
                }
                for (int elementIndex = 0; elementIndex < nodeList.getLength(); elementIndex++ ) {
                    *//*Element ele = (Element) nodeList.item(elementIndex);
                    Extension extension = new Extension();
                    extension.setContents(ele.getAttribute("contents"));
                    extension.setName(ele.getAttribute("name"));
                    extension.setScope(ele.getAttribute("scope"));
                    extension.setType(ele.getAttribute("type"));
                    extension.setLanguage(ele.getAttribute("language"));
                    extension.setIsEnabled(ele.getAttribute("is-enabled"));
                    extensionList.add(extension);*//*
                }
            }*/
        } catch (Exception e1) {
            LOGGER.error("Error generating export report map" + e1.getMessage());
            e1.printStackTrace();
        }
        inEntityMap.put("Code Extension", extensionList);
    }

    public List<BaseEntity> allRecordsFromEntity(){
        String jsonFile = controllerr.ENTITY_JSON_DIR + File.separator + ENTITY_NAME;
        List<String> allFiles = getAllFileNames(jsonFile, CommonConstants.JSON_EXTENSION);
        List<BaseEntity> records = new ArrayList<>();
        for(String file: allFiles) {
            DatabaseVariform databaseVariform = getDataFromFS(jsonFile + File.separator + file, DatabaseVariform.class);
            records.add(databaseVariform);
        }
        return records;
    }

    public List<BaseEntity> allRecordsFromEntityByWildcardChar(String wildcardString){
        return null;
    }

    private static final String ENTITY_NAME = "ExtensionTrigger";
    private static final Logger LOGGER = LoggerFactory.getLogger(ExtensionTriggerDao.class);
}
