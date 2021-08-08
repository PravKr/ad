package com.ad.dao;

import com.ad.constants.CommonConstants;
import com.ad.models.ArgoSecRole;
import com.ad.models.BaseEntity;
import com.ad.models.Console;
import org.jdom.Document;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("ConsoleDao")
public class ConsoleDao extends EntitiesDao {

    public void convertXMLtoJSON(String inResponse, Map<String, List<BaseEntity>> inEntityMap) {
        List<BaseEntity> extensionList = new ArrayList<>();
        try {
            Document document = xmlUtil1.stringToXmlDocument(inResponse);
            if(document != null) {
                Element element = document.getRootElement();
                List<Element> elements = element.getChildren("console");
                if(elements.size() == 0) {
                    return;
                }
                int elementIndex = 0;
                for (Element ele: elements) {
                    Console argoSecRole = new Console();
                    argoSecRole.setGkey(++elementIndex);
                    argoSecRole.setHardwarId(ele.getAttributeValue("hardware-id"));
                    argoSecRole.setHardwareLocation(ele.getAttributeValue("hardware-location"));
                    argoSecRole.setExternalConsoleId(ele.getAttributeValue("external-console-id"));
                    argoSecRole.setLifeCycleState(ele.getAttributeValue("life-cycle-state"));
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
            Console console = getDataFromFS(jsonFile + File.separator + file, Console.class);
            records.add(console);
        }
        return records;
    }

    public List<BaseEntity> allRecordsFromEntityByWildcardChar(String wildcardString){
        String jsonFile = controllerr.ENTITY_JSON_DIR + File.separator + ENTITY_NAME;
        List<String> allFiles = getAllFileNames(jsonFile, CommonConstants.JSON_EXTENSION);
        List<BaseEntity> records = new ArrayList<>();
        for(String file: allFiles) {
            Console console = getDataFromFS(jsonFile + File.separator + file, Console.class);
            if(isMatch(console.getHardwarId(), wildcardString)) {
                records.add(console);
            }
        }
        return records;
    }

    private static final String ENTITY_NAME = "Console";
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleDao.class);
}
