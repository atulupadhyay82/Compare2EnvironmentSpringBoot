package com.thomsonreuters.Services;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * Fetch extract data from DEV, QA, TempQA, UAT environment
 */
@Service
public class ExtractFetch {

    public ResponseEntity<String> fetchExtractJSON(String uri, String userName, String password, String userName_WISH, String password_WISH, String companyName, String extractName, String env) throws Exception {
        ResponseEntity<String> responseEntity;
        if(extractName.contains("Wish") ){
            responseEntity= getStringResponseEntity(uri, companyName, extractName, userName_WISH, password_WISH);
        }
        else{
            responseEntity = getStringResponseEntity(uri, companyName, extractName, userName, password);
        }
        return responseEntity;
    }

    private ResponseEntity<String> getStringResponseEntity(String url, String companyName, String extractName, String userName, String password) throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        String unEncoded = userName + ":" + password;
        URI uri = new URI(url + "services/rest/taxtreatments/company/" + companyName + "/extractName/" + extractName + "?loadMethod=FULL");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("authorization", "Basic " + Base64.getEncoder().encodeToString(unEncoded.getBytes()));
        ResponseEntity<String> response= restTemplate.exchange(
                uri,
                HttpMethod.GET,
                new HttpEntity<String>(null, httpHeaders),
                String.class
        );
        return response;

    }
}


