package com.ad.v1;

import com.ad.dao.CartDao;
import com.ad.dao.EntitiesDao;
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
@RequestMapping("ad/v1")
public class EntitiesControllerV1 {
    @Autowired
    OperationHandler operationHandler;

    @Autowired
    CartDao cartDao;

    @Autowired
    HttpServletRequest requestHeader;

    @RequestMapping(value = "/entities/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object getAllEntities(@PathVariable String id) {
               return "[\n" +
                       "    \"CodeExtension\",\n" +
                       "    \"GeneralReference\",\n" +
                       "    \"DatabaseBackedVariform\",\n" +
                       "    \"GroovyPlugins\"\n" +
                       "]";
    }

    /*@RequestMapping(value = "/{systemId}/{entityName}", method = RequestMethod.POST)
    @ResponseBody
    public Object getEntityRecords(@PathVariable String systemId, @PathVariable String entityName) {
        return "[\n" +
                "    \"CodeExtension\",\n" +
                "    \"GeneralReference\",\n" +
                "    \"DatabaseBackedVariform\",\n" +
                "    \"GroovyPlugins\"\n" +
                "]";
    }*/

    @RequestMapping(value = "/entities/import", method = RequestMethod.POST)
    @ResponseBody
    public void importSelectedEntities(@RequestBody List<String> argoList) {
        //operationHandler.importt(argoList);
    }

    @RequestMapping(value = "/entities/export", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<byte[]> exportSelectedEntities() {
        String xml = operationHandler.export(null, null);
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
    public ResponseEntity<byte[]> exportAndImportSelectedEntities(@RequestBody List<String> argoList) {
        String xml = operationHandler.exportAndImport(argoList, null, null);
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

    @RequestMapping(value = "/entities/cart", method = RequestMethod.GET)
    @ResponseBody
    public Object cart() {
        return "{\n" +
                "    \"Digital Asset\": [\n" +
                "        {\n" +
                "            \"gkey\": 2,\n" +
                "            \"name\": \"ShowGkeyTableViewCommand1\",\n" +
                "            \"short-description\": \"ShowGkeyTableViewCommand1\",\n" +
                "            \"is-pre-deployed\": \"N\",\n" +
                "            \"format\": \"GROOVY\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"gkey\": 1,\n" +
                "            \"name\": \"ShowGkeyTableViewCommand\",\n" +
                "            \"short-description\": \"ShowGkeyTableViewCommand\",\n" +
                "            \"is-pre-deployed\": \"N\",\n" +
                "            \"format\": \"GROOVY\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"General Reference\": [\n" +
                "        {\n" +
                "            \"gkey\": 2,\n" +
                "            \"refType\": \"AUTO_DEPLOY_TEST\",\n" +
                "            \"refId1\": \"AUTO_DEPLOY_TEST\",\n" +
                "            \"refValue1\": \"AUTO_DEPLOY_TEST\",\n" +
                "            \"refCreator\": \"admin\",\n" +
                "            \"refCreated\": \"2021-04-15T11:42:40\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"gkey\": 1,\n" +
                "            \"refType\": \"AUTO_DEPLOY\",\n" +
                "            \"refId1\": \"RESOURCE_PROPERTIES_PATH\",\n" +
                "            \"refValue1\": \"C:\\\\My Place\\\\Task\\\\Auto deploy Tool\\\\Test_15_April\",\n" +
                "            \"refCreator\": \"admin\",\n" +
                "            \"refCreated\": \"2021-04-15T11:42:40\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"Code Extension\": [\n" +
                "        {\n" +
                "            \"gkey\": 1,\n" +
                "            \"name\": \"SnxGeneralReferenceExporter\",\n" +
                "            \"scope\": null,\n" +
                "            \"type\": \"BEAN_PROTOTYPE\",\n" +
                "            \"language\": \"GROOVY\",\n" +
                "            \"is-enabled\": \"Y\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"gkey\": 3,\n" +
                "            \"name\": \"SnxExtensionExporter\",\n" +
                "            \"scope\": null,\n" +
                "            \"type\": \"BEAN_PROTOTYPE\",\n" +
                "            \"language\": \"GROOVY\",\n" +
                "            \"is-enabled\": \"Y\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"Database Backed Variform\": [\n" +
                "        {\n" +
                "            \"gkey\": 1,\n" +
                "            \"cfgvar-name\": \"CUSTOM_FORM_PREANNOUNCEMENT\",\n" +
                "            \"cfgvar-description\": \"CUSTOM_FORM_PREANNOUNCEMENT\",\n" +
                "            \"cfgvar-enabled\": \"Y\",\n" +
                "            \"cfgvar-variform-ids\": \"|CUSTOM_FORM_PREANNOUNCEMENT|\",\n" +
                "            \"cfgvar-creator\": \"admin\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"gkey\": 2,\n" +
                "            \"cfgvar-name\": \"CUSTOM_INSPECTOR_VESSEL_VISIT\",\n" +
                "            \"cfgvar-description\": \"Added Custom Menu - Lashing\",\n" +
                "            \"cfgvar-enabled\": \"Y\",\n" +
                "            \"cfgvar-variform-ids\": \"|INSPECTOR_VESSEL_VISIT|\",\n" +
                "            \"cfgvar-creator\": \"admin\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
    }
}
