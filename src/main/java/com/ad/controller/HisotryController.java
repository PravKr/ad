package com.ad.controller;

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

@CrossOrigin(origins = "http://localhost:5000", maxAge = 3600)
@Controller
@RequestMapping("ad/history/import")
public class HisotryController {
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
        controllerr.intilizeDataDir(requestHeader, systemId, OperationContants.IMPORT_STRING, null);
        if(OperationContants.IMPORT_STRING.equalsIgnoreCase(OperationContants.IMPORT_STRING)) {
            return importHistoryDao.getHistory();
        } else if(OperationContants.EXPORT_STRING.equalsIgnoreCase(OperationContants.IMPORT_STRING)) {
            return exportHistoryDao.getHistory();
        }
        return null;
    }

    @RequestMapping(value = "/{systemType}/{systemId}/{date}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Set<Object>> getHistoryByDate(@PathVariable String systemId,
                                                    @PathVariable String date) {
        controllerr.intilizeDataDir(requestHeader, systemId, OperationContants.IMPORT_STRING, null);
        if(OperationContants.IMPORT_STRING.equalsIgnoreCase(OperationContants.IMPORT_STRING)) {
            return importHistoryDao.getHistoryByDate(date);
        } else if(OperationContants.EXPORT_STRING.equalsIgnoreCase(OperationContants.IMPORT_STRING)) {
            return exportHistoryDao.getHistoryByDate(date);
        }
        return null;
    }

    @RequestMapping(value = "/{systemType}/{systemId}/importedSystem/{date}", method = RequestMethod.POST)
    @ResponseBody
    public List<String> getImportedSystemByDate(@PathVariable String systemId,
                                                     @PathVariable String date) {
        controllerr.intilizeDataDir(requestHeader, systemId, OperationContants.IMPORT_STRING, null);
        return exportHistoryDao.getImportSystemListByDate(date);
    }

    @RequestMapping(value = "/{systemType}/{systemId}/{date}/export", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<byte[]> exportSelectedHistoryDate(@PathVariable String systemId,
                                                         @PathVariable String date) {
        controllerr.intilizeDataDir(requestHeader, systemId, OperationContants.IMPORT_STRING, null);
        ImportHistory importHistory = new ImportHistory();
        ExportHistory exportHistory = new ExportHistory();
        controllerr.createImportAndExportHistory(importHistory, exportHistory, null, systemId);

        String xml = operationHandler.exportFromHistory(importHistory, exportHistory, OperationContants.IMPORT_STRING, date);
        byte[] isr = xml.getBytes();
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentLength(isr.length);
        respHeaders.setContentType(new MediaType("text", "xml"));
        respHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        respHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=entities.xml");
        return new ResponseEntity<byte[]>(isr, respHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/{systemType}/{systemId}/{date}/import", method = RequestMethod.POST)
    @ResponseBody
    public String importSelectedHistoryByDate(@PathVariable String systemId,
                                       @PathVariable String date,
                                       @RequestBody List<String> argoIdList) {
        controllerr.intilizeDataDir(requestHeader, systemId, OperationContants.IMPORT_STRING, null);
        ImportHistory importHistory = new ImportHistory();
        ExportHistory exportHistory = new ExportHistory();
        controllerr.createImportAndExportHistory(importHistory, exportHistory, argoIdList, OperationContants.IMPORT_STRING);
        boolean isImported = operationHandler.importFromHistory(argoIdList, importHistory, exportHistory, OperationContants.IMPORT_STRING, date);
        if(isImported) {
            return "Import Successfull";
        } else {
            return "Import is not Successfull";
        }
    }
}
