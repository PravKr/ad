package com.ad.dao;

import com.ad.constants.CommonConstants;
import com.ad.models.BaseEntity;
import com.ad.models.EquipmentCondition;
import com.ad.models.StorageRule;
import org.jdom.Document;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("StorageRuleDao")
public class StorageRuleDao extends EntitiesDao {

    public void convertXMLtoJSON(String inResponse, Map<String, List<BaseEntity>> inEntityMap) {
        List<BaseEntity> extensionList = new ArrayList<>();
        try {
            Document document = xmlUtil1.stringToXmlDocument(inResponse);
            if(document != null) {
                Element element = document.getRootElement();
                List<Element> elements = element.getChildren("storage-rule");
                if(elements.size() == 0) {
                    return;
                }
                int elementIndex = 0;
                for (Element ele: elements) {
                    StorageRule storageRule = new StorageRule();
                    storageRule.setGkey(++elementIndex);
                    storageRule.setRuleId(ele.getAttributeValue("rule-id"));
                    storageRule.setIsRuleForPower(ele.getAttributeValue("is-rule-for-power"));
                    storageRule.setStartDayRule(ele.getAttributeValue("start-day-rule"));
                    storageRule.setEndDayRule(ele.getAttributeValue("end-day-rule"));
                    extensionList.add(storageRule);
                    String dataFile = controllerr.ENTITY_XML_DIR + File.separator + ENTITY_NAME + File.separator + elementIndex + CommonConstants.XML_EXTENSION;
                    saveDataToFS(dataFile, xmlUtil1.convertToString(ele, true), Boolean.FALSE);
                    String jsonFile = controllerr.ENTITY_JSON_DIR + File.separator + ENTITY_NAME + File.separator + elementIndex + CommonConstants.JSON_EXTENSION;
                    saveDataToFS(jsonFile, storageRule, Boolean.FALSE);
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
            StorageRule storageRule = getDataFromFS(jsonFile + File.separator + file, StorageRule.class);
            records.add(storageRule);
        }
        return records;
    }

    public List<BaseEntity> allRecordsFromEntityByWildcardChar(String wildcardString){
        String jsonFile = controllerr.ENTITY_JSON_DIR + File.separator + ENTITY_NAME;
        List<String> allFiles = getAllFileNames(jsonFile, CommonConstants.JSON_EXTENSION);
        List<BaseEntity> records = new ArrayList<>();
        for(String file: allFiles) {
            StorageRule storageRule = getDataFromFS(jsonFile + File.separator + file, StorageRule.class);
            if(isMatch(storageRule.getRuleId(), wildcardString)) {
                records.add(storageRule);
            }
        }
        return records;
    }

    private static final String ENTITY_NAME = "StorageRule";
    private static final Logger LOGGER = LoggerFactory.getLogger(StorageRuleDao.class);
}
