package com.thomsonreuters.dto;

import lombok.Data;

@Data
public class TestResult {

    private String extractName;

    private String result;

    public String getExtractName() {
        return extractName;
    }

    public void setExtractName(String extractName) {
        this.extractName = extractName;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
