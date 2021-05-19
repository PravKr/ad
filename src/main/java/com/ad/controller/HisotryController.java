package com.ad.controller;

import com.ad.constants.OperationContants;
import com.ad.dao.*;
import com.ad.request.handler.OperationHandler;
import com.ad.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:5000", maxAge = 3600)
@Controller
@RequestMapping("ad/history")
public class HisotryController {
    @Autowired
    OperationHandler operationHandler;
    @Autowired
    CartDao cartDao;
    @Autowired
    ArgoDao argoDao;
    @Autowired
    HttpServletRequest requestHeader;
    @Autowired
    Controllerr controllerr;
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    DateUtil dateUtil;
    @Autowired
    ImportHistoryDao importHistoryDao;
    @Autowired
    ExportHistoryDao exportHistoryDao;

    @RequestMapping(value = "/{systemType}/{systemId}", method = RequestMethod.POST)
    @ResponseBody
    public List<String> getHistory(@PathVariable String systemType,
                                         @PathVariable String systemId) {
        controllerr.intilizeDataDir(requestHeader, systemId, systemType);
        if(OperationContants.IMPORT_STRING.equalsIgnoreCase(systemType)) {
            return importHistoryDao.getHistory();
        } else if(OperationContants.EXPORT_STRING.equalsIgnoreCase(systemType)) {
            return exportHistoryDao.getHistory();
        }
        return null;
    }

    @RequestMapping(value = "/{systemType}/{systemId}/{date}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Set<Object>> getHistoryByDate(@PathVariable String systemType,
                                                    @PathVariable String systemId,
                                                    @PathVariable String date) {
        controllerr.intilizeDataDir(requestHeader, systemId, systemType);
        if(OperationContants.IMPORT_STRING.equalsIgnoreCase(systemType)) {
            return importHistoryDao.getHistoryByDate(date);
        } else if(OperationContants.EXPORT_STRING.equalsIgnoreCase(systemType)) {
            return exportHistoryDao.getHistoryByDate(date);
        }
        return null;
    }

    @RequestMapping(value = "/{systemType}/{systemId}/importedSystem/{date}", method = RequestMethod.POST)
    @ResponseBody
    public List<String> getImportedSystemByDate(@PathVariable String systemType,
                                                     @PathVariable String systemId,
                                                     @PathVariable String date) {
        controllerr.intilizeDataDir(requestHeader, systemId, systemType);
        return exportHistoryDao.getImportSystemListByDate(date);
    }
}
