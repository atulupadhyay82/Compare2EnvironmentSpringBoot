package com.thomsonreuters.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Data
public class PropertyConfig {

    @Value("${uri_staging_env1}" )
    private String uri_STG_env1;

    @Value("${userName_staging_env1}" )
    private String user_STG_env1;

    @Value("${password_staging_env1}" )
    private String password_STG_env1;

    @Value("${uri_staging_env2}" )
    private String uri_STG_env2;

    @Value("${userName_staging_env2}" )
    private String user_STG_env2;

    @Value("${password_staging_env2}" )
    private String password_STG_env2;

    @Value("${uri_ce_env1}" )
    private String uri_CE_env1;

    @Value("${userName_ce_env1}" )
    private String user_CE_env1;

    @Value("${password_ce_env1}" )
    private String password_CE_env1;

    @Value("${uri_ce_env2}" )
    private String uri_CE_env2;

    @Value("${userName_ce_env2}" )
    private String user_CE_env2;

    @Value("${password_ce_env2}" )
    private String password_CE_env2;

    @Value("${userName_ce_Wish}" )
    private String user_CE_Wish;

    @Value("${password_ce_Wish}" )
    private String password_CE_Wish;

    @Value("${environment1}" )
    private String environment1;

    @Value("${environment2}" )
    private String environment2;

    public String getUri_STG_env1() {
        return uri_STG_env1;
    }

    public void setUri_STG_env1(String uri_STG_env1) {
        this.uri_STG_env1 = uri_STG_env1;
    }

    public String getUser_STG_env1() {
        return user_STG_env1;
    }

    public void setUser_STG_env1(String user_STG_env1) {
        this.user_STG_env1 = user_STG_env1;
    }

    public String getPassword_STG_env1() {
        return password_STG_env1;
    }

    public void setPassword_STG_env1(String password_STG_env1) {
        this.password_STG_env1 = password_STG_env1;
    }

    public String getUri_STG_env2() {
        return uri_STG_env2;
    }

    public void setUri_STG_env2(String uri_STG_env2) {
        this.uri_STG_env2 = uri_STG_env2;
    }

    public String getUser_STG_env2() {
        return user_STG_env2;
    }

    public void setUser_STG_env2(String user_STG_env2) {
        this.user_STG_env2 = user_STG_env2;
    }

    public String getPassword_STG_env2() {
        return password_STG_env2;
    }

    public void setPassword_STG_env2(String password_STG_env2) {
        this.password_STG_env2 = password_STG_env2;
    }

    public String getUri_CE_env1() {
        return uri_CE_env1;
    }

    public void setUri_CE_env1(String uri_CE_env1) {
        this.uri_CE_env1 = uri_CE_env1;
    }

    public String getUser_CE_env1() {
        return user_CE_env1;
    }

    public void setUser_CE_env1(String user_CE_env1) {
        this.user_CE_env1 = user_CE_env1;
    }

    public String getPassword_CE_env1() {
        return password_CE_env1;
    }

    public void setPassword_CE_env1(String password_CE_env1) {
        this.password_CE_env1 = password_CE_env1;
    }

    public String getUri_CE_env2() {
        return uri_CE_env2;
    }

    public void setUri_CE_env2(String uri_CE_env2) {
        this.uri_CE_env2 = uri_CE_env2;
    }

    public String getUser_CE_env2() {
        return user_CE_env2;
    }

    public void setUser_CE_env2(String user_CE_env2) {
        this.user_CE_env2 = user_CE_env2;
    }

    public String getPassword_CE_env2() {
        return password_CE_env2;
    }

    public void setPassword_CE_env2(String password_CE_env2) {
        this.password_CE_env2 = password_CE_env2;
    }

    public String getUser_CE_Wish() {
        return user_CE_Wish;
    }

    public void setUser_CE_Wish(String user_CE_Wish) {
        this.user_CE_Wish = user_CE_Wish;
    }

    public String getPassword_CE_Wish() {
        return password_CE_Wish;
    }

    public void setPassword_CE_Wish(String password_CE_Wish) {
        this.password_CE_Wish = password_CE_Wish;
    }

    public String getEnvironment1() {
        return environment1;
    }

    public void setEnvironment1(String environment1) {
        this.environment1 = environment1;
    }

    public String getEnvironment2() {
        return environment2;
    }

    public void setEnvironment2(String environment2) {
        this.environment2 = environment2;
    }
}
