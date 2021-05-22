package com.ad.dao;

import com.ad.models.BaseEntity;
import com.ad.util.XMLUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

        // Check if a string matches with a given wildcard pattern
        public static boolean isMatch(String str, String pattern) {
                return isMatch(str, 0, pattern, 0);
        }

        // Recursive function to check if the input string matches
        // with a given wildcard pattern
        public static boolean isMatch(String str, int n, String pattern, int m) {
                // end of the pattern is reached
                if (m == pattern.length()) {
                        // return true only if the end of the input string is also reached
                        return n == str.length();
                }

                // if the input string reaches its end, return when the
                // remaining characters in the pattern are all `*`
                if (n == str.length()) {
                        for (int i = m; i < pattern.length(); i++) {
                                if (pattern.charAt(i) != '*') {
                                        return false;
                                }
                        }
                        return true;
                }

                // if the current wildcard character is `?` or the current character in
                // the pattern is the same as the current character in the input string
                if (pattern.charAt(m) == '?' || pattern.charAt(m) == str.charAt(n)) {
                        // move to the next character in the pattern and the input string
                        return isMatch(str, n + 1, pattern, m + 1);
                }

                // if the current wildcard character is `*`
                if (pattern.charAt(m) == '*') {
                        // move to the next character in the input string or
                        // ignore `*` and move to the next character in the pattern
                        return isMatch(str, n + 1, pattern, m) ||
                                isMatch(str, n, pattern, m + 1);
                }

                // we reach here when the current character in the pattern is not a
                // wildcard character, and it doesn't match the current
                // character in the input string
                return false;
        }

        public void convertXMLtoJSON(String inResponse, Map<String, List<BaseEntity>> inEntityMap){}

        public abstract List<BaseEntity> allRecordsFromEntity();

        public abstract List<BaseEntity> allRecordsFromEntityByWildcardChar(String wildcardString);
}
