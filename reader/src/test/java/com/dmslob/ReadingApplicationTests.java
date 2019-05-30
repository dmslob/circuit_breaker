package com.dmslob;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReadingApplicationTests {

    private MockRestServiceServer mockServer;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private RestTemplate rest;

    @Before
    public void setup() {
        this.mockServer = MockRestServiceServer.createServer(rest);
    }

    @After
    public void teardown() {
        this.mockServer = null;
    }

    @Test
    public void toReadTest() {
        this.mockServer
                .expect(requestTo("http://localhost:8090/recommended"))
                .andExpect(method(HttpMethod.GET)).
                andRespond(withSuccess("readingList", MediaType.TEXT_PLAIN));

        String books = testRestTemplate.getForObject("/to-read", String.class);
        assertThat(books).isEqualTo("readingList");
    }

    @Test
    public void toReadFailureTest() {
        this.mockServer
                .expect(requestTo("http://localhost:8090/recommended")).
                andExpect(method(HttpMethod.GET)).andRespond(withServerError());

        String books = testRestTemplate.getForObject("/to-read", String.class);
        assertThat(books).isEqualTo("another readingList");
    }
}
