package com.thomsonreuters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thomsonreuters.dto.TestCase;
import com.thomsonreuters.dto.TestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

import javax.annotation.PostConstruct;
import java.net.URI;


/**
 * The @SpringBootTest annotation is useful when we need to bootstrap the entire container. The annotation works by creating the ApplicationContext that will be utilized in our tests.
 *
 * We can use the webEnvironment attribute of @SpringBootTest to configure our runtime environment; we're using WebEnvironment.MOCK here so that the container will operate in a mock servlet environment.
 *
 * Next, the @TestPropertySource annotation helps configure the locations of properties files specific to our tests. Note that the property file loaded with @TestPropertySource will override the existing application.properties file.
 *
 * The application-integrationtest.properties contains the details to configure the persistence storage:
 */

@SpringBootTest()
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application.properties")
public class TestFetchExtract extends AbstractTestNGSpringContextTests
{

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private TestCase testCase;


    @BeforeClass
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        System.out.println(mockMvc);

        testCase=new TestCase();
        testCase.setCompanyName("01_Wayfair_US");
        testCase.setExtractName("WayfairUAT_09_DC");
    }

    @Test
    public void testEmployee() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .post("/compareExtract")
                .content(asJsonString(testCase))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value("matched"));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}