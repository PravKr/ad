package com.ad.dao;

import com.ad.util.DateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.shl.exam.util.DateUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class BaseDao {
    @Value("${dataDir:/Users/kumarpr/ad/data}")
    public String dataDir;

    @Autowired
    public DateUtil dateUtil;

    ObjectMapper objectMapper = new ObjectMapper();

    public List<String> getListOfPapers(String filePattern) {
        try {
            return Files.list(Paths.get(dataDir))
                    .filter(Files::isRegularFile)
                    .filter(x->x.getFileName().toString().matches(filePattern))
                    .map(x-> x.getFileName().toString())
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getAllFileNames(String dir){
        return  getAllFileNames(dir,x->true);
    }

    public List<String> getAllFileNames(String dir,Predicate<Path> predicate) {
        String fullFileName=dataDir.concat("/").concat(dir);
        try {
            return Files.list(Paths.get(fullFileName))
                    .filter(Files::isRegularFile)
                    .filter(x->x.getFileName().toString().endsWith(".json"))
                    .filter(predicate)
                    .map(x-> x.getFileName().toString())
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getFileContentById(String filePath) {
        String fullFileName = dataDir.concat("/").concat(filePath);
        try {
            return FileUtils.readFileToString(new File(fullFileName));
        } catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> boolean saveDataToFS(String fileName,T t, boolean isTruncate){
        boolean isSuccess=false;
        String fullFileName=dataDir.concat("/").concat(fileName);
        try {
            String data=objectMapper.writeValueAsString(t);
            File parentFile= new File(fullFileName).getParentFile();
            if(!parentFile.exists()){
                parentFile.mkdirs();
            }
            if(isTruncate) {
                Files.write(Paths.get(fullFileName),data.getBytes(),StandardOpenOption.TRUNCATE_EXISTING);
            } else{
                Files.write(Paths.get(fullFileName),data.getBytes(),StandardOpenOption.CREATE);
            }
            isSuccess=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    public void deleteDataFromFs(String fileName){
        String fullFileName=dataDir.concat("/").concat(fileName);
        try {
            Files.delete((Paths.get(fullFileName)));
        } catch (IOException e) {
            System.out.println("Paper Not Found!");
        }
    }

    public boolean isAvail(String fileName){
        String fullFileName=dataDir.concat("/").concat(fileName);
        return Files.exists(Paths.get(fullFileName));
    }

    public <T> T getDataFromFS(String fileName, Class<T> tClass) {
        String fullFileName=dataDir.concat("/").concat(fileName);
        System.out.println("File: " + fullFileName);
        if(isAvail(fileName)){
            try {
                String content = new String(Files.readAllBytes(Paths.get(fullFileName)));
                try {
                    T t=objectMapper.readValue(content,tClass);
                    System.out.println(tClass.getName() + ":" + t.toString());
                    return t;
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                System.out.println("Not Found!");
            }
        }
        return null;
    }

    public long getFileCount(String dir){
        String fullDirPath=dataDir.concat("/").concat(dir);
        try {
            return Files.list(Paths.get(fullDirPath))
                    .filter(Files::isRegularFile)
                    .filter(x->x.toString().endsWith(".json"))
                    .count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Map<String,String> getAnswerMap(String strResult){
        return Pattern.compile("\\s*,\\s*")
                .splitAsStream(strResult.trim())
                .map(s -> s.split(":", 2))
                .collect(Collectors.toMap(a -> a[0].replace("q",""), a -> a.length>1? a[1]: ""))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    public String getValidUserName(String userName){
        return userName.replaceAll("([a-zA-Z0-9._-]*)(@)([a-zA-Z0-9.]+)","$1")
                .replace(".","-");
    }

    public String getUserKey(String validUserName) {
        //write logic to replace the token
        return validUserName;
    }

}
