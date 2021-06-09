package com.ad.controller;

import com.ad.constants.CommonConstants;
import com.ad.constants.OperationContants;
import com.ad.dao.*;
import com.ad.models.ExportHistory;
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
import java.util.List;
import java.util.Map;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("ad/history/import")
public class ImportHisotryController {
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
        controllerr.intilizeDataDir(requestHeader, systemId, OperationContants.IMPORT_STRING);
        return importHistoryDao.getHistory();
    }

    @RequestMapping(value = "/{systemId}/{date}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Set<Object>> getHistoryByDate(@PathVariable String systemId,
                                                    @PathVariable String date) {
        controllerr.intilizeDataDir(requestHeader, systemId, OperationContants.IMPORT_STRING, CommonConstants.VISIT_DATE_PATTERN);
        return importHistoryDao.getHistoryByDate(date);
    }

    @RequestMapping(value = "/{systemId}/importedSystem/{date}", method = RequestMethod.POST)
    @ResponseBody
    public List<String> getImportedSystemByDate(@PathVariable String systemId,
                                                     @PathVariable String date) {
        controllerr.intilizeDataDir(requestHeader, systemId, OperationContants.IMPORT_STRING);
        return exportHistoryDao.getImportSystemListByDate(date);
    }

    @RequestMapping(value = "/{systemId}/{date}/import", method = RequestMethod.POST)
    @ResponseBody
    public String importSelectedEntities(@PathVariable String systemId,
                                         @PathVariable String date,
                                         @RequestBody List<String> argoIdList) {
        controllerr.intilizeDataDir(requestHeader, systemId, OperationContants.IMPORT_STRING);
        ImportHistory importHistory = new ImportHistory();
        ExportHistory exportHistory = new ExportHistory();
        controllerr.createImportAndExportHistory(importHistory, exportHistory, argoIdList, systemId, null);
        boolean isImported = operationHandler.importtFromHistory(argoIdList, importHistory, exportHistory, date, OperationContants.IMPORT_STRING);
        if(isImported) {
            return "Import Successfull";
        } else {
            return "Import is not Successfull";
        }
    }

    @RequestMapping(value = "/{systemId}/{date}/export", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<byte[]> exportSelectedEntities(@PathVariable String systemId,
                                                         @PathVariable String date) {
        controllerr.intilizeDataDir(requestHeader, systemId, OperationContants.IMPORT_STRING, CommonConstants.VISIT_DATE_PATTERN);
        ImportHistory importHistory = new ImportHistory();
        ExportHistory exportHistory = new ExportHistory();
        controllerr.createImportAndExportHistory(importHistory, exportHistory, null, systemId, null);

        String xml = operationHandler.exportFromHistory(importHistory, exportHistory, date, OperationContants.IMPORT_STRING);
        byte[] isr = xml.getBytes();
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentLength(isr.length);
        respHeaders.setContentType(new MediaType("text", "xml"));
        respHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        respHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=entities.xml");
        return new ResponseEntity<byte[]>(isr, respHeaders, HttpStatus.OK);
    }
}
