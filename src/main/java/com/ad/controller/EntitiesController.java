package com.ad.controller;

import com.ad.dao.ArgoDao;
import com.ad.dao.CartDao;
import com.ad.dao.EntitiesDao;
import com.ad.models.*;
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
@RequestMapping("ad/entities")
public class EntitiesController {
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

    static Map<String, String> daoMap;
    static {
        daoMap = new HashMap<>();
        daoMap.put("CodeExtension", "ExtensionDao");
        daoMap.put("DatabaseBackedVariform", "DbVariformFileDefinitionDao");
        daoMap.put("GeneralReference", "GeneralReferenceDao");
        daoMap.put("GroovyPlugins", "DigitalAssetDao");
        daoMap.put("EventType", "EventTypeDao");
        daoMap.put("GeneralNotice", "NoticeRequestDao");
    }

    @RequestMapping(value = "/{systemType}/{systemId}", method = RequestMethod.POST)
    @ResponseBody
    public Set<String> getAllEntities(@PathVariable String systemType,
                                      @PathVariable String systemId) {
        controllerr.intilizeDataDir(requestHeader, systemId, systemType);
        Map<String, List<BaseEntity>> entityMap = new HashMap<>();
        Argo argo = argoDao.getArgo(systemType, systemId);
        operationHandler.startExport(argo, entityMap);
        return entityMap.keySet();
    }

    @RequestMapping(value = "/{systemType}/{systemId}/{entityName}", method = RequestMethod.POST)
    @ResponseBody
    public List<BaseEntity> getEntityRecords(@PathVariable String systemType,
                                             @PathVariable String systemId,
                                             @PathVariable String entityName) {
        controllerr.intilizeDataDir(requestHeader, systemId, systemType);
        EntitiesDao entitiesDao = (EntitiesDao) applicationContext.getBean(daoMap.get(entityName));
        return entitiesDao.allRecordsFromEntity();
    }

    @RequestMapping(value = "/{systemType}/{systemId}/{entityName}/wildcard", method = RequestMethod.POST)
    @ResponseBody
    public List<BaseEntity> getEntityRecordsByWhileCardChar(@PathVariable String systemType,
                                                            @PathVariable String systemId,
                                                            @PathVariable String entityName,
                                                            @RequestBody SearchModel searchModel) {
        controllerr.intilizeDataDir(requestHeader, systemId, systemType);
        EntitiesDao entitiesDao = (EntitiesDao) applicationContext.getBean(daoMap.get(entityName));
        if(searchModel.getText().isEmpty()) {
            return entitiesDao.allRecordsFromEntity();
        }
        return entitiesDao.allRecordsFromEntityByWildcardChar(searchModel.getText());
    }

    @RequestMapping(value = "/{systemType}/{systemId}/import", method = RequestMethod.POST)
    @ResponseBody
    public String importSelectedEntities(@PathVariable String systemType,
                                       @PathVariable String systemId,
                                       @RequestBody List<String> argoIdList) {
        controllerr.intilizeDataDir(requestHeader, systemId, systemType);
        ImportHistory importHistory = new ImportHistory();
        ExportHistory exportHistory = new ExportHistory();
        controllerr.createImportAndExportHistory(importHistory, exportHistory, argoIdList, systemId);
        boolean isImported = operationHandler.importt(argoIdList, importHistory, exportHistory);
        if(isImported) {
            return "Import Successfull";
        } else {
            return "Import is not Successfull";
        }
    }

    @RequestMapping(value = "/{systemType}/{systemId}/export", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<byte[]> exportSelectedEntities(@PathVariable String systemType,
                                                         @PathVariable String systemId) {
        controllerr.intilizeDataDir(requestHeader, systemId, systemType);
        ImportHistory importHistory = new ImportHistory();
        ExportHistory exportHistory = new ExportHistory();
        controllerr.createImportAndExportHistory(importHistory, exportHistory, null, systemId);

        String xml = operationHandler.export(importHistory, exportHistory);
        byte[] isr = xml.getBytes();
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentLength(isr.length);
        respHeaders.setContentType(new MediaType("text", "xml"));
        respHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        respHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=entities.xml");
        return new ResponseEntity<byte[]>(isr, respHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/{systemType}/{systemId}/exportandimport", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<byte[]> exportAndImportSelectedEntities(@PathVariable String systemType,
                                                                  @PathVariable String systemId,
                                                                  @RequestBody List<String> argoIdList) {
        controllerr.intilizeDataDir(requestHeader, systemId, systemType);
        ImportHistory importHistory = new ImportHistory();
        ExportHistory exportHistory = new ExportHistory();
        controllerr.createImportAndExportHistory(importHistory, exportHistory, argoIdList, systemId);
        String xml = operationHandler.exportAndImport(argoIdList, importHistory, exportHistory);
        byte[] isr = xml.getBytes();
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentLength(isr.length);
        respHeaders.setContentType(new MediaType("text", "xml"));
        respHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        respHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=entities.xml");
        return new ResponseEntity<byte[]>(isr, respHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/{systemType}/{systemId}/addToCart/{entity}", method = RequestMethod.POST)
    @ResponseBody
    public Object addToCart(@PathVariable String systemType,
                            @PathVariable String systemId,
                          @RequestBody Set<String> entityGkeySet,
                          @PathVariable String entity) {
        controllerr.intilizeDataDir(requestHeader, systemId, systemType);
        boolean isSaved = cartDao.saveToCart(entityGkeySet, entity);
        if(isSaved) {
            return "Added to Cart";
        } else {
            return "Problem while adding to cart";
        }
    }

    @RequestMapping(value = "/{systemType}/{systemId}/removeFromCart/{entity}", method = RequestMethod.POST)
    @ResponseBody
    public Object removeFromCart(@PathVariable String systemType,
                                 @PathVariable String systemId,
                               @RequestBody Set<String> entityGkeySet,
                                @PathVariable String entity) {
        controllerr.intilizeDataDir(requestHeader, systemId, systemType);
        boolean isSaved = cartDao.removeFromCart(entityGkeySet, entity);
        if(isSaved) {
            return "removed from Cart";
        } else {
            return "Problem while removing from cart";
        }
    }

    @RequestMapping(value = "/{systemType}/{systemId}/removeByEntityFromCart/{entity}", method = RequestMethod.POST)
    @ResponseBody
    public Object removeCartByEntity(@PathVariable String systemType,
                                 @PathVariable String systemId,
                                 @PathVariable String entity) {
        controllerr.intilizeDataDir(requestHeader, systemId, systemType);
        boolean isSaved = cartDao.removeAllByEntity(entity);
        if(isSaved) {
            return entity + " removed from Cart";
        } else {
            return "some problem came while removing Cart";
        }
    }

    @RequestMapping(value = "/{systemType}/{systemId}/emptyCart", method = RequestMethod.POST)
    @ResponseBody
    public Object emptyCart(@PathVariable String systemType,
                            @PathVariable String systemId) {
        controllerr.intilizeDataDir(requestHeader, systemId, systemType);
        boolean isSaved = cartDao.removeEverytingFromCart();
        if(isSaved) {
            return "Cart is Empty";
        } else {
            return "some problem while removing all data from cart";
        }
    }

    @RequestMapping(value = "/{systemType}/{systemId}/cart", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Set<Object>> cart(@PathVariable String systemType,
                                         @PathVariable String systemId) {
        controllerr.intilizeDataDir(requestHeader, systemId, systemType);
        return cartDao.getEntitiesFromCartWithDetails();
    }
}
