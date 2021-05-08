package com.ad.dao;

import com.ad.models.BaseEntity;
import com.ad.util.XMLUtil;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public abstract class EntitiesDao extends BaseDao {

        protected XMLUtil xmlUtil1 = new XMLUtil();

        public static final String JSON_EXTENSION = ".json";

        public static final String XML_EXTENSION = ".xml";

        public void convertXMLtoJSON(String inResponse, Map<String, List<BaseEntity>> inEntityMap){}

        public abstract List<BaseEntity> allRecordsFromEntity();
}
