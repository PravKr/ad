package com.ad.controller;

import com.ad.dao.ArgoDao;
import com.ad.models.Argo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5000", maxAge = 3600)
@Controller
@RequestMapping("ad")
public class ArgoController {

    @Autowired
    ArgoDao argoDao;

    @RequestMapping(value = "/argo/{type}", method = RequestMethod.POST)
    @ResponseBody
    public Argo addArgo(@RequestBody Argo argo, @PathVariable String type) {
        return argoDao.addArgo(type, argo);
    }

    @RequestMapping(value = "/argo/{type}", method = RequestMethod.GET)
    @ResponseBody
    public List<Argo> getArgosByType(@PathVariable String type) {
        return argoDao.getAllArgo(type);
    }

    @RequestMapping(value = "/argos", method = RequestMethod.GET)
    @ResponseBody
    public List<Argo> getArgos() {
        List<Argo> allArgoList = new ArrayList<>();
        allArgoList.addAll(argoDao.getAllArgo("import"));
        allArgoList.addAll(argoDao.getAllArgo("export"));
        return allArgoList;
    }
}
