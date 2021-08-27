package com.ad.dao;

import com.ad.constants.CommonConstants;
import com.ad.constants.ErrorEnum;
import com.ad.core.SMException;
import com.ad.models.Argo;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ArgoDao extends BaseDao {

    public Argo addArgo(Argo argo) {
        String dataFile = controllerr.ARGO_DIR + "/" + argo.getId()+".json";
        saveDataToFS(dataFile, argo, Boolean.FALSE);
        return argo;
    }

    public boolean removeArgo(String id) {
        String dataFile = controllerr.ARGO_DIR + "/" + id +".json";
        String systemFile = id + File.separator;
        //argo.setId(argo.getSystemName().replaceAll(" ", ""));
        deleteDataFromFs(dataFile);
        //deleteDirFromFs(systemFile);
        return true;
    }

    public Argo getArgo(String inId) {
        String dataFile = controllerr.ARGO_DIR + "/" + inId +".json";
        return getDataFromFS(dataFile, Argo.class);
    }

    public List<Argo> getAllArgo(){
        List<String> files = getAllFileNames(controllerr.ARGO_DIR, CommonConstants.JSON_EXTENSION);
        if(null==files){
            throw new SMException(ErrorEnum.NOT_FOUND);
        }
        return files.stream()
                .map(x->{
                    System.out.println(x);
                    Argo s = getDataFromFS(controllerr.ARGO_DIR+"/"+x, Argo.class);
                    return s;
                })
                .sorted(Comparator.comparing(Argo::getId))
                .collect(Collectors.toList());
    }
}
