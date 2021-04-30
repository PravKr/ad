package com.ad.dao;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class CartDao extends EntitiesDao {

    public static final String CART_DIR ="cart";

    public void saveToCart(Set<String> entityGkeySet, String entity) {
        String dataFile = CART_DIR + File.separator + entity + ".json";
        getFromCartByEntity(dataFile, entityGkeySet);
        saveDataToFS(dataFile, entityGkeySet);
    }

    public void getFromCartByEntity(String inDataFile, Set<String> entityGkeySet) {
        Set<String> set = getDataFromFS(inDataFile, Set.class);
        if(set != null) {
            set.addAll(entityGkeySet);
        }
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
}
