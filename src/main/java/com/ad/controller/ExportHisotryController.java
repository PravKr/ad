package com.ad.controller;

import com.ad.constants.CommonConstants;
import com.ad.constants.OperationContants;
import com.ad.dao.ExportHistoryDao;
import com.ad.dao.ImportHistoryDao;
import com.ad.models.ExportHistory;
import com.ad.models.ImportHistory;
import com.ad.request.handler.OperationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:5000", maxAge = 3600)
@Controller
@RequestMapping("ad/history/export")
public class ExportHisotryController {
    @Autowired
    OperationHandler operationHandler;
    @Autowired
    HttpServletRequest requestHeader;
    @Autowired
    Controllerr controllerr;
    @Autowired
    ImportHistoryDao importHistoryDao;
    @Autowired
    ExportHistoryDao exportHistoryDao;

    @RequestMapping(value = "/{systemId}", method = RequestMethod.POST)
    @ResponseBody
    public List<String> getHistory(@PathVariable String systemId) {
        controllerr.intilizeDataDir(requestHeader, systemId, OperationContants.EXPORT_STRING);
        return exportHistoryDao.getHistory();
    }

    @RequestMapping(value = "/{systemId}/{date}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Set<Object>> getHistoryByDate(@PathVariable String systemId,
                                                     @PathVariable String date) {
        controllerr.intilizeDataDir(requestHeader, systemId, OperationContants.EXPORT_STRING, CommonConstants.VISIT_DATE_PATTERN);
        return exportHistoryDao.getHistoryByDate(date);
    }

    @RequestMapping(value = "/{systemId}/importedSystem/{date}", method = RequestMethod.POST)
    @ResponseBody
    public List<String> getImportedSystemByDate(@PathVariable String systemId,
                                                @PathVariable String date) {
        controllerr.intilizeDataDir(requestHeader, systemId, OperationContants.EXPORT_STRING);
        return exportHistoryDao.getImportSystemListByDate(date);
    }
}
