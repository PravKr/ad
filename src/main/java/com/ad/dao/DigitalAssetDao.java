package com.ad.dao;

import com.ad.models.BaseEntity;
import com.ad.models.DigitalAsset;
import org.jdom.Document;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;

@Component("DigitalAssetDao")
public class DigitalAssetDao extends EntitiesDao {

    public void convertXMLtoJSON(String inResponse, Map<String, List<BaseEntity>> inEntityMap) {
        List<BaseEntity> digitalAssetList = new ArrayList<>();
        try {
            Document document = xmlUtil1.stringToXmlDocument(inResponse);
            if(document != null) {
                Element element = document.getRootElement();
                List<Element> elements = element.getChildren("digital-asset");
                int elementIndex = 0;
                for (Element ele: elements) {
                    DigitalAsset digitalAsset = new DigitalAsset();
                    digitalAsset.setGkey(++elementIndex);
                    digitalAsset.setId(ele.getAttributeValue("id"));
                    digitalAsset.setShortDescription(ele.getAttributeValue("short-description"));
                    digitalAsset.setIsPreDeployed(ele.getAttributeValue("is-pre-deployed"));
                    digitalAsset.setFormat(ele.getAttributeValue("format"));
                    digitalAssetList.add(digitalAsset);
                    String xmlFile = controllerr.ENTITY_XML_DIR + File.separator + ENTITY_NAME + File.separator + elementIndex + XML_EXTENSION;
                    saveDataToFS(xmlFile, xmlUtil1.convertToString(ele, true), Boolean.FALSE);
                    String jsonFile = controllerr.ENTITY_JSON_DIR + File.separator + ENTITY_NAME + File.separator + elementIndex + JSON_EXTENSION;
                    saveDataToFS(jsonFile, digitalAsset, Boolean.FALSE);
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
            DigitalAsset digitalAsset = getDataFromFS(jsonFile + File.separator + file, DigitalAsset.class);
            records.add(digitalAsset);
        }
        return records;
    }

    private static final String ENTITY_NAME = "GroovyPlugins";
    private static final Logger LOGGER = LoggerFactory.getLogger(DigitalAssetDao.class);
}
