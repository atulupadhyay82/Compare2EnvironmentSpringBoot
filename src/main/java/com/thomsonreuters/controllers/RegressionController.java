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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RestController
public class RegressionController {

    @Autowired
    private RunStaging runStaging;

    @RequestMapping(method = RequestMethod.POST, value="/compareExtract")
    public TestResult compareExtract(@RequestBody TestCase testcase) throws IOException, URISyntaxException {
        HashMap<String, String> result= runStaging.compareExtractData(testcase.getCompanyName(), testcase.getExtractName());
        TestResult testResult=new TestResult();
        printResult(testResult, result);
        return testResult;
    }

    private static void printResult(TestResult testResult, HashMap<String, String> result) {
        Iterator hmIterator = result.entrySet().iterator();

        // Iterate through the hashmap and add some bonus marks for every student
        while (hmIterator.hasNext()) {

            Map.Entry mapElement = (Map.Entry)hmIterator.next();
            testResult.setResult((String)mapElement.getValue());
            testResult.setExtractName(mapElement.getKey().toString());
            System.out.println(mapElement.getKey() + " : " + mapElement.getValue());
        }

    }
}
