package com.thomsonreuters.extractValidatorResultParser.extractValidatorPOJO;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MainResult {

    @SerializedName("testResult")
    @Expose
    private String testResult;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("productCode")
    @Expose
    private String productCode;
    @SerializedName("productCategoryName")
    @Expose
    private String productCategoryName;
    @SerializedName("effectiveDate")
    @Expose
    private String effectiveDate;
    @SerializedName("modelScenarioTaxAmount")
    @Expose
    private String modelScenarioTaxAmount;
    @SerializedName("extractTaxAmount")
    @Expose
    private String extractTaxAmount;
    @SerializedName("grossAmount")
    @Expose
    private String grossAmount;
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("jurisdiction")
    @Expose
    private String jurisdiction;
    @SerializedName("scenarioExecuted")
    @Expose
    private Integer scenarioExecuted;

    public String getTestResult() {
        return testResult;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getModelScenarioTaxAmount() {
        return modelScenarioTaxAmount;
    }

    public void setModelScenarioTaxAmount(String modelScenarioTaxAmount) {
        this.modelScenarioTaxAmount = modelScenarioTaxAmount;
    }

    public String getExtractTaxAmount() {
        return extractTaxAmount;
    }

    public void setExtractTaxAmount(String extractTaxAmount) {
        this.extractTaxAmount = extractTaxAmount;
    }

    public String getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(String grossAmount) {
        this.grossAmount = grossAmount;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getJurisdiction() {
        return jurisdiction;
    }

    public void setJurisdiction(String jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    public Integer getScenarioExecuted() {
        return scenarioExecuted;
    }

    public void setScenarioExecuted(Integer scenarioExecuted) {
        this.scenarioExecuted = scenarioExecuted;
    }

}

