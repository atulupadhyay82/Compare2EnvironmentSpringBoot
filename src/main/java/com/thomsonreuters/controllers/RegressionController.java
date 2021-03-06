package com.thomsonreuters.controllers;

import com.thomsonreuters.Services.RunStaging;
import com.thomsonreuters.dto.TestCase;
import com.thomsonreuters.dto.TestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

@RestController
public class RegressionController {

    @Autowired
    private RunStaging runStaging;

    @RequestMapping(method = RequestMethod.POST, value="/compareExtractWithStaging")
    public TestResult compareExtractWithStaging(@RequestBody TestCase testcase) throws IOException, URISyntaxException {
        TestResult result= runStaging.compareExtractData(testcase.getCompanyName(), testcase.getExtractName());
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value="/compareExtractWithoutStaging")
    public TestResult compareExtractWithoutStaging(@RequestBody TestCase testcase) throws IOException, URISyntaxException {
        TestResult result= runStaging.fetchAndCompareExtractData(testcase.getCompanyName(), testcase.getExtractName());
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value="/fetchExtracts")
    public List<TestResult> getProcessedVersion(@RequestBody List<TestCase> testcases) throws Exception {
        List<TestResult> results=new ArrayList<>();
        for(TestCase testcase:testcases){
            results.add(runStaging.generatedProcessedVersion(testcase.getCompanyName(), testcase.getExtractName()));
        }

        return results;
    }


}
