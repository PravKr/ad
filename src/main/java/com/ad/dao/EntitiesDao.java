package com.ad.dao;

import com.ad.models.BaseEntity;
import com.ad.util.XMLUtil;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public abstract class EntitiesDao extends BaseDao {

        protected XMLUtil xmlUtil1 = new XMLUtil();

        public static final String JSON_EXTENSION = ".json";

        public static final String XML_EXTENSION = ".xml";

        public List<String> removeExtensionFromFile(List<String> inListString) {
                List<String> stringList = new ArrayList<>();
                for(String string: inListString) {
                        stringList.add(string.substring(0, string.indexOf('.')));
                }
                return stringList;
        }

        public String addExtensionToFile(String inFileName) {
                return inFileName + JSON_EXTENSION;
        }

        public void convertXMLtoJSON(String inResponse, Map<String, List<BaseEntity>> inEntityMap){}

        public abstract List<BaseEntity> allRecordsFromEntity();
}
