package com.ad.dao;

import com.ad.models.BaseEntity;
import com.ad.util.XMLUtil;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

@Component
public class EntitiesDao extends BaseDao {

        protected XMLUtil xmlUtil1 = new XMLUtil();

        public static final String ENTITY_XML_DIR ="entities/xml";

        public static final String ENTITY_JSON_DIR ="entities/json";

        public static final String JSON_EXTENSION = ".json";

        public static final String XML_EXTENSION = ".xml";

        public void convertXMLtoJSON(String inResponse, Map<String, List<BaseEntity>> inEntityMap){}
}
