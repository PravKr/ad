package com.ad.dao;

import com.ad.models.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component("ImportEntityDao")
public class ImportEntityDao extends EntitiesDao {

    @Autowired
    CartDao cartDao;

    public List<String> getListOfEntities() {
        List<String> allEntities = new ArrayList<>();
        Map<String, Set<String>> inEntityMap = cartDao.getEntitiesFromCart();
        for (Map.Entry<String, Set<String>> entry : inEntityMap.entrySet()) {
            String dataFile = controllerr.ENTITY_XML_DIR + File.separator + entry.getKey() + File.separator;
            for (String elementIndex: entry.getValue()) {
                allEntities.add(getDataFromFS(dataFile + elementIndex + XML_EXTENSION, String.class)/*getFileContentById(dataFile + elementIndex + ".txt")*/);
            }
        }
        return allEntities;
    }

    public List<BaseEntity> allRecordsFromEntity(){
        return null;
    }
}
