package com.ad.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

@Component
public class Controllerr {
    @Value("${dataDir:/Users/kumarpr/ad/data}")
    public String dataDir;

    public String ARGO_DIR;
    public String CART_DIR;
    public String ENTITY_XML_DIR;
    public String ENTITY_JSON_DIR;
    public String HISTORY_DIR;

    public void intilizeDataDir(HttpServletRequest requestHeader, String systemId, String type) {
        String auth = requestHeader.getHeader("AAAuthorization");
        if(auth == null) {
            ARGO_DIR = "argo";
            CART_DIR = systemId + File.separator + type + File.separator + "cart";
            ENTITY_XML_DIR = systemId + File.separator + type + File.separator + "entities/xml";
            ENTITY_JSON_DIR = systemId + File.separator + type + File.separator + "entities/json";
            HISTORY_DIR = systemId + File.separator + type + File.separator + "history";
        } else {
            String encodedUserPass = auth.substring("Basic ".length());
            ARGO_DIR = encodedUserPass + File.separator + type + File.separator + "argo";
            CART_DIR = encodedUserPass + File.separator + systemId + File.separator + type + File.separator + "cart";
            ENTITY_XML_DIR = encodedUserPass + File.separator + systemId + File.separator + type + File.separator + "entities/xml";
            ENTITY_JSON_DIR = encodedUserPass + File.separator + systemId + File.separator + type + File.separator + "entities/json";
            HISTORY_DIR = encodedUserPass + File.separator + systemId + File.separator + type + File.separator + "history";
        }
    }

    public void intilizeDataDir(HttpServletRequest requestHeader) {
        String auth = requestHeader.getHeader("Authorization");
        if(auth == null) {
            ARGO_DIR = "argo";
            CART_DIR = "cart";
            ENTITY_XML_DIR = "entities/xml";
            ENTITY_JSON_DIR = "entities/json";
        } else {
            String encodedUserPass = auth.substring("Basic ".length());
            ARGO_DIR = encodedUserPass + File.separator + "argo";
            CART_DIR = encodedUserPass + File.separator + "cart";
            ENTITY_XML_DIR = encodedUserPass + File.separator + "entities/xml";
            ENTITY_JSON_DIR = encodedUserPass + File.separator + "entities/json";
        }
    }
}
