package com.ad.dao;

import com.ad.models.BaseEntity;
import com.ad.models.GeneralReference;
import org.jdom.Document;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;

@Component("GeneralReferenceDao")
public class GeneralReferenceDao extends EntitiesDao {

    public void convertXMLtoJSON(String inResponse, Map<String, List<BaseEntity>> inEntityMap) {
        List<BaseEntity> extensionList = new ArrayList<>();
        try {
            Document document = xmlUtil1.stringToXmlDocument(inResponse);
            if(document != null) {
                Element element = document.getRootElement();
                List<Element> elements = element.getChildren("GeneralReference");
                int elementIndex = 0;
                for (Element ele: elements) {
                    GeneralReference genralRef = new GeneralReference();
                    genralRef.setGkey(++elementIndex);
                    genralRef.setRefType(ele.getAttributeValue("refType"));
                    genralRef.setRefId1(ele.getAttributeValue("refId1"));
                    genralRef.setRefValue1(ele.getAttributeValue("refValue1"));
                    genralRef.setRefCreator(ele.getAttributeValue("refCreator"));
                    genralRef.setRefCreated(ele.getAttributeValue("refCreated"));
                    genralRef.setChecked(Boolean.FALSE);
                    extensionList.add(genralRef);
                    String xmlFile = controllerr.ENTITY_XML_DIR + File.separator + ENTITY_NAME + File.separator + elementIndex + XML_EXTENSION;
                    saveDataToFS(xmlFile, xmlUtil1.convertToString(ele, true), Boolean.FALSE);
                    String jsonFile = controllerr.ENTITY_JSON_DIR + File.separator + ENTITY_NAME + File.separator + elementIndex + JSON_EXTENSION;
                    saveDataToFS(jsonFile, genralRef, Boolean.FALSE);
                }
            }
        } catch (Exception e1) {
            LOGGER.error("Error generating export report map" + e1.getMessage());
        }
        inEntityMap.put(ENTITY_NAME, extensionList);
    }

    public List<BaseEntity> allRecordsFromEntity(){
        String jsonFile = controllerr.ENTITY_JSON_DIR + File.separator + ENTITY_NAME;
        List<String> allFiles = getAllFileNames(jsonFile);
        List<BaseEntity> records = new ArrayList<>();
        for(String file: allFiles) {
            GeneralReference generalReference = getDataFromFS(jsonFile + File.separator + file, GeneralReference.class);
            records.add(generalReference);
        }
        return records;
    }

    private static final String ENTITY_NAME = "GeneralReference";
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneralReferenceDao.class);
}
