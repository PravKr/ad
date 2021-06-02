package com.ad.dao;

import com.ad.constants.CommonConstants;
import com.ad.models.BaseEntity;
import com.ad.models.ExtensionTrigger;
import com.ad.models.GroovyJob;
import org.jdom.Document;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("ArgoGroovyJobDefinitionDao")
public class GroovyJobDao extends EntitiesDao {

    public void convertXMLtoJSON(String inResponse, Map<String, List<BaseEntity>> inEntityMap) {
        List<BaseEntity> groovyJobList = new ArrayList<>();
        try {
            Document document = xmlUtil1.stringToXmlDocument(inResponse);
            if(document != null) {
                Element element = document.getRootElement();
                List<Element> elements = element.getChildren("ArgoGroovyJobDefinition");
                int elementIndex = 0;
                for (Element ele: elements) {
                    GroovyJob groovyJob = new GroovyJob();
                    groovyJob.setGkey(++elementIndex);
                    groovyJob.setJobdefId(ele.getAttributeValue("jobdefId"));
                    groovyJob.setJobdefJobGroup(ele.getAttributeValue("jobdefJobGroup"));
                    groovyJob.setGrvjobCodeType(ele.getAttributeValue("grvjobCodeType"));
                    groovyJobList.add(groovyJob);
                    String xmlFile = controllerr.ENTITY_XML_DIR + File.separator + ENTITY_NAME + File.separator + elementIndex + CommonConstants.XML_EXTENSION;
                    saveDataToFS(xmlFile, xmlUtil1.convertToString(ele, true), Boolean.FALSE);
                    String jsonFile = controllerr.ENTITY_JSON_DIR + File.separator + ENTITY_NAME + File.separator + elementIndex + CommonConstants.JSON_EXTENSION;
                    saveDataToFS(jsonFile, groovyJob, Boolean.FALSE);
                }
            }
        } catch (Exception e1) {
            LOGGER.error("Error generating export report map" + e1.getMessage());
            e1.printStackTrace();
        }
        inEntityMap.put(ENTITY_NAME, groovyJobList);
    }

    public List<BaseEntity> allRecordsFromEntity(){
        String jsonFile = controllerr.ENTITY_JSON_DIR + File.separator + ENTITY_NAME;
        List<String> allFiles = getAllFileNames(jsonFile, CommonConstants.JSON_EXTENSION);
        List<BaseEntity> records = new ArrayList<>();
        for(String file: allFiles) {
            GroovyJob groovyJob = getDataFromFS(jsonFile + File.separator + file, GroovyJob.class);
            records.add(groovyJob);
        }
        return records;
    }

    public List<BaseEntity> allRecordsFromEntityByWildcardChar(String wildcardString){
        String jsonFile = controllerr.ENTITY_JSON_DIR + File.separator + ENTITY_NAME;
        List<String> allFiles = getAllFileNames(jsonFile, CommonConstants.JSON_EXTENSION);
        List<BaseEntity> records = new ArrayList<>();
        for(String file: allFiles) {
            GroovyJob groovyJob = getDataFromFS(jsonFile + File.separator + file, GroovyJob.class);
            if(isMatch(groovyJob.getJobdefId(), wildcardString)) {
                records.add(groovyJob);
            }
        }
        return records;
    }

    private static final String ENTITY_NAME = "GroovyJob";
    private static final Logger LOGGER = LoggerFactory.getLogger(GroovyJobDao.class);
}
