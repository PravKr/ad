package com.ad.dao;

import com.ad.models.BaseEntity;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;

@Component
public class CartDao extends EntitiesDao {

    public boolean saveToCart(Set<String> entityGkeySet, String entity) {
        String dataFile = controllerr.CART_DIR + File.separator + entity + ".json";
        Set<String> set = getDataFromFS(dataFile, Set.class);
        if(set != null) {
            entityGkeySet.addAll(set);
        }
        return saveDataToFS(dataFile, entityGkeySet, Boolean.FALSE);
    }

    public boolean removeFromCart(Set<String> entityGkeySet, String entity) {
        String dataFile = controllerr.CART_DIR + File.separator + entity + ".json";
        Set<String> set = getDataFromFS(dataFile, Set.class);
        if(set != null) {
            set.removeAll(entityGkeySet);
        }
        return saveDataToFS(dataFile, set, Boolean.TRUE);
    }

    public Map<String, Set<String>> getEntitiesFromCart() {
        Map<String, Set<String>> inEntityMap = new HashMap<>();
        List<String> allFiles = getAllFileNames(controllerr.CART_DIR);
        String dataFile = controllerr.CART_DIR + File.separator;
        for(String file: allFiles) {
            Set<String> set = getDataFromFS(dataFile + File.separator + file, Set.class);
            inEntityMap.put(file.substring(0, file.indexOf('.')), set);
        }
        return inEntityMap;
    }

    public Map<String, Set<Object>> getEntitiesFromCartWithDetails() {
        Map<String, Set<Object>> inEntityMap = new HashMap<>();
        List<String> allFiles = getAllFileNames(controllerr.CART_DIR);
        String dataFile = controllerr.CART_DIR + File.separator;
        for(String file: allFiles) {
            Set<Object> baseEntitySet = new HashSet<>();
            Set<String> set = getDataFromFS(dataFile + File.separator + file, Set.class);
            String entityName = file.substring(0, file.indexOf("."));
            for(String key: set) {
                String jsonFile = controllerr.ENTITY_JSON_DIR + File.separator + entityName + File.separator + key + JSON_EXTENSION;
                Object object = getDataFromFS(jsonFile, Object.class);
                baseEntitySet.add(object);
            }
            inEntityMap.put(entityName, baseEntitySet);
        }
        return inEntityMap;
    }

    public List<BaseEntity> allRecordsFromEntity(){
        return null;
    }
}
