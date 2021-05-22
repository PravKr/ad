package com.ad.dao;

import com.ad.constants.CommonConstants;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SystemDao extends BaseDao{

    public List<String> getVisitedHistory() {
        List<String> visitedHistory = getAllFileNames(controllerr.SYSTEM_DIR);
        visitedHistory.add(CommonConstants.VISIT_NOW_STRING);
        return getAllFileNames(controllerr.SYSTEM_DIR);
    }
}
