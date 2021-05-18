package com.ad.dao;

import com.ad.models.Argo;
import com.ad.models.BaseEntity;
import com.ad.models.ImportHistory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;

@Component
public class ImportHistoryDao extends EntitiesDao {
    public static final String HISTORY_PATH = "history";

    public void createOrSaveHistory(Argo inArgo, ImportHistory inImportHistory) {
        String importHistoryFile =
                inArgo.getId() + File.separator + "import" + File.separator + HISTORY_PATH + File.separator + inImportHistory.getExportDate() + ".json";
        saveDataToFS(importHistoryFile, inImportHistory, Boolean.FALSE);
    }

    public Map<String, Set<Object>> getHistoryDate(String date) {
        Map<String, Set<Object>> inEntityMap = new HashMap<>();
        String dataFile = controllerr.HISTORY_DIR + File.separator + date;
        ImportHistory history = getDataFromFS(dataFile, ImportHistory.class);
        for(Map.Entry<String, Set<String>> entry: history.getExportedEnitites().entrySet()) {
            Set<Object> baseEntitySet = new HashSet<>();
            String jsonDataFile = history.getExportSystemId() + File.separator + "export/entities/json" + File.separator + entry.getKey();
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

    public List<String> getHistory() {
        return getAllFileNames(controllerr.HISTORY_DIR);
    }

    public List<BaseEntity> allRecordsFromEntity(){
        return null;
    }
}
