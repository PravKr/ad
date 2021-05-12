package com.ad.controller;

import com.ad.dao.ArgoDao;
import com.ad.models.Argo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @RequestMapping(value = "/add/argo/{type}", method = RequestMethod.POST)
    @ResponseBody
    public Argo addArgo(@RequestBody Argo argo, @PathVariable String type) {
        controllerr.intilizeDataDir(requestHeader);
        return argoDao.addArgo(type, argo);
    }

    @RequestMapping(value = "/remove/argo/{type}", method = RequestMethod.POST)
    @ResponseBody
    public Argo removeArgo(@RequestBody Argo argo, @PathVariable String type) {
        controllerr.intilizeDataDir(requestHeader);
        return argoDao.addArgo(type, argo);
    }

    @RequestMapping(value = "/argo/{type}", method = RequestMethod.POST)
    @ResponseBody
    public List<Argo> getArgosByType(@PathVariable String type) {
        controllerr.intilizeDataDir(requestHeader);
        return argoDao.getAllArgo(type);
    }

    @RequestMapping(value = "/argos", method = RequestMethod.POST)
    @ResponseBody
    public List<Argo> getArgos() {
        controllerr.intilizeDataDir(requestHeader);
        List<Argo> allArgoList = new ArrayList<>();
        allArgoList.addAll(argoDao.getAllArgo("import"));
        allArgoList.addAll(argoDao.getAllArgo("export"));
        return allArgoList;
    }
}
