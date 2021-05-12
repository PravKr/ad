package com.ad.dao;

import com.ad.models.BaseEntity;
import com.ad.models.Extension;
import org.jdom.Document;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;

@Component("ExtensionDao")
public class ExtensionDao extends EntitiesDao {

    public void convertXMLtoJSON(String inResponse, Map<String, List<BaseEntity>> inEntityMap) {
        List<BaseEntity> extensionList = new ArrayList<>();
        try {
            Document document = xmlUtil1.stringToXmlDocument(inResponse);
            if(document != null) {
                Element element = document.getRootElement();
                List<Element> elements = element.getChildren("extension");
                int elementIndex = 0;
                for (Element ele: elements) {
                    Extension extension = new Extension();
                    extension.setGkey(++elementIndex);
                    extension.setName(ele.getAttributeValue("name"));
                    extension.setScope(ele.getAttributeValue("scope"));
                    extension.setType(ele.getAttributeValue("type"));
                    extensionList.add(extension);
                    String dataFile = controllerr.ENTITY_XML_DIR + File.separator + ENTITY_NAME + File.separator + elementIndex + XML_EXTENSION;
                    saveDataToFS(dataFile, xmlUtil1.convertToString(ele, true), Boolean.FALSE);
                    String jsonFile = controllerr.ENTITY_JSON_DIR + File.separator + ENTITY_NAME + File.separator + elementIndex + JSON_EXTENSION;
                    saveDataToFS(jsonFile, extension, Boolean.FALSE);
                }
            }
        } catch (Exception e1) {
            LOGGER.error("Error generating export report map" + e1.getMessage());
            e1.printStackTrace();
        }
        inEntityMap.put(ENTITY_NAME, extensionList);
    }

    public List<BaseEntity> allRecordsFromEntity(){
        String jsonFile = controllerr.ENTITY_JSON_DIR + File.separator + ENTITY_NAME;
        List<String> allFiles = getAllFileNames(jsonFile);
        List<BaseEntity> records = new ArrayList<>();
        for(String file: allFiles) {
            Extension extension = getDataFromFS(jsonFile + File.separator + file, Extension.class);
            records.add(extension);
        }
        return records;
    }

    private static final String ENTITY_NAME = "CodeExtension";
    private static final Logger LOGGER = LoggerFactory.getLogger(ExtensionDao.class);
}
