package com.ad.v1;

import com.ad.dao.ArgoDao;
import com.ad.models.Argo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5000", maxAge = 3600)
@Controller
@RequestMapping("v1/ad")
public class ArgoControllerV1 {

    @Autowired
    ArgoDao argoDao;

    @RequestMapping(value = "/argo/{type}", method = RequestMethod.POST)
    @ResponseBody
    public Argo addArgo(@RequestBody Argo argo, @PathVariable String type) {
        return argoDao.addArgo(type, argo);
    }

    @RequestMapping(value = "/argo/{type}", method = RequestMethod.GET)
    @ResponseBody
    public Object getArgosByType(@PathVariable String type) {
        return "[\n" +
                "    {\n" +
                "        \"id\": \"id\",\n" +
                "        \"endPoint\": \"end-point\",\n" +
                "        \"username\": \"username\",\n" +
                "        \"password\": \"password\",\n" +
                "        \"operator\": \"operator\",\n" +
                "        \"complex\": \"complex\",\n" +
                "        \"facility\": \"facility\",\n" +
                "        \"yard\": \"yard\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"id1\",\n" +
                "        \"endPoint\": \"end-point\",\n" +
                "        \"username\": \"username\",\n" +
                "        \"password\": \"password\",\n" +
                "        \"operator\": \"operator\",\n" +
                "        \"complex\": \"complex\",\n" +
                "        \"facility\": \"facility\",\n" +
                "        \"yard\": \"yard\"\n" +
                "    }\n" +
                "]";
    }

    @RequestMapping(value = "/argos", method = RequestMethod.GET)
    @ResponseBody
    public Object getArgos() {
        return "[\n" +
                "    {\n" +
                "        \"id\": \"id\",\n" +
                "        \"endPoint\": \"end-point\",\n" +
                "        \"username\": \"username\",\n" +
                "        \"password\": \"password\",\n" +
                "        \"operator\": \"operator\",\n" +
                "        \"complex\": \"complex\",\n" +
                "        \"facility\": \"facility\",\n" +
                "        \"yard\": \"yard\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"id1\",\n" +
                "        \"endPoint\": \"end-point\",\n" +
                "        \"username\": \"username\",\n" +
                "        \"password\": \"password\",\n" +
                "        \"operator\": \"operator\",\n" +
                "        \"complex\": \"complex\",\n" +
                "        \"facility\": \"facility\",\n" +
                "        \"yard\": \"yard\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"id3\",\n" +
                "        \"endPoint\": \"end-point\",\n" +
                "        \"username\": \"username\",\n" +
                "        \"password\": \"password\",\n" +
                "        \"operator\": \"operator\",\n" +
                "        \"complex\": \"complex\",\n" +
                "        \"facility\": \"facility\",\n" +
                "        \"yard\": \"yard\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"id4\",\n" +
                "        \"endPoint\": \"end-point\",\n" +
                "        \"username\": \"username\",\n" +
                "        \"password\": \"password\",\n" +
                "        \"operator\": \"operator\",\n" +
                "        \"complex\": \"complex\",\n" +
                "        \"facility\": \"facility\",\n" +
                "        \"yard\": \"yard\"\n" +
                "    }\n" +
                "]";
    }
}

