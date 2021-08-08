package com.ad.dao;

import com.ad.constants.CommonConstants;
import com.ad.models.BaseEntity;
import com.ad.models.ReportDefinition;
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

@Component("ReportingJobDefinitionDao")
public class ReportingJobDefinitionDao extends EntitiesDao {

    public void convertXMLtoJSON(String inResponse, Map<String, List<BaseEntity>> inEntityMap) {
        List<BaseEntity> extensionList = new ArrayList<>();
        try {
            Document document = xmlUtil1.stringToXmlDocument(inResponse);
            if(document != null) {
                Element element = document.getRootElement();
                List<Element> elements = element.getChildren("reporting-job-definition");
                if(elements.size() == 0) {
                    return;
                }
                int elementIndex = 0;
                for (Element ele: elements) {
                    ReportingJobDefinition reportDefinition = new ReportingJobDefinition();
                    reportDefinition.setGkey(++elementIndex);
                    reportDefinition.setId(ele.getAttributeValue("id"));
                    reportDefinition.setExecutionGroupName(ele.getAttributeValue("execution-group-name"));
                    reportDefinition.setReportUserId(ele.getAttributeValue("report-user-id"));
                    reportDefinition.setScopeId(ele.getAttributeValue("scope-id"));
                    extensionList.add(reportDefinition);
                    String dataFile = controllerr.ENTITY_XML_DIR + File.separator + ENTITY_NAME + File.separator + elementIndex + CommonConstants.XML_EXTENSION;
                    saveDataToFS(dataFile, xmlUtil1.convertToString(ele, true), Boolean.FALSE);
                    String jsonFile = controllerr.ENTITY_JSON_DIR + File.separator + ENTITY_NAME + File.separator + elementIndex + CommonConstants.JSON_EXTENSION;
                    saveDataToFS(jsonFile, reportDefinition, Boolean.FALSE);
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
            ReportingJobDefinition reportingJobDefinition = getDataFromFS(jsonFile + File.separator + file, ReportingJobDefinition.class);
            records.add(reportingJobDefinition);
        }
        return records;
    }

    public List<BaseEntity> allRecordsFromEntityByWildcardChar(String wildcardString){
        String jsonFile = controllerr.ENTITY_JSON_DIR + File.separator + ENTITY_NAME;
        List<String> allFiles = getAllFileNames(jsonFile, CommonConstants.JSON_EXTENSION);
        List<BaseEntity> records = new ArrayList<>();
        for(String file: allFiles) {
            ReportingJobDefinition reportingJobDefinition = getDataFromFS(jsonFile + File.separator + file, ReportingJobDefinition.class);
            if(isMatch(reportingJobDefinition.getId(), wildcardString)) {
                records.add(reportingJobDefinition);
            }
        }
        return records;
    }

    private static final String ENTITY_NAME = "ReportingJobDefinition";
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportingJobDefinitionDao.class);
}
