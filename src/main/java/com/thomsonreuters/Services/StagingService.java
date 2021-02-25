package com.thomsonreuters.Services;


import com.thomsonreuters.repository.env1.Env1CfgExtractRepository;
import com.thomsonreuters.repository.env1.Env1StgExecutionRepository;
import com.thomsonreuters.repository.env2.Env2CfgExtractRepository;
import com.thomsonreuters.repository.env2.Env2StgExecutionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;

/**
 * https://www.baeldung.com/spring-data-jpa-multiple-databases
 */

@Service
public class StagingService {


    @Autowired
    private Env1CfgExtractRepository env1CfgExtractRepository;

    @Autowired
    private Env1StgExecutionRepository env1StgExecutionRepository;

    @Autowired
    private Env2CfgExtractRepository env2CfgExtractRepository;

    @Autowired
    private Env2StgExecutionRepository env2StgExecutionRepository;

    private static Logger logger = LoggerFactory.getLogger(StagingService.class);

    /**
     *
     * @param extractID
     * @return
     */
    private String  getStatusFromEnv1(Long extractID) {
        return env1StgExecutionRepository.getStatus(extractID);
    }

    private String  getStatusFromEnv2(Long extractID) {
        return env2StgExecutionRepository.getStatus(extractID);
    }

    /**
     *
     * @param extractName
     * @return
     */
    public Long getExtractFromEnv1(String extractName){
        return env1CfgExtractRepository.getExtractID(extractName);
    }

    public Long getExtractFromEnv2(String extractName){
        return env2CfgExtractRepository.getExtractID(extractName);
    }

    public void triggerStaging(String URL, String username, String password, Long extractID, String env) throws URISyntaxException {
        String response= executeStaging(URL, username,password,extractID);
        logger.info(response);
    }

    public void waitForItsCompletionInEnv1(Long extractID, String env) {
        if(getStatusFromEnv1(extractID)==null){
            logger.info("Staging has not been triggered yet for the extract: "+extractID+" in "+env);
            return;
        }
        while(!getStatusFromEnv1(extractID).equalsIgnoreCase("Complete") &&
                !getStatusFromEnv1(extractID).equalsIgnoreCase("ERROR")){
            try {
                logger.info(extractID+" is still in "+ getStatusFromEnv1(extractID)+" state in "+env);
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        logger.info(extractID+" is completed now in "+env);
    }

    public void waitForItsCompletionInEnv2(Long extractID, String env) {
        if(getStatusFromEnv2(extractID)==null){
            logger.info("Staging has not been triggered yet for the extract: "+extractID+" in "+env);
            return;
        }
        while(!getStatusFromEnv2(extractID).equalsIgnoreCase("Complete") &&
                !getStatusFromEnv2(extractID).equalsIgnoreCase("ERROR")){
            try {
                logger.info(extractID+" is still in "+getStatusFromEnv2(extractID)+" state in "+env);
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        logger.info(extractID+" is completed now in "+env);

    }

    private String executeStaging(String url, String username, String password, Long extractID) throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        String unEncoded = username + ":" + password;
        URI uri = new URI(url + extractID);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("authorization", "Basic " + Base64.getEncoder().encodeToString(unEncoded.getBytes()));
        ResponseEntity<String> response= restTemplate.exchange(
                uri,
                HttpMethod.POST,
                new HttpEntity<String>(null, httpHeaders),
                String.class
        );
        logger.info(extractID+" is triggered in "+uri.toString());
        return response.getBody();

    }
}
