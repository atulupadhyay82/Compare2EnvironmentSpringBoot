package com.thomsonreuters.regressionTool.pojoClasses;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.List;


@Generated("a")
@SuppressWarnings("unused")
public class Root {

    @SerializedName("addresses")
    private List<Address> mAddresses;
    @SerializedName("companyName")
    private String mCompanyName;
    @SerializedName("contentVersion")
    private Long mContentVersion;
    @SerializedName("extractDate")
    private List<Long> mExtractDate;
    @SerializedName("extractName")
    private String mExtractName;
    @SerializedName("groupingRule")
    private String mGroupingRule;
    @SerializedName("jurisdictionTreatmentMappings")
    private List<JurisdictionTreatmentMapping> mJurisdictionTreatmentMappings;
    @SerializedName("jurisdictions")
    private List<Jurisdiction> mJurisdictions;
    @SerializedName("loadMethod")
    private String mLoadMethod;
    @SerializedName("products")
    private List<Product> mProducts;
    @SerializedName("treatmentGroupTreatments")
    private List<TreatmentGroupTreatment> mTreatmentGroupTreatments;
    @SerializedName("treatmentGroups")
    private List<TreatmentGroup> mTreatmentGroups;
    @SerializedName("treatments")
    private List<Treatment> mTreatments;


    //Authorities Data Types
    @SerializedName("authorities")
    private List<Authority> mAuthorities;
    @SerializedName("authorityTreatmentMappings")
    private List<AuthorityTreatmentMapping> mAuthorityTreatmentMappings;
    @SerializedName("jurisdictionAuthorities")
    private List<JurisdictionAuthority> mJurisdictionAuthorities;
    @SerializedName("locations")
    private List<Location> mLocations;


    public List<Authority> getmAuthorities() {
        return mAuthorities;
    }

    public void setmAuthorities(List<Authority> mAuthorities) {
        this.mAuthorities = mAuthorities;
    }

    public List<AuthorityTreatmentMapping> getmAuthorityTreatmentMappings() {
        return mAuthorityTreatmentMappings;
    }

    public void setmAuthorityTreatmentMappings(List<AuthorityTreatmentMapping> mAuthorityTreatmentMappings) {
        this.mAuthorityTreatmentMappings = mAuthorityTreatmentMappings;
    }

    public List<JurisdictionAuthority> getmJurisdictionAuthorities() {
        return mJurisdictionAuthorities;
    }

    public void setmJurisdictionAuthorities(List<JurisdictionAuthority> mJurisdictionAuthorities) {
        this.mJurisdictionAuthorities = mJurisdictionAuthorities;
    }

    public List<Location> getmLocations() {
        return mLocations;
    }

    public void setmLocations(List<Location> mLocations) {
        this.mLocations = mLocations;
    }

    public List<Address> getAddresses() {
        return mAddresses;
    }

    public void setAddresses(List<Address> addresses) {
        mAddresses = addresses;
    }

    public String getCompanyName() {
        return mCompanyName;
    }

    public void setCompanyName(String companyName) {
        mCompanyName = companyName;
    }

    public Long getContentVersion() {
        return mContentVersion;
    }

    public void setContentVersion(Long contentVersion) {
        mContentVersion = contentVersion;
    }

    public List<Long> getExtractDate() {
        return mExtractDate;
    }

    public void setExtractDate(List<Long> extractDate) {
        mExtractDate = extractDate;
    }

    public String getExtractName() {
        return mExtractName;
    }

    public void setExtractName(String extractName) {
        mExtractName = extractName;
    }

    public String getGroupingRule() {
        return mGroupingRule;
    }

    public void setGroupingRule(String groupingRule) {
        mGroupingRule = groupingRule;
    }

    public List<JurisdictionTreatmentMapping> getJurisdictionTreatmentMappings() {
        return mJurisdictionTreatmentMappings;
    }

    public void setJurisdictionTreatmentMappings(List<JurisdictionTreatmentMapping> jurisdictionTreatmentMappings) {
        mJurisdictionTreatmentMappings = jurisdictionTreatmentMappings;
    }

    public List<Jurisdiction> getJurisdictions() {
        return mJurisdictions;
    }

    public void setJurisdictions(List<Jurisdiction> jurisdictions) {
        mJurisdictions = jurisdictions;
    }

    public String getLoadMethod() {
        return mLoadMethod;
    }

    public void setLoadMethod(String loadMethod) {
        mLoadMethod = loadMethod;
    }

    public List<Product> getProducts() {
        return mProducts;
    }

    public void setProducts(List<Product> products) {
        mProducts = products;
    }

    public List<TreatmentGroupTreatment> getTreatmentGroupTreatments() {
        return mTreatmentGroupTreatments;
    }

    public void setTreatmentGroupTreatments(List<TreatmentGroupTreatment> treatmentGroupTreatments) {
        mTreatmentGroupTreatments = treatmentGroupTreatments;
    }

    public List<TreatmentGroup> getTreatmentGroups() {
        return mTreatmentGroups;
    }

    public void setTreatmentGroups(List<TreatmentGroup> treatmentGroups) {
        mTreatmentGroups = treatmentGroups;
    }

    public List<Treatment> getTreatments() {
        return mTreatments;
    }

    public void setTreatments(List<Treatment> treatments) {
        mTreatments = treatments;
    }

}
