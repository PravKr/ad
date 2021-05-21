package com.ad.dao;

import com.ad.constants.ErrorEnum;
import com.ad.core.SMException;
import com.ad.models.Argo;
import com.ad.models.BaseEntity;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ArgoDao extends BaseDao {

    public Argo addArgo(String type, Argo argo) {
        String dataFile = controllerr.ARGO_DIR + "/" + type+"/" + argo.getId()+".json";
        //argo.setId(argo.getSystemName().replaceAll(" ", ""));
        argo.setSystemType(type);
        saveDataToFS(dataFile, argo, Boolean.FALSE);
        return argo;
    }

    public boolean removeArgo(String type, String id) {
        String dataFile = controllerr.ARGO_DIR + "/" + type+"/" + id +".json";
        String systemFile = id + File.separator + type;
        //argo.setId(argo.getSystemName().replaceAll(" ", ""));
        deleteDataFromFs(dataFile);
        deleteDirFromFs(systemFile);
        return true;
    }

    public Argo getArgo(String inType, String inId) {
        String dataFile = controllerr.ARGO_DIR + "/" + inType+"/" + inId +".json";
        return getDataFromFS(dataFile, Argo.class);
    }

    public List<Argo> getAllArgo(String type){
        String dirName = controllerr.ARGO_DIR + "/" + type;
        List<String> files = getAllFileNames(dirName);
        if(null==files){
            throw new SMException(ErrorEnum.NOT_FOUND);
        }
        return files.stream()
                .map(x->{
                    System.out.println(x);
                    Argo s = getDataFromFS(dirName+"/"+x, Argo.class);
                    return s;
                })
                .sorted(Comparator.comparing(Argo::getId))
                .collect(Collectors.toList());
    }
}
