package com.ad.controller;

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

    @RequestMapping(value = "/{systemId}/{visitedDate}", method = RequestMethod.POST)
    @ResponseBody
    public List<String> getHistory(@PathVariable String systemId,
                                   @PathVariable String visitedDate) {
        controllerr.intilizeDataDir(requestHeader, systemId, OperationContants.EXPORT_STRING, visitedDate);
        return exportHistoryDao.getHistory();
    }

    @RequestMapping(value = "/{systemId}/{visitedDate}/{date}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Set<Object>> getHistoryByDate(@PathVariable String systemType,
                                                     @PathVariable String systemId,
                                                     @PathVariable String visitedDate,
                                                     @PathVariable String date) {
        controllerr.intilizeDataDir(requestHeader, systemId, systemType, visitedDate);
        return exportHistoryDao.getHistoryByDate(date);
    }

    @RequestMapping(value = "/{systemId}/{visitedDate}/importedSystem/{date}", method = RequestMethod.POST)
    @ResponseBody
    public List<String> getImportedSystemByDate(@PathVariable String systemId,
                                                @PathVariable String visitedDate,
                                                @PathVariable String date) {
        controllerr.intilizeDataDir(requestHeader, systemId, OperationContants.EXPORT_STRING, visitedDate);
        return exportHistoryDao.getImportSystemListByDate(date);
    }

    @RequestMapping(value = "/{systemId}/{visitedDate}/{date}/export", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<byte[]> exportSelectedHistoryDate(@PathVariable String systemId,
                                                            @PathVariable String visitedDate,
                                                            @PathVariable String date) {
        controllerr.intilizeDataDir(requestHeader, systemId, OperationContants.EXPORT_STRING, visitedDate);
        ImportHistory importHistory = new ImportHistory();
        ExportHistory exportHistory = new ExportHistory();
        controllerr.createImportAndExportHistory(importHistory, exportHistory, null, systemId);

        String xml = operationHandler.exportFromHistory(importHistory, exportHistory, OperationContants.EXPORT_STRING, date);
        byte[] isr = xml.getBytes();
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentLength(isr.length);
        respHeaders.setContentType(new MediaType("text", "xml"));
        respHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        respHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=entities.xml");
        return new ResponseEntity<byte[]>(isr, respHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/{systemId}/{visitedDate}/{date}/import", method = RequestMethod.POST)
    @ResponseBody
    public String importSelectedHistoryByDate(@PathVariable String systemId,
                                                @PathVariable String visitedDate,
                                                @PathVariable String date,
                                                @RequestBody List<String> argoIdList) {
        controllerr.intilizeDataDir(requestHeader, systemId, OperationContants.EXPORT_STRING, visitedDate);
        ImportHistory importHistory = new ImportHistory();
        ExportHistory exportHistory = new ExportHistory();
        controllerr.createImportAndExportHistory(importHistory, exportHistory, argoIdList, OperationContants.EXPORT_STRING);
        boolean isImported = operationHandler.importFromHistory(argoIdList, importHistory, exportHistory, OperationContants.EXPORT_STRING, date);
        if(isImported) {
            return "Import Successfull";
        } else {
            return "Import is not Successfull";
        }
    }
}
