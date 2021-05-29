package com.ad.dao;

import com.ad.constants.CommonConstants;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class SystemDao extends BaseDao {

    public Set<String> getVisitedHistory(String systemId) {
        Set<String> visitedHistory = getDataFromFS(systemId + File.separator + systemId + ".json", Set.class);;
        if(visitedHistory == null) {
            visitedHistory = new HashSet<>();
        }
        visitedHistory.add(CommonConstants.VISIT_NOW_STRING);
        visitedHistory.remove("history");
        return visitedHistory;
    }

    public Set<String> getEntitiesByDate() {
        return getAllDirNames(controllerr.ENTITY_JSON_DIR);
    }

    public boolean saveToSystem(String systemId, String visitDate) {
        String dataFile = systemId + File.separator + systemId + ".json";
        Set<String> set = getDataFromFS(dataFile, Set.class);
        if(set != null) {
            set.add(visitDate);
        } else {
            set = new HashSet<>();
            set.add(visitDate);
        }
        return saveDataToFS(dataFile, set, Boolean.FALSE);
    }

    public boolean removeFromSystem(String systemId, String visitDate) {
        String dataFile = systemId + File.separator + systemId + ".json";
        Set<String> set = getDataFromFS(dataFile, Set.class);
        if(set != null) {
            set.remove(visitDate);
        }
        return saveDataToFS(dataFile, set, Boolean.TRUE);
    }
}
