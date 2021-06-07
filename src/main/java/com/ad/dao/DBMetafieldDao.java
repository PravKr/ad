package com.ad.dao;

import com.ad.constants.CommonConstants;
import com.ad.models.BaseEntity;
import com.ad.models.DBMetafield;
import com.ad.models.ExtensionTrigger;
import org.jdom.Document;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("DbMetafieldDao")
public class DBMetafieldDao extends EntitiesDao {

    public void convertXMLtoJSON(String inResponse, Map<String, List<BaseEntity>> inEntityMap) {
        List<BaseEntity> dbMetafieldList = new ArrayList<>();
        try {
            Document document = xmlUtil1.stringToXmlDocument(inResponse);
            if(document != null) {
                Element element = document.getRootElement();
                List<Element> elements = element.getChildren("db-metafield");
                if(elements.size() == 0) {
                    return;
                }
                int elementIndex = 0;
                for (Element ele: elements) {
                    DBMetafield dbMetafield = new DBMetafield();
                    dbMetafield.setGkey(++elementIndex);
                    dbMetafield.setId(ele.getAttributeValue("id"));
                    dbMetafield.setImportance(ele.getAttributeValue("importance"));
                    dbMetafield.setShortName(ele.getAttributeValue("short-name"));
                    dbMetafieldList.add(dbMetafield);
                    String xmlFile = controllerr.ENTITY_XML_DIR + File.separator + ENTITY_NAME + File.separator + elementIndex + CommonConstants.XML_EXTENSION;
                    saveDataToFS(xmlFile, xmlUtil1.convertToString(ele, true), Boolean.FALSE);
                    String jsonFile = controllerr.ENTITY_JSON_DIR + File.separator + ENTITY_NAME + File.separator + elementIndex + CommonConstants.JSON_EXTENSION;
                    saveDataToFS(jsonFile, dbMetafield, Boolean.FALSE);
                }
            }
        } catch (Exception e1) {
            LOGGER.error("Error generating export report map" + e1.getMessage());
            e1.printStackTrace();
        }
        inEntityMap.put(ENTITY_NAME, dbMetafieldList);
    }

    public List<BaseEntity> allRecordsFromEntity(){
        String jsonFile = controllerr.ENTITY_JSON_DIR + File.separator + ENTITY_NAME;
        List<String> allFiles = getAllFileNames(jsonFile, CommonConstants.JSON_EXTENSION);
        List<BaseEntity> records = new ArrayList<>();
        for(String file: allFiles) {
            DBMetafield dbMetafield = getDataFromFS(jsonFile + File.separator + file, DBMetafield.class);
            records.add(dbMetafield);
        }
        return records;
    }

    public List<BaseEntity> allRecordsFromEntityByWildcardChar(String wildcardString){
        String jsonFile = controllerr.ENTITY_JSON_DIR + File.separator + ENTITY_NAME;
        List<String> allFiles = getAllFileNames(jsonFile, CommonConstants.JSON_EXTENSION);
        List<BaseEntity> records = new ArrayList<>();
        for(String file: allFiles) {
            DBMetafield dbMetafield = getDataFromFS(jsonFile + File.separator + file, DBMetafield.class);
            if(isMatch(dbMetafield.getId(), wildcardString)) {
                records.add(dbMetafield);
            }
        }
        return records;
    }

    private static final String ENTITY_NAME = "Fields";
    private static final Logger LOGGER = LoggerFactory.getLogger(DBMetafieldDao.class);
}
