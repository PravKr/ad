package com.ad.dao;

import com.ad.constants.CommonConstants;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class SystemDao extends BaseDao{

    public Set<String> getVisitedHistory() {
        Set<String> visitedHistory = getAllDirNames(controllerr.SYSTEM_DIR);
        visitedHistory.add(CommonConstants.VISIT_NOW_STRING);
        visitedHistory.remove("history");
        return visitedHistory;
    }

    public Set<String> getEntitiesByDate() {
        return getAllDirNames(controllerr.ENTITY_JSON_DIR);
    }
}
