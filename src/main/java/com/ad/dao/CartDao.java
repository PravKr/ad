package com.ad.dao;

import com.ad.models.BaseEntity;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;

@Component
public class CartDao extends EntitiesDao {

    public static final String CART_DIR ="cart";

    public void saveToCart(Set<String> entityGkeySet, String entity) {
        String dataFile = CART_DIR + File.separator + entity + ".json";
        Set<String> set = getDataFromFS(dataFile, Set.class);
        if(set != null) {
            entityGkeySet.addAll(set);
        }
        saveDataToFS(dataFile, entityGkeySet);
    }

    public void removeFromCart(Set<String> entityGkeySet, String entity) {
        String dataFile = CART_DIR + File.separator + entity + ".json";
        Set<String> set = getDataFromFS(dataFile, Set.class);
        if(set != null) {
            entityGkeySet.remove(set);
        }
        saveDataToFS(dataFile, entityGkeySet);
    }

    public Map<String, Set<String>> getEntitiesFromCart() {
        Map<String, Set<String>> inEntityMap = new HashMap<>();
        List<String> allFiles = getAllFileNames(CART_DIR);
        String dataFile = CART_DIR + File.separator;
        for(String file: allFiles) {
            Set<String> set = getDataFromFS(dataFile + File.separator + file, Set.class);
            inEntityMap.put(file.substring(0, file.indexOf('.')), set);
        }
        return inEntityMap;
    }

    public Map<String, Set<Object>> getEntitiesFromCartWithDetails() {
        Map<String, Set<Object>> inEntityMap = new HashMap<>();
        List<String> allFiles = getAllFileNames(CART_DIR);
        String dataFile = CART_DIR + File.separator;
        for(String file: allFiles) {
            Set<Object> baseEntitySet = new HashSet<>();
            Set<String> set = getDataFromFS(dataFile + File.separator + file, Set.class);
            String entityName = file.substring(0, file.indexOf("."));
            for(String key: set) {
                String jsonFile = ENTITY_JSON_DIR + File.separator + entityName + File.separator + key + JSON_EXTENSION;
                Object object = getDataFromFS(jsonFile, Object.class);
                baseEntitySet.add(object);
            }
            inEntityMap.put(entityName, baseEntitySet);
        }
        return inEntityMap;
    }
}
