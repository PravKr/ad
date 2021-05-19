package com.ad.models;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ExportHistory extends BaseEntity {

    private String exportSystemId;
    private List<String> importSystemList;
    private Map<String, Set<String>> exportedEnitites;
    private String exportDate;

    public String getExportSystemId() {
        return exportSystemId;
    }

    public void setExportSystemId(String exportSystemId) {
        this.exportSystemId = exportSystemId;
    }

    public List<String> getImportSystemList() {
        return importSystemList;
    }

    public void setImportSystemList(List<String> importSystemList) {
        this.importSystemList = importSystemList;
    }

    public Map<String, Set<String>> getExportedEnitites() {
        return exportedEnitites;
    }

    public void setExportedEnitites(Map<String, Set<String>> exportedEnitites) {
        this.exportedEnitites = exportedEnitites;
    }

    public String getExportDate() {
        return exportDate;
    }

    public void setExportDate(String exportDate) {
        this.exportDate = exportDate;
    }
}
