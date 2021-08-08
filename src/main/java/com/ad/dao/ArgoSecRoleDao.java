package com.ad.dao;

import com.ad.constants.CommonConstants;
import com.ad.models.ArgoSecRole;
import com.ad.models.BaseEntity;
import com.ad.models.ReportDefinition;
import org.jdom.Document;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("ArgoSecRoleDao")
public class ArgoSecRoleDao extends EntitiesDao {

    public void convertXMLtoJSON(String inResponse, Map<String, List<BaseEntity>> inEntityMap) {
        List<BaseEntity> extensionList = new ArrayList<>();
        try {
            Document document = xmlUtil1.stringToXmlDocument(inResponse);
            if(document != null) {
                Element element = document.getRootElement();
                List<Element> elements = element.getChildren("role");
                if(elements.size() == 0) {
                    return;
                }
                int elementIndex = 0;
                for (Element ele: elements) {
                    ArgoSecRole argoSecRole = new ArgoSecRole();
                    argoSecRole.setGkey(++elementIndex);
                    argoSecRole.setName(ele.getAttributeValue("name"));
                    argoSecRole.setOperator(ele.getAttributeValue("operator"));
                    argoSecRole.setDescription(ele.getAttributeValue("description"));
                    extensionList.add(argoSecRole);
                    String dataFile = controllerr.ENTITY_XML_DIR + File.separator + ENTITY_NAME + File.separator + elementIndex + CommonConstants.XML_EXTENSION;
                    saveDataToFS(dataFile, xmlUtil1.convertToString(ele, true), Boolean.FALSE);
                    String jsonFile = controllerr.ENTITY_JSON_DIR + File.separator + ENTITY_NAME + File.separator + elementIndex + CommonConstants.JSON_EXTENSION;
                    saveDataToFS(jsonFile, argoSecRole, Boolean.FALSE);
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
            ArgoSecRole argoSecRole = getDataFromFS(jsonFile + File.separator + file, ArgoSecRole.class);
            records.add(argoSecRole);
        }
        return records;
    }

    public List<BaseEntity> allRecordsFromEntityByWildcardChar(String wildcardString){
        String jsonFile = controllerr.ENTITY_JSON_DIR + File.separator + ENTITY_NAME;
        List<String> allFiles = getAllFileNames(jsonFile, CommonConstants.JSON_EXTENSION);
        List<BaseEntity> records = new ArrayList<>();
        for(String file: allFiles) {
            ArgoSecRole argoSecRole = getDataFromFS(jsonFile + File.separator + file, ArgoSecRole.class);
            if(isMatch(argoSecRole.getName(), wildcardString)) {
                records.add(argoSecRole);
            }
        }
        return records;
    }

    private static final String ENTITY_NAME = "Roles";
    private static final Logger LOGGER = LoggerFactory.getLogger(ArgoSecRoleDao.class);
}
