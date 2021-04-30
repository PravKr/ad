package com.ad.dao;

import com.ad.models.BaseEntity;
import com.ad.models.DatabaseVariform;
import org.jdom.Document;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("DbVariformFileDefinitionDao")
public class DbVariformFileDefinitionDao extends EntitiesDao {

    public void convertXMLtoJSON(String inResponse, Map<String, List<BaseEntity>> inEntityMap) {
        List<BaseEntity> dbVariformList = new ArrayList<>();
        try {
            Document document = xmlUtil1.stringToXmlDocument(inResponse);
            if(document != null) {
                Element element = document.getRootElement();
                List<Element> elements = element.getChildren("db-variform-file-definition");
                int elementIndex = 0;
                for (Element ele: elements) {
                    DatabaseVariform dbVariform = new DatabaseVariform();
                    dbVariform.setGkey(++elementIndex);
                    dbVariform.setName(ele.getAttributeValue("cfgvar-name"));
                    dbVariform.setDescription(ele.getAttributeValue("cfgvar-description"));
                    dbVariform.setEnabled(ele.getAttributeValue("cfgvar-enabled"));
                    dbVariform.setVariformIds(ele.getAttributeValue("cfgvar-variform-ids"));
                    dbVariform.setCreator(ele.getAttributeValue("cfgvar-creator"));
                    dbVariformList.add(dbVariform);
                    String xmlFile = ENTITY_XML_DIR + File.separator + ENTITY_NAME + File.separator + elementIndex + XML_EXTENSION;
                    saveDataToFS(xmlFile, xmlUtil1.convertToString(ele, true));
                    String jsonFile = ENTITY_JSON_DIR + File.separator + ENTITY_NAME + File.separator + elementIndex + JSON_EXTENSION;
                    saveDataToFS(jsonFile, dbVariform);
                }
            }
        } catch (Exception e1) {
            LOGGER.error("Error generating export report map" + e1.getMessage());
        }
        inEntityMap.put(ENTITY_NAME, dbVariformList);
    }

    private static final String ENTITY_NAME = "Database Backed Variform";
    private static final Logger LOGGER = LoggerFactory.getLogger(DbVariformFileDefinitionDao.class);
}
