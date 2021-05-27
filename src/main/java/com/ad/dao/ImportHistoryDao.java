package com.ad.dao;

import com.ad.constants.CommonConstants;
import com.ad.models.Argo;
import com.ad.models.BaseEntity;
import com.ad.models.ExportHistory;
import com.ad.models.ImportHistory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;

@Component
public class ImportHistoryDao extends EntitiesDao {

    public void createOrSaveHistory(Argo inArgo, ImportHistory inImportHistory) {
        String importHistoryFile =
                inArgo.getId() + File.separator + HISTORY_PATH + File.separator + "import" + File.separator + inImportHistory.getExportDate() + JSON_EXTENSION;
        saveDataToFS(importHistoryFile, inImportHistory, Boolean.FALSE);
    }

    public Map<String, Set<Object>> getHistoryByDate(String date) {
        Map<String, Set<Object>> inEntityMap = new HashMap<>();
        String dataFile = controllerr.HISTORY_DIR + File.separator + addExtensionToFile(date);
        ImportHistory history = getDataFromFS(dataFile, ImportHistory.class);
        String jsonDataFile = history.getExportSystemId() + File.separator + HISTORY_PATH +
                File.separator + history.getExportSystemVisitDate() + File.separator + "export/entities/json" + File.separator;
        for(Map.Entry<String, Set<String>> entry: history.getExportedEnitites().entrySet()) {
            Set<Object> baseEntitySet = new HashSet<>();
            for(String key: entry.getValue()) {
                String jsonFile = jsonDataFile + entry.getKey() + File.separator + key + JSON_EXTENSION;
                Object object = getDataFromFS(jsonFile, Object.class);
                baseEntitySet.add(object);
            }
            if(!baseEntitySet.isEmpty()) {
                inEntityMap.put(entry.getKey(), baseEntitySet);
            }
        }
        return inEntityMap;
    }

    public List<String> getHistory() {
        return removeExtensionFromFile(getAllFileNames(controllerr.HISTORY_DIR));
    }

    public Map<String, List<String>> getListOfEntitiesHistory(ImportHistory inImportHistory, ExportHistory inExportHistory, String date) {
        Map<String, List<String>> allEntities = new HashMap<>();
        String dataFile = controllerr.HISTORY_DIR + File.separator + addExtensionToFile(date);
        ImportHistory history = getDataFromFS(dataFile, ImportHistory.class);
        Map<String, Set<String>> inEntityMap = history.getExportedEnitites();
        if(inImportHistory != null) {
            inImportHistory.setExportedEnitites(inEntityMap);
        }

        if(inExportHistory != null) {
            inExportHistory.setExportedEnitites(inEntityMap);
        }
        String xmlDataFile = history.getExportSystemId() + File.separator + HISTORY_PATH + File.separator + history.getExportSystemVisitDate()
                + File.separator + "export/entities/xml" + File.separator;
        for (Map.Entry<String, Set<String>> entry : inEntityMap.entrySet()) {
            List<String> contents = new ArrayList<>();
            for (String elementIndex : entry.getValue()) {
                contents.add(getDataFromFS(xmlDataFile + entry.getKey() + File.separator + elementIndex + XML_EXTENSION, String.class));
            }
            allEntities.put(entry.getKey(), contents);
        }
        return allEntities;
    }

    public List<BaseEntity> allRecordsFromEntity(){
        return null;
    }

    public List<BaseEntity> allRecordsFromEntityByWildcardChar(String wildcardString){
        return null;
    }
}
