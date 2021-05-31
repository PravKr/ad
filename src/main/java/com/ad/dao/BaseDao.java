package com.ad.dao;

import com.ad.controller.Controllerr;
import com.ad.util.DateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class BaseDao {
    @Value("${dataDir:/Users/kumarpr/ad/data}")
    public String dataDir;

    @Autowired
    Controllerr controllerr;

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

    public List<String> getAllFileNames(String dir, String fileExtension){
        return  getAllFileNames(dir, fileExtension, x->true);
    }

    public List<String> getAllFileNames(String dir, String fileExtension, Predicate<Path> predicate) {
        String fullFileName=dataDir.concat("/").concat(dir);
        try {
            return Files.list(Paths.get(fullFileName))
                    .filter(Files::isRegularFile)
                    .filter(x->x.getFileName().toString().endsWith(fileExtension))
                    .filter(predicate)
                    .map(x-> x.getFileName().toString())
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public Set<String> getAllDirNames(String dir){
        return  getAllDirNames(dir,x->true);
    }

    public Set<String> getAllDirNames(String dir,Predicate<Path> predicate) {
        String fullFileName=dataDir.concat("/").concat(dir);
        try {
            return Files.list(Paths.get(fullFileName))
                    .filter(Files::isDirectory)
                    .map(x-> x.getFileName().toString())
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return new HashSet<>();
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
            Files.write(Paths.get(fullFileName),data.getBytes(),
                    isTruncate ? StandardOpenOption.TRUNCATE_EXISTING : StandardOpenOption.CREATE);
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
            System.out.println("some problem occured");
        }
    }

    public boolean deleteDirFromFs(String fileName){
        String fullFileName=dataDir.concat("/").concat(fileName);
        try {
            FileUtils.cleanDirectory(new File(fullFileName));
            return Boolean.TRUE;
        } catch (IOException e) {
            System.out.println("some problem occured while delete Directory");
            return Boolean.FALSE;
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

    //Change the name of system folder
    public void moveFiles(String srcDir, String destDir) {
        String fullSrcDirPath=dataDir.concat("/").concat(srcDir);
        String fullDestDirPath=dataDir.concat("/").concat(destDir);
        try {
            File srcFile = new File(fullSrcDirPath);
            File destFile = new File(fullDestDirPath);
            FileUtils.copyDirectory(srcFile, destFile);
        } catch (FileAlreadyExistsException ex) {
            System.out.println("Target file already exists");
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.format("I/O error: %s%n", ex);
        }
    }

    public void deleteDir(String dir) {
        deleteDirFromFs(dir);
    }
}
