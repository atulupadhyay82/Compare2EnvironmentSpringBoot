package com.thomsonreuters.Services;


import com.thomsonreuters.repository.qa.QACfgExtractRepository;
import com.thomsonreuters.repository.qa.QAStgExecutionRepository;
import com.thomsonreuters.repository.sat.SATCfgExtractRepository;
import com.thomsonreuters.repository.sat.SATStgExecutionRepository;
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
    private QACfgExtractRepository qaCfgExtractRepository;

    @Autowired
    private QAStgExecutionRepository qaStgExecutionRepository;

    @Autowired
    private SATCfgExtractRepository satCfgExtractRepository;

    @Autowired
    private SATStgExecutionRepository satStgExecutionRepository;

    /**
     *
     * @param extractID
     * @return
     */
    private String  getStatusFromQAEnv(Long extractID) {
        return qaStgExecutionRepository.getStatus(extractID);
    }

    private String  getStatusFromSATEnv(Long extractID) {
        return satStgExecutionRepository.getStatus(extractID);
    }

    /**
     *
     * @param extractName
     * @return
     */
    public Long getExtractFromQAEnv(String extractName){
        return qaCfgExtractRepository.getExtractID(extractName);
    }

    public Long getExtractFromSATEnv(String extractName){
        return satCfgExtractRepository.getExtractID(extractName);
    }

    public void triggerStaging(String URL, String username, String password, Long extractID, String env) throws URISyntaxException {
        String response= executeStaging(URL, username,password,extractID);
        System.out.println(response);
    }

    public void waitForItsCompletionInQAEnv(Long extractID) {

        while(!getStatusFromQAEnv(extractID).equalsIgnoreCase("Complete") ||
                !getStatusFromQAEnv(extractID).equalsIgnoreCase("ERROR")){
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(extractID+" is completed now in QA");
    }

    public void waitForItsCompletionInSATEnv(Long extractID) {
        while(!getStatusFromSATEnv(extractID).equalsIgnoreCase("Complete") ||
                !getStatusFromSATEnv(extractID).equalsIgnoreCase("ERROR")){
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(extractID+" is completed now in QA");

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
        System.out.println(extractID+" is triggered in "+uri.toString());
        return response.getBody();

    }
}
