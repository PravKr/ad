package com.ad.dao;

import com.ad.constants.CommonConstants;
import com.ad.models.ArgoSecRole;
import com.ad.models.BaseEntity;
import com.ad.models.DocumentType;
import org.jdom.Document;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("DocumentTypeDao")
public class DocumentTypeDao extends EntitiesDao {

    public void convertXMLtoJSON(String inResponse, Map<String, List<BaseEntity>> inEntityMap) {
        List<BaseEntity> extensionList = new ArrayList<>();
        try {
            Document document = xmlUtil1.stringToXmlDocument(inResponse);
            if(document != null) {
                Element element = document.getRootElement();
                List<Element> elements = element.getChildren("document-type");
                if(elements.size() == 0) {
                    return;
                }
                int elementIndex = 0;
                for (Element ele: elements) {
                    DocumentType documentType = new DocumentType();
                    documentType.setGkey(++elementIndex);
                    documentType.setId(ele.getAttributeValue("id"));
                    documentType.setUsage(ele.getAttributeValue("usage"));
                    documentType.setLifeCycleState(ele.getAttributeValue("life-cycle-state"));
                    documentType.setDescription(ele.getAttributeValue("description"));
                    extensionList.add(documentType);
                    String dataFile = controllerr.ENTITY_XML_DIR + File.separator + ENTITY_NAME + File.separator + elementIndex + CommonConstants.XML_EXTENSION;
                    saveDataToFS(dataFile, xmlUtil1.convertToString(ele, true), Boolean.FALSE);
                    String jsonFile = controllerr.ENTITY_JSON_DIR + File.separator + ENTITY_NAME + File.separator + elementIndex + CommonConstants.JSON_EXTENSION;
                    saveDataToFS(jsonFile, documentType, Boolean.FALSE);
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
        List<String> allFiles = getAllFileNames(jsonFile, CommonConstants.JSON_EXTENSION);
        List<BaseEntity> records = new ArrayList<>();
        for(String file: allFiles) {
            DocumentType documentType = getDataFromFS(jsonFile + File.separator + file, DocumentType.class);
            records.add(documentType);
        }
        return records;
    }

    public List<BaseEntity> allRecordsFromEntityByWildcardChar(String wildcardString){
        String jsonFile = controllerr.ENTITY_JSON_DIR + File.separator + ENTITY_NAME;
        List<String> allFiles = getAllFileNames(jsonFile, CommonConstants.JSON_EXTENSION);
        List<BaseEntity> records = new ArrayList<>();
        for(String file: allFiles) {
            DocumentType documentType = getDataFromFS(jsonFile + File.separator + file, DocumentType.class);
            if(isMatch(documentType.getId(), wildcardString)) {
                records.add(documentType);
            }
        }
        return records;
    }

    private static final String ENTITY_NAME = "Documents";
    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentTypeDao.class);
}
