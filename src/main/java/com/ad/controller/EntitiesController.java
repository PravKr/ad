package com.ad.controller;

import com.ad.dao.ArgoDao;
import com.ad.dao.CartDao;
import com.ad.dao.EntitiesDao;
import com.ad.models.Argo;
import com.ad.models.BaseEntity;
import com.ad.request.handler.OperationHandler;
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
    }

    @RequestMapping(value = "/{systemId}", method = RequestMethod.POST)
    @ResponseBody
    public Set<String> getAllEntities(@PathVariable String systemId) {
        controllerr.intilizeDataDir(requestHeader, systemId);
        Map<String, List<BaseEntity>> entityMap = new HashMap<>();
        Argo argo = argoDao.getArgo("export", systemId);
        operationHandler.startExport(argo, entityMap);
        return entityMap.keySet();
    }

    @RequestMapping(value = "/{systemId}/{entityName}", method = RequestMethod.POST)
    @ResponseBody
    public List<BaseEntity> getEntityRecords(@PathVariable String systemId, @PathVariable String entityName) {
        controllerr.intilizeDataDir(requestHeader, systemId);
        EntitiesDao entitiesDao = (EntitiesDao) applicationContext.getBean(daoMap.get(entityName));
        return entitiesDao.allRecordsFromEntity();
    }

    @RequestMapping(value = "/{systemId}/import", method = RequestMethod.POST)
    @ResponseBody
    public void importSelectedEntities(@PathVariable String systemId, @RequestBody List<String> argoIdList) {
        controllerr.intilizeDataDir(requestHeader, systemId);
        operationHandler.importt(argoIdList);
    }

    @RequestMapping(value = "/{systemId}/export", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<byte[]> exportSelectedEntities(@PathVariable String systemId) {
        controllerr.intilizeDataDir(requestHeader, systemId);
        String xml = operationHandler.export();
        byte[] isr = xml.getBytes();
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentLength(isr.length);
        respHeaders.setContentType(new MediaType("text", "xml"));
        respHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        respHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=entities.xml");
        return new ResponseEntity<byte[]>(isr, respHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/{systemId}/exportandimport", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<byte[]> exportAndImportSelectedEntities(@PathVariable String systemId, @RequestBody List<String> argoIdList) {
        controllerr.intilizeDataDir(requestHeader, systemId);
        String xml = operationHandler.exportAndImport(argoIdList);
        byte[] isr = xml.getBytes();
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentLength(isr.length);
        respHeaders.setContentType(new MediaType("text", "xml"));
        respHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        respHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=entities.xml");
        return new ResponseEntity<byte[]>(isr, respHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/{systemId}/addToCart/{entity}", method = RequestMethod.POST)
    @ResponseBody
    public void addToCart(@PathVariable String systemId,
                          @RequestBody Set<String> entityGkeySet,
                          @PathVariable String entity) {
        controllerr.intilizeDataDir(requestHeader, systemId);
        cartDao.saveToCart(entityGkeySet, entity);
    }

    @RequestMapping(value = "/{systemId}/removeFromCart/{entity}", method = RequestMethod.POST)
    @ResponseBody
    public void removeFromCart(@PathVariable String systemId,
                               @RequestBody Set<String> entityGkeySet,
                                @PathVariable String entity) {
        controllerr.intilizeDataDir(requestHeader, systemId);
        cartDao.removeFromCart(entityGkeySet, entity);
    }

    @RequestMapping(value = "/{systemId}/cart", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Set<Object>> cart(@PathVariable String systemId) {
        controllerr.intilizeDataDir(requestHeader, systemId);
        return cartDao.getEntitiesFromCartWithDetails();
    }
}
