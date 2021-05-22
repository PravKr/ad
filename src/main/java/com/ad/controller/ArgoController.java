package com.ad.controller;

import com.ad.constants.OperationContants;
import com.ad.dao.ArgoDao;
import com.ad.models.Argo;
import com.ad.request.handler.OperationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5000", maxAge = 3600)
@Controller
@RequestMapping("ad")
public class ArgoController {

    @Autowired
    ArgoDao argoDao;

    @Autowired
    HttpServletRequest requestHeader;

    @Autowired
    Controllerr controllerr;

    @Autowired
    OperationHandler operationHandler;

    @RequestMapping(value = "/ping/argo/{systemType}/{systemId}", method = RequestMethod.POST)
    @ResponseBody
    public String ping(@PathVariable String systemType,
                       @PathVariable String systemId) {
        controllerr.intilizeDataDir(requestHeader);
        Argo argo = argoDao.getArgo(systemType, systemId);
        return operationHandler.ping(argo);
    }

    @RequestMapping(value = "/add/argo/{systemType}", method = RequestMethod.POST)
    @ResponseBody
    public Argo addArgo(@RequestBody Argo argo, @PathVariable String systemType) {
        controllerr.intilizeDataDir(requestHeader);
        return argoDao.addArgo(systemType, argo);
    }

    @RequestMapping(value = "/remove/argo/{systemType}/{systemId}", method = RequestMethod.POST)
    @ResponseBody
    public String removeArgo(@PathVariable String systemType,
                           @PathVariable String systemId) {
        controllerr.intilizeDataDir(requestHeader);
        if(argoDao.removeArgo(systemType, systemId)){
            return "System removed successfully";
        } else {
            return "Some problem happend";
        }
    }

    @RequestMapping(value = "/argo/{systemType}", method = RequestMethod.POST)
    @ResponseBody
    public List<Argo> getArgosBySystemType(@PathVariable String systemType) {
        controllerr.intilizeDataDir(requestHeader);
        return argoDao.getAllArgo(systemType);
    }

    @RequestMapping(value = "/argos", method = RequestMethod.POST)
    @ResponseBody
    public List<Argo> getArgos() {
        controllerr.intilizeDataDir(requestHeader);
        List<Argo> allArgoList = new ArrayList<>();
        allArgoList.addAll(argoDao.getAllArgo(OperationContants.IMPORT_STRING));
        allArgoList.addAll(argoDao.getAllArgo(OperationContants.EXPORT_STRING));
        return allArgoList;
    }
}
