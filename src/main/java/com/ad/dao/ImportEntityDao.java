package com.ad.dao;

import com.ad.constants.CommonConstants;
import com.ad.models.BaseEntity;
import com.ad.models.ExportHistory;
import com.ad.models.ImportHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;

@Component("ImportEntityDao")
public class ImportEntityDao extends EntitiesDao {

    @Autowired
    CartDao cartDao;

    public Map<String, List<String>> getListOfEntities(ImportHistory inImportHistory, ExportHistory inExportHistory) {
        Map<String, List<String>> allEntities = new HashMap<>();
        Map<String, Set<String>> inEntityMap = cartDao.getEntitiesFromCart();
        if(inImportHistory != null) {
            inImportHistory.setExportedEnitites(inEntityMap);
        }

        if(inExportHistory != null) {
            inExportHistory.setExportedEnitites(inEntityMap);
        }

        for (Map.Entry<String, Set<String>> entry : inEntityMap.entrySet()) {
            String dataFile = controllerr.ENTITY_XML_DIR + File.separator + entry.getKey() + File.separator;
            List<String> contents = new ArrayList<>();
            for (String elementIndex : entry.getValue()) {
                contents.add(getDataFromFS(dataFile + elementIndex + CommonConstants.XML_EXTENSION, String.class)/*getFileContentById(dataFile + elementIndex + ".txt")*/);
            }
            allEntities.put(entry.getKey(), contents);
        }
        return allEntities;
    }

    public void renameOfSystemWithDateFolder() {

    }
    public List<BaseEntity> allRecordsFromEntityByWildcardChar(String wildcardString){
        return null;
    }

    public List<BaseEntity> allRecordsFromEntity(){
        return null;
    }
}
