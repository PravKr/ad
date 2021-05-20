package com.ad.dao;

import com.ad.models.Argo;
import com.ad.models.BaseEntity;
import com.ad.models.ExportHistory;
import com.ad.models.ImportHistory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;

@Component
public class ExportHistoryDao extends EntitiesDao {

    public void createOrSaveHistory(ExportHistory inExportHistory) {
        String exportHistoryFile =
                controllerr.HISTORY_DIR + File.separator + inExportHistory.getExportDate() + JSON_EXTENSION;
        saveDataToFS(exportHistoryFile, inExportHistory, Boolean.FALSE);
    }

    public List<String> getImportSystemListByDate(String date) {
        String exportHistoryFile =
                controllerr.HISTORY_DIR + File.separator + addExtensionToFile(date);
        return getDataFromFS(exportHistoryFile, List.class);
    }

    public Map<String, Set<Object>> getHistoryByDate(String date) {
        Map<String, Set<Object>> inEntityMap = new HashMap<>();
        String dataFile = controllerr.HISTORY_DIR + File.separator + addExtensionToFile(date);
        ExportHistory history = getDataFromFS(dataFile, ExportHistory.class);
        for(Map.Entry<String, Set<String>> entry: history.getExportedEnitites().entrySet()) {
            Set<Object> baseEntitySet = new HashSet<>();
            String jsonDataFile = controllerr.ENTITY_JSON_DIR + File.separator + entry.getKey();
            for(String key: entry.getValue()) {
                String jsonFile = jsonDataFile + File.separator + key + JSON_EXTENSION;
                Object object = getDataFromFS(jsonFile, Object.class);
                baseEntitySet.add(object);
            }
            if(!baseEntitySet.isEmpty()) {
                inEntityMap.put(entry.getKey(), baseEntitySet);
            }
        }
        return inEntityMap;
    }

    public Map<String, List<String>> getListOfEntitiesHistory(ImportHistory inImportHistory, ExportHistory inExportHistory, String date) {
        Map<String, List<String>> allEntities = new HashMap<>();
        String dataFile = controllerr.HISTORY_DIR + File.separator + addExtensionToFile(date);
        ExportHistory history = getDataFromFS(dataFile, ExportHistory.class);
        Map<String, Set<String>> inEntityMap = history.getExportedEnitites();
        if(inImportHistory != null) {
            inImportHistory.setExportedEnitites(inEntityMap);
        }

        if(inExportHistory != null) {
            inExportHistory.setExportedEnitites(inEntityMap);
        }

        for (Map.Entry<String, Set<String>> entry : inEntityMap.entrySet()) {
            String xmlDataFile = controllerr.ENTITY_JSON_DIR + File.separator + entry.getKey() + File.separator;
            List<String> contents = new ArrayList<>();
            for (String elementIndex : entry.getValue()) {
                contents.add(getDataFromFS(xmlDataFile + elementIndex + XML_EXTENSION, String.class)/*getFileContentById(dataFile + elementIndex + ".txt")*/);
            }
            allEntities.put(entry.getKey(), contents);
        }
        return allEntities;
    }

    public List<String> getHistory() {
        return removeExtensionFromFile(getAllFileNames(controllerr.HISTORY_DIR));
    }

    public List<BaseEntity> allRecordsFromEntity(){
        return null;
    }
}
