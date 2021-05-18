package com.ad.controller;

import com.ad.dao.ArgoDao;
import com.ad.dao.CartDao;
import com.ad.dao.EntitiesDao;
import com.ad.dao.ImportHistoryDao;
import com.ad.models.Argo;
import com.ad.models.BaseEntity;
import com.ad.models.ImportHistory;
import com.ad.request.handler.OperationHandler;
import com.ad.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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

    @RequestMapping(value = "/{systemType}/{systemId}", method = RequestMethod.POST)
    @ResponseBody
    public List<String> getHistory(@PathVariable String systemType,
                                         @PathVariable String systemId) {
        controllerr.intilizeDataDir(requestHeader, systemId, systemType);
        return importHistoryDao.getHistory();
    }

    @RequestMapping(value = "/{systemType}/{systemId}/{date}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Set<Object>> getHistoryByDate(@PathVariable String systemType,
                                                    @PathVariable String systemId,
                                                    @PathVariable String date) {
        controllerr.intilizeDataDir(requestHeader, systemId, systemType);
        return importHistoryDao.getHistoryDate(date);
    }
}
