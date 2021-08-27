package com.ad.controller;

import com.ad.dao.ArgoDao;
import com.ad.models.Argo;
import com.ad.request.handler.OperationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
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

    @RequestMapping(value = "/ping/argo/{systemId}", method = RequestMethod.POST)
    @ResponseBody
    public String ping(@PathVariable String systemId) {
        controllerr.intilizeDataDir(requestHeader);
        Argo argo = argoDao.getArgo(systemId);
        return operationHandler.ping(argo);
    }

    @RequestMapping(value = "/add/argo", method = RequestMethod.POST)
    @ResponseBody
    public Argo addArgo(@RequestBody Argo argo) {
        controllerr.intilizeDataDir(requestHeader);
        return argoDao.addArgo(argo);
    }

    @RequestMapping(value = "/remove/argo/{systemId}", method = RequestMethod.POST)
    @ResponseBody
    public String removeArgo(@PathVariable String systemId) {
        controllerr.intilizeDataDir(requestHeader);
        if(argoDao.removeArgo(systemId)){
            return "System removed successfully";
        } else {
            return "Some problem happend";
        }
    }

    @RequestMapping(value = "/argo", method = RequestMethod.POST)
    @ResponseBody
    public List<Argo> getArgosBySystemType() {
        controllerr.intilizeDataDir(requestHeader);
        return argoDao.getAllArgo();
    }

    @RequestMapping(value = "/argos", method = RequestMethod.POST)
    @ResponseBody
    public List<Argo> getArgos() {
        controllerr.intilizeDataDir(requestHeader);
        return argoDao.getAllArgo();
    }
}
