package com.ad.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;

@Component
public class FileUtil {

    @Autowired
    ResourceLoader resourceLoader;

    public File getFileFromResource(String inLocation) {
        try{
            Resource resource = resourceLoader.getResource(inLocation);
            InputStream input = resource.getInputStream();
           return resource.getFile();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
