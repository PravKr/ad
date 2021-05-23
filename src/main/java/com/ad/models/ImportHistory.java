package com.ad.models;

import java.util.Date;
import java.util.Map;
import java.util.Set;

public class ImportHistory  extends BaseEntity {

    private String exportSystemId;
    private String exportSystemVisitDate;
    private Map<String, Set<String>> exportedEnitites;
    private String exportDate;

    public String getExportSystemId() {
        return exportSystemId;
    }

    public void setExportSystemId(String exportSystemId) {
        this.exportSystemId = exportSystemId;
    }

    public String getExportSystemVisitDate() {
        return exportSystemVisitDate;
    }

    public void setExportSystemVisitDate(String exportSystemVisitDate) {
        this.exportSystemVisitDate = exportSystemVisitDate;
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
