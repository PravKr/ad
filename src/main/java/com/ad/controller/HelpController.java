package com.ad.controller;

import com.ad.constants.OperationContants;
import com.ad.dao.ArgoDao;
import com.ad.models.Argo;
import com.ad.request.handler.OperationHandler;
import com.ad.util.FileUtil;
import org.apache.commons.io.FileUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5000", maxAge = 3600)
@Controller
@RequestMapping("ad")
public class HelpController {

    @Autowired
    OperationHandler operationHandler;

    @Autowired
    FileUtil fileUtil;

    @RequestMapping(value = "/download/help", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<byte[]> downloadHelp() throws IOException {
        byte[] isr = replaceDocument("classpath:AD.docx");
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentLength(isr.length);
        respHeaders.add("Content-Disposition", "attachment;filename=help.docx");
        return ResponseEntity.ok().headers(respHeaders).body(isr);
    }

    @RequestMapping(value = "/download/n4Plugins", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<byte[]> downloadN4Plugins() throws IOException {
        String xml = readSoapXMLFile("classpath:n4Plugins.xml");
        byte[] isr = xml.getBytes();
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentLength(isr.length);
        respHeaders.setContentType(new MediaType("text", "xml"));
        respHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        respHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=N4Plugins.xml");
        return new ResponseEntity<byte[]>(isr, respHeaders, HttpStatus.OK);
    }

    /**
     * To read Soap xml
     */
    private String readSoapXMLFile(String inSoapFormat) {
        //Read xml file
        File file = fileUtil.getFileFromResource(inSoapFormat);
        if (file != null) {
            try {
                return FileUtils.readFileToString(file);
            }catch(IOException ex){
                return ex.getMessage();
            }
        } else {
            return "argoservice soap request file not found in resource ";
        }
    }

    public byte[] replaceDocument(String path) throws IOException {
        try (XWPFDocument doc = new XWPFDocument(new FileInputStream(fileUtil.getFileFromResource(path)))) {
            for (XWPFParagraph paragraph : doc.getParagraphs()) {
                if (!paragraph.getText().contains("${")) {
                    continue;
                }
            }

            try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                doc.write(out);
                return out.toByteArray();
            }
        }
    }
}
