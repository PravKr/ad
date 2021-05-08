package com.ad.request.handler;

import com.ad.dao.*;
import com.ad.models.Argo;
import com.ad.models.BaseEntity;
import com.ad.util.XMLUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.*;

/*
 * OperationHandler
 *
 * @author <a href="mailto:pravin.kumar@navis.com">Pravin Kumar</a>
 *
 * Date: 15-06-2020
 *
 * Called from: Various Classes
 *
 * Description: This will control all operation like Import, Export and Import & Export
 *
 */
@Component
public class OperationHandler {

    @Autowired
    SoapCallHandler soapCallHandler;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    ImportEntityDao importEntityDao;

    @Autowired
    XMLUtil xmlUtil;

    @Autowired
    ArgoDao argoDao;


    @Value("${entities}")
    public String entities;

    private static List<String> errorList = new ArrayList<>();

    /**
     * To Export the selected component
     *
     */
    public void startExport(Argo inArgo, Map<String, List<BaseEntity>> inEntityMap) {
        LOGGER.info("Export is started");
        soapCallHandler.initilizeTopology(inArgo);
        Map<String, String> soapRequestParam = new HashMap<>();
        for (String entity : entities.split(",")) {
            soapRequestParam.put(CODE_EXTENSION, SNX_EXPORT_PROCESSOR);
            soapRequestParam.put(ENTITY_NAME, entity);
            soapRequestParam.put(ENTITY_ITEM, "All");
            String response = soapCallHandler.executeSoapCall(soapRequestParam);
            if (response == null || errorList.contains(response)) {
                LOGGER.error(response);
                LOGGER.warn(" entity not found ");
            } else {
                //Code for converting the xml to json
                convertToSpecificDao(response, entity, inEntityMap);
            }
            soapRequestParam.clear();
        }

        LOGGER.info("Export is ended");
    }

    public void importt(List<String> inArgoList) {
        List<String> allEntities = importEntityDao.getListOfEntities();
        String importXml = xmlUtil.convertListToSNX(allEntities);
        for(String argoId: inArgoList) {
            Argo argo = argoDao.getArgo("import", argoId);
            startImport(argo, importXml);
        }
    }

    public String export() {
        List<String> allEntities = importEntityDao.getListOfEntities();
        return xmlUtil.convertListToSNX(allEntities);
    }

    public String exportAndImport(List<String> inArgoIdList) {
        List<String> allEntities = importEntityDao.getListOfEntities();
        String importXml = xmlUtil.convertListToSNX(allEntities);
        for(String argoId: inArgoIdList) {
            Argo argo = argoDao.getArgo("import", argoId);
            startImport(argo, importXml);
        }
        return importXml;
    }

    /**
     * To Start the Import
     */
    public void startImport(Argo inArgo, String inImportXml) {
        LOGGER.info("Import is started");
        //code for generating selected component SNX
        soapCallHandler.initilizeTopology(inArgo);
        HashMap<String, String> soapRequestMap = new HashMap<>();
        soapRequestMap.put(CODE_EXTENSION, SNX_IMPORT_PROCESSOR);
        soapRequestMap.put(ENTITY_NAME, inImportXml);
        String response = soapCallHandler.executeSoapCall(soapRequestMap);
        if(response != null) {
            LOGGER.info("Import successful");
        } else if (errorList.contains(response)) {
            LOGGER.error(response);
        }
        LOGGER.info("Import is ended");
    }

    private void convertToSpecificDao(String inResponse,
                                      String inEntity,
                                      Map<String, List<BaseEntity>> inEntityMap) {
        try {
             EntitiesDao entitiesDao = (EntitiesDao) applicationContext.getBean(inEntity + "Dao");
            entitiesDao.convertXMLtoJSON(inResponse, inEntityMap);
        } catch (Exception e) {
            LOGGER.error("Class not found", e.getMessage());
        }
    }

    static {
        errorList.add("Entity name is Mandatory Parameter");
        errorList.add("Snx Value is Mandatory Parameter");
    }

    private static final String CODE_EXTENSION = "code-extension";
    private static final String SNX_EXPORT_PROCESSOR = "GenericExportProcessor";
    private static final String SNX_IMPORT_PROCESSOR = "GenericImportProcessor";
    private static final String ENTITY_NAME = "entity-name";
    private static final String ENTITY_ITEM = "entity-item";
    private static final String IMPORT_OPERATION = "import";
    private static final String EXPORT_OPERATION = "export";
    private static final String EXPORT_IMPORT_OPERATION = "exportandimport";
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationHandler.class);
}