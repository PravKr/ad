package com.ad.controller;

import com.ad.constants.CommonConstants;
import com.ad.dao.ArgoDao;
import com.ad.dao.CartDao;
import com.ad.dao.EntitiesDao;
import com.ad.dao.SystemDao;
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

@CrossOrigin(origins = "*", maxAge = 3600)
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
    @Autowired
    SystemDao systemDao;
    @Autowired
    DateUtil dateUtil;

    public static String visitNowDate;

    static Map<String, String> daoMap;
    static {
        daoMap = new HashMap<>();
        daoMap.put("CodeExtension", "ExtensionDao");
        daoMap.put("DatabaseBackedVariform", "DbVariformFileDefinitionDao");
        daoMap.put("GeneralReference", "GeneralReferenceDao");
        daoMap.put("GroovyPlugins", "DigitalAssetDao");
        daoMap.put("EventType", "EventTypeDao");
        daoMap.put("GeneralNotice", "NoticeRequestDao");
        daoMap.put("ExtensionTrigger", "ExtensionInjectionDao");
        daoMap.put("GroovyJob", "ArgoGroovyJobDefinitionDao");
        daoMap.put("Fields", "DbMetafieldDao");
        daoMap.put("EdiSession", "EdiSessionDao");
        daoMap.put("EdiMessageType", "EdiMessageTypeDao");
        daoMap.put("EquipCondition", "EquipConditionDao");
        daoMap.put("StorageRule", "StorageRuleDao");
        daoMap.put("HoldPermissionView", "HoldPermissionViewDao");
        daoMap.put("ReportDefinition", "ReportDefinitionDao");
        daoMap.put("ReportingJobDefinition", "ReportingJobDefinitionDao");
        daoMap.put("ReportDesign", "ReportDesignDao");
        daoMap.put("ReportableEntity", "ReportableEntityDao");
        daoMap.put("BizGroup", "BizGroupDao");
        daoMap.put("Roles", "ArgoSecRoleDao");
        daoMap.put("Console", "ConsoleDao");
        daoMap.put("Documents", "DocumentTypeDao");
        daoMap.put("Printers", "PrinterDao");
        daoMap.put("GateConfiguration", "GateConfigurationDao");
        daoMap.put("Gates", "GateDao");
        daoMap.put("TradingPartner", "EdiTradingPartnerDao");
    }

    @RequestMapping(value = "/{systemType}/{systemId}", method = RequestMethod.POST)
    @ResponseBody
    public Set<String> getVisitHistory(@PathVariable String systemType,
                                      @PathVariable String systemId) {
        controllerr.intilizeDataDir(requestHeader, systemId, systemType);
        return systemDao.getVisitedHistory(systemId);
    }

    @RequestMapping(value = "/{systemType}/{systemId}/{visitDate}", method = RequestMethod.POST)
    @ResponseBody
    public Set<String> getVisitHistoryByDate(@PathVariable String systemType,
                                        @PathVariable String systemId,
                                        @PathVariable String visitDate) {
        if(CommonConstants.VISIT_NOW_STRING.equalsIgnoreCase(visitDate)) {
            visitNowDate = dateUtil.getCurrentIndiaTimeInString();
            controllerr.intilizeDataDir(requestHeader, systemId, systemType, visitNowDate);
            Map<String, List<BaseEntity>> entityMap = new HashMap<>();
            Argo argo = argoDao.getArgo(systemType, systemId);
            operationHandler.startExport(argo, entityMap);
            systemDao.saveToSystem(systemId, visitNowDate);
            return entityMap.keySet();
        } else {
            controllerr.intilizeDataDir(requestHeader, systemId, systemType, visitDate);
            return systemDao.getEntitiesByDate();
        }
    }

    @RequestMapping(value = "/{systemType}/{systemId}/{visitDate}/{entityName}", method = RequestMethod.POST)
    @ResponseBody
    public List<BaseEntity> getEntityRecords(@PathVariable String systemType,
                                             @PathVariable String systemId,
                                             @PathVariable String visitDate,
                                             @PathVariable String entityName) {
        controllerr.intilizeDataDir(requestHeader, systemId, systemType, getVisitedDate(visitDate));
        EntitiesDao entitiesDao = (EntitiesDao) applicationContext.getBean(daoMap.get(entityName));
        return entitiesDao.allRecordsFromEntity();
    }

    @RequestMapping(value = "/{systemType}/{systemId}/{visitDate}/{entityName}/wildcard", method = RequestMethod.POST)
    @ResponseBody
    public List<BaseEntity> getEntityRecordsByWhileCardChar(@PathVariable String systemType,
                                                            @PathVariable String systemId,
                                                            @PathVariable String visitDate,
                                                            @PathVariable String entityName,
                                                            @RequestBody SearchModel searchModel) {
        controllerr.intilizeDataDir(requestHeader, systemId, systemType, getVisitedDate(visitDate));
        EntitiesDao entitiesDao = (EntitiesDao) applicationContext.getBean(daoMap.get(entityName));
        if(searchModel.getText().isEmpty()) {
            return entitiesDao.allRecordsFromEntity();
        }
        return entitiesDao.allRecordsFromEntityByWildcardChar(searchModel.getText());
    }

    @RequestMapping(value = "/{systemType}/{systemId}/{visitDate}/import", method = RequestMethod.POST)
    @ResponseBody
    public String importSelectedEntities(@PathVariable String systemType,
                                       @PathVariable String systemId,
                                         @PathVariable String visitDate,
                                       @RequestBody List<String> argoIdList) {
        controllerr.intilizeDataDir(requestHeader, systemId, systemType, getVisitedDate(visitDate));
        ImportHistory importHistory = new ImportHistory();
        ExportHistory exportHistory = new ExportHistory();
        controllerr.createImportAndExportHistory(importHistory, exportHistory, argoIdList, systemId, getVisitedDate(visitDate));
        boolean isImported = operationHandler.importt(argoIdList, importHistory, exportHistory);
        if(isImported) {
            systemDao.removeFromSystem(systemId, getVisitedDate(visitDate));
            return "Import Successfull";
        } else {
            return "Import is not Successfull";
        }
    }

    @RequestMapping(value = "/{systemType}/{systemId}/{visitDate}/export", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<byte[]> exportSelectedEntities(@PathVariable String systemType,
                                                         @PathVariable String systemId,
                                                         @PathVariable String visitDate) {
        controllerr.intilizeDataDir(requestHeader, systemId, systemType, getVisitedDate(visitDate));
        ImportHistory importHistory = new ImportHistory();
        ExportHistory exportHistory = new ExportHistory();
        controllerr.createImportAndExportHistory(importHistory, exportHistory, null, systemId, getVisitedDate(visitDate));

        String xml = operationHandler.export(importHistory, exportHistory);
        byte[] isr = xml.getBytes();
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentLength(isr.length);
        respHeaders.setContentType(new MediaType("text", "xml"));
        respHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        respHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=entities.xml");
        return new ResponseEntity<byte[]>(isr, respHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/{systemType}/{systemId}/{visitDate}/addToCart/{entity}", method = RequestMethod.POST)
    @ResponseBody
    public Object addToCart(@PathVariable String systemType,
                            @PathVariable String systemId,
                            @PathVariable String visitDate,
                          @RequestBody Set<String> entityGkeySet,
                          @PathVariable String entity) {
        controllerr.intilizeDataDir(requestHeader, systemId, systemType, getVisitedDate(visitDate));
        boolean isSaved = cartDao.saveToCart(entityGkeySet, entity);
        if(isSaved) {
            return "Added to Cart";
        } else {
            return "Problem while adding to cart";
        }
    }

    @RequestMapping(value = "/{systemType}/{systemId}/{visitDate}/removeFromCart/{entity}", method = RequestMethod.POST)
    @ResponseBody
    public Object removeFromCart(@PathVariable String systemType,
                                 @PathVariable String systemId,
                                 @PathVariable String visitDate,
                               @RequestBody Set<String> entityGkeySet,
                                @PathVariable String entity) {
        controllerr.intilizeDataDir(requestHeader, systemId, systemType, getVisitedDate(visitDate));
        boolean isSaved = cartDao.removeFromCart(entityGkeySet, entity);
        if(isSaved) {
            return "removed from Cart";
        } else {
            return "Problem while removing from cart";
        }
    }

    @RequestMapping(value = "/{systemType}/{systemId}/{visitDate}/removeByEntityFromCart/{entity}", method = RequestMethod.POST)
    @ResponseBody
    public Object removeCartByEntity(@PathVariable String systemType,
                                 @PathVariable String systemId,
                                     @PathVariable String visitDate,
                                 @PathVariable String entity) {
        controllerr.intilizeDataDir(requestHeader, systemId, systemType, getVisitedDate(visitDate));
        boolean isSaved = cartDao.removeAllByEntity(entity);
        if(isSaved) {
            return entity + " removed from Cart";
        } else {
            return "some problem came while removing Cart";
        }
    }

    @RequestMapping(value = "/{systemType}/{systemId}/{visitDate}/emptyCart", method = RequestMethod.POST)
    @ResponseBody
    public Object emptyCart(@PathVariable String systemType,
                            @PathVariable String systemId,
                            @PathVariable String visitDate) {
        controllerr.intilizeDataDir(requestHeader, systemId, systemType, getVisitedDate(visitDate));
        boolean isSaved = cartDao.removeEverytingFromCart();
        if(isSaved) {
            return "Cart is Empty";
        } else {
            return "some problem while removing all data from cart";
        }
    }

    @RequestMapping(value = "/{systemType}/{systemId}/{visitDate}/cart", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Set<Object>> cart(@PathVariable String systemType,
                                         @PathVariable String systemId,
                                         @PathVariable String visitDate) {
        controllerr.intilizeDataDir(requestHeader, systemId, systemType, getVisitedDate(visitDate));
        return cartDao.getEntitiesFromCartWithDetails();
    }

    private String getVisitedDate(String visitDate) {
        return CommonConstants.VISIT_NOW_STRING.equalsIgnoreCase(visitDate) ? visitNowDate : visitDate;
    }
}
