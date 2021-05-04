package com.ad.controller;

import com.ad.dao.ArgoDao;
import com.ad.dao.CartDao;
import com.ad.models.Argo;
import com.ad.models.BaseEntity;
import com.ad.request.handler.OperationHandler;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("ad")
public class EntitiesController {
    @Autowired
    OperationHandler operationHandler;

    @Autowired
    CartDao cartDao;

    @Autowired
    ArgoDao argoDao;

    @Autowired
    HttpServletRequest requestHeader;

    @RequestMapping(value = "/entities/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, List<BaseEntity>> getAllEntities(@PathVariable String id) {
        //requestHeader.authenticate();
        Map<String, List<BaseEntity>> entityMap = new HashMap<>();
        Argo argo = argoDao.getArgo("export", id);
        operationHandler.startExport(argo, entityMap);
        return entityMap;
    }

    @RequestMapping(value = "/entities/import", method = RequestMethod.POST)
    @ResponseBody
    public void importSelectedEntities(@RequestBody List<String> argoIdList) {
        operationHandler.importt(argoIdList);
    }

    @RequestMapping(value = "/entities/export", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<byte[]> exportSelectedEntities() {
        String xml = operationHandler.export();
        byte[] isr = xml.getBytes();
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentLength(isr.length);
        respHeaders.setContentType(new MediaType("text", "xml"));
        respHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        respHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=entities.xml");
        return new ResponseEntity<byte[]>(isr, respHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/entities/exportandimport", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<byte[]> exportAndImportSelectedEntities(@RequestBody List<String> argoIdList) {
        String xml = operationHandler.exportAndImport(argoIdList);
        byte[] isr = xml.getBytes();
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentLength(isr.length);
        respHeaders.setContentType(new MediaType("text", "xml"));
        respHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        respHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=entities.xml");
        return new ResponseEntity<byte[]>(isr, respHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/entities/addToCart/{entity}", method = RequestMethod.POST)
    @ResponseBody
    public void addToCart(@RequestBody Set<String> entityGkeySet,
                               @PathVariable String entity) {
        cartDao.saveToCart(entityGkeySet, entity);
    }

    @RequestMapping(value = "/entities/removeFromCart/{entity}", method = RequestMethod.POST)
    @ResponseBody
    public void removeFromCart(@RequestBody Set<String> entityGkeySet,
                          @PathVariable String entity) {
        cartDao.removeFromCart(entityGkeySet, entity);
    }

    @RequestMapping(value = "/entities/cart", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Set<Object>> cart() {
        return cartDao.getEntitiesFromCartWithDetails();
    }
}
