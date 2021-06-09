package com.ad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("ad/basicauth")
public class BasicAuthController {

    @Autowired
    HttpServletRequest requestHeader;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public String basicauth() {
        String auth = requestHeader.getHeader("Authorization");
        System.out.println("Authentication::::::::::::::: " + auth);
        return "successfull";
    }
}

