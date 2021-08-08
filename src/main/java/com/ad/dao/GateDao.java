package com.ad.dao;

import com.ad.constants.CommonConstants;
import com.ad.models.ArgoSecRole;
import com.ad.models.BaseEntity;
import com.ad.models.Gate;
import org.jdom.Document;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("GateDao")
public class GateDao extends EntitiesDao {

    public void convertXMLtoJSON(String inResponse, Map<String, List<BaseEntity>> inEntityMap) {
        List<BaseEntity> extensionList = new ArrayList<>();
        try {
            Document document = xmlUtil1.stringToXmlDocument(inResponse);
            if(document != null) {
                Element element = document.getRootElement();
                List<Element> elements = element.getChildren("gate");
                if(elements.size() == 0) {
                    return;
                }
                int elementIndex = 0;
                for (Element ele: elements) {
                    Gate gate = new Gate();
                    gate.setGkey(++elementIndex);
                    gate.setId(ele.getAttributeValue("id"));
                    gate.setGateConfigId(ele.getAttributeValue("gate-config-id"));
                    gate.setFacilityId(ele.getAttributeValue("facility-id"));
                    gate.setDescription(ele.getAttributeValue("description"));
                    extensionList.add(gate);
                    String dataFile = controllerr.ENTITY_XML_DIR + File.separator + ENTITY_NAME + File.separator + elementIndex + CommonConstants.XML_EXTENSION;
                    saveDataToFS(dataFile, xmlUtil1.convertToString(ele, true), Boolean.FALSE);
                    String jsonFile = controllerr.ENTITY_JSON_DIR + File.separator + ENTITY_NAME + File.separator + elementIndex + CommonConstants.JSON_EXTENSION;
                    saveDataToFS(jsonFile, gate, Boolean.FALSE);
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
            Gate gate = getDataFromFS(jsonFile + File.separator + file, Gate.class);
            records.add(gate);
        }
        return records;
    }

    public List<BaseEntity> allRecordsFromEntityByWildcardChar(String wildcardString){
        String jsonFile = controllerr.ENTITY_JSON_DIR + File.separator + ENTITY_NAME;
        List<String> allFiles = getAllFileNames(jsonFile, CommonConstants.JSON_EXTENSION);
        List<BaseEntity> records = new ArrayList<>();
        for(String file: allFiles) {
            Gate argoSecRole = getDataFromFS(jsonFile + File.separator + file, Gate.class);
            if(isMatch(argoSecRole.getId(), wildcardString)) {
                records.add(argoSecRole);
            }
        }
        return records;
    }

    private static final String ENTITY_NAME = "Gates";
    private static final Logger LOGGER = LoggerFactory.getLogger(GateDao.class);
}
