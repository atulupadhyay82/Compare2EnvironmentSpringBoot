package com.thomsonreuters.Services;

import com.thomsonreuters.config.PropertyConfig;
import com.thomsonreuters.dto.TestResult;
import com.thomsonreuters.regressionTool.jsonParser.JsonReader;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hpsf.CustomProperty;
import org.aspectj.weaver.ast.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;


@Service
public class RunStaging {

    @Autowired
    private ExtractFetch extractFetch;

    @Autowired
    private StagingService stagingService;

    @Autowired
    private PropertyConfig propertyConfig;

   private static Logger logger = LoggerFactory.getLogger(RunStaging.class);


    public TestResult compareExtractData(String companyName, String extractName) throws URISyntaxException, IOException {
        String env1=propertyConfig.getEnvironment1();
        String env2=propertyConfig.getEnvironment2();

        Long extractID_Env1 =stagingService.getExtractFromEnv1(extractName);
        stagingService.triggerStaging(propertyConfig.getUri_STG_env1(),propertyConfig.getUser_STG_env1(),propertyConfig.getPassword_STG_env1(),extractID_Env1,env1);
        logger.info(extractName+" is in execution state in "+  env1 +", wait for its completion");

        Long extractID_Env2 =stagingService.getExtractFromEnv2(extractName);
        stagingService.triggerStaging(propertyConfig.getUri_STG_env2(),propertyConfig.getUser_STG_env2(),propertyConfig.getPassword_STG_env2(),extractID_Env2,env2);
        logger.info(extractName+" is in execution state in "+  env2+", wait for its completion");

        stagingService.waitForItsCompletionInEnv1(extractID_Env1,env1);
        stagingService.waitForItsCompletionInEnv2(extractID_Env2,env2);

        return fetchAndCompareExtractData(companyName,extractName);

    }


    public TestResult fetchAndCompareExtractData(String companyName, String extractName){
        try {
            ResponseEntity<String> env_1_Json_String= extractFetch.fetchExtractJSON(propertyConfig.getUri_CE_env1(),propertyConfig.getUser_CE_env1(),propertyConfig.getPassword_CE_env1(),propertyConfig.getUser_CE_Wish(),
                    propertyConfig.getPassword_CE_Wish(),companyName,extractName,propertyConfig.getEnvironment1());

            ResponseEntity<String> env_2_Json_String= extractFetch.fetchExtractJSON(propertyConfig.getUri_CE_env2(),propertyConfig.getUser_CE_env2(),propertyConfig.getPassword_CE_env2(),propertyConfig.getUser_CE_Wish(),
                    propertyConfig.getPassword_CE_Wish(),companyName,extractName,propertyConfig.getEnvironment2());

            return compareBothJSON(extractName,env_1_Json_String,env_2_Json_String);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("CompareExtract:: Error occured for extract " + extractName + ":: " + e.getMessage());
        }
        return null;

    }

    private TestResult compareBothJSON(String extractName, ResponseEntity<String> qaJsonFile, ResponseEntity<String> satJsonFile) throws Exception {
        String env1=propertyConfig.getEnvironment1();
        String env2= propertyConfig.getEnvironment2();
        TestResult result = null;
        File env1_File = null,env2_File = null;

        try {
            logger.info("Converting json file :"+extractName+" from "+env1);
            env1_File= JsonReader.getProcessedJson(qaJsonFile.getBody(),env1);

            logger.info("Converting json file :"+extractName+" from "+env2);
            env2_File=JsonReader.getProcessedJson(satJsonFile.getBody(),env2);

            logger.info("Comparing the processed version of :"+extractName+" from both the env's");
            boolean matched = FileUtils.contentEquals(env1_File, env2_File);

            if (matched)
                result = generateResults(extractName, "matched");
            else
                result = generateResults(extractName, "notMatched");

        }catch (Exception e){
            if (env1_File == null && env2_File == null)
                result = generateResults(extractName, " has some issue in " + env1 + " and " + env2 + " both");
             else if (env1_File == null)
                result = generateResults(extractName, " has some issue in " + env1);
             else if (env2_File == null)
                result = generateResults(extractName, " has some issue in " + env2);

        }finally {

            if ((env1_File != null)) {
                env1_File.delete();
            }
            if ((env2_File != null)) {
                env2_File.delete();
            }

        }

        return result;

    }

    public TestResult generateResults(String extractName, String result){
        TestResult testResult=new TestResult();
        testResult.setExtractName(extractName);
        testResult.setResult(result);
        return testResult;
    }

    }
