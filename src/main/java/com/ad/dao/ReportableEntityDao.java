package com.ad.dao;

import com.ad.constants.CommonConstants;
import com.ad.models.BaseEntity;
import com.ad.models.ReportableEntity;
import com.ad.models.ReportingJobDefinition;
import org.jdom.Document;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("ReportableEntityDao")
public class ReportableEntityDao extends EntitiesDao {

    public void convertXMLtoJSON(String inResponse, Map<String, List<BaseEntity>> inEntityMap) {
        List<BaseEntity> extensionList = new ArrayList<>();
        try {
            Document document = xmlUtil1.stringToXmlDocument(inResponse);
            if(document != null) {
                Element element = document.getRootElement();
                List<Element> elements = element.getChildren("reportable-entity");
                if(elements.size() == 0) {
                    return;
                }
                int elementIndex = 0;
                for (Element ele: elements) {
                    ReportableEntity reportableEntity = new ReportableEntity();
                    reportableEntity.setGkey(++elementIndex);
                    reportableEntity.setName(ele.getAttributeValue("name"));
                    reportableEntity.setDisplayName(ele.getAttributeValue("display-name"));
                    reportableEntity.setBaseEntity(ele.getAttributeValue("base-entity"));
                    reportableEntity.setDescription(ele.getAttributeValue("description"));
                    extensionList.add(reportableEntity);
                    String dataFile = controllerr.ENTITY_XML_DIR + File.separator + ENTITY_NAME + File.separator + elementIndex + CommonConstants.XML_EXTENSION;
                    saveDataToFS(dataFile, xmlUtil1.convertToString(ele, true), Boolean.FALSE);
                    String jsonFile = controllerr.ENTITY_JSON_DIR + File.separator + ENTITY_NAME + File.separator + elementIndex + CommonConstants.JSON_EXTENSION;
                    saveDataToFS(jsonFile, reportableEntity, Boolean.FALSE);
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
            ReportableEntity reportableEntity = getDataFromFS(jsonFile + File.separator + file, ReportableEntity.class);
            records.add(reportableEntity);
        }
        return records;
    }

    public List<BaseEntity> allRecordsFromEntityByWildcardChar(String wildcardString){
        String jsonFile = controllerr.ENTITY_JSON_DIR + File.separator + ENTITY_NAME;
        List<String> allFiles = getAllFileNames(jsonFile, CommonConstants.JSON_EXTENSION);
        List<BaseEntity> records = new ArrayList<>();
        for(String file: allFiles) {
            ReportableEntity reportableEntity = getDataFromFS(jsonFile + File.separator + file, ReportableEntity.class);
            if(isMatch(reportableEntity.getName(), wildcardString)) {
                records.add(reportableEntity);
            }
        }
        return records;
    }

    private static final String ENTITY_NAME = "ReportableEntity";
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportableEntityDao.class);
}
