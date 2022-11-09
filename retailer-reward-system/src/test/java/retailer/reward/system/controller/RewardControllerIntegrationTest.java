package retailer.reward.system.controller;


import retailer.reward.system.repository.TransactionsRepository;
import retailer.reward.system.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RewardControllerIntegrationTest {

    private String baseUrl = "http://localhost";

    public static TestRestTemplate testRestTemplate;

    @LocalServerPort
    private Integer port;

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    public static void init(){
        testRestTemplate = new TestRestTemplate();
    }

    @BeforeEach
    public void setUp(){
        baseUrl= baseUrl.concat(":"+port).concat("/rewards");
    }

    @Test
    @Sql(statements = "insert into USER (USER_ID, USER_NAME) values (1, 'uma')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "insert into TRANSACTIONS (ID, CREATED_DATE,PRODUCT_NAME, PRODUCT_PRICE,PRODUCT_QUANTITY, REWARD_POINTS,USER_ID) values (100, CURRENT_DATE, 'apple',25.0, 5, 100, 1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "insert into TRANSACTIONS (ID, CREATED_DATE,PRODUCT_NAME, PRODUCT_PRICE,PRODUCT_QUANTITY, REWARD_POINTS,USER_ID) values (101, CURRENT_DATE, 'google',25.0, 6, 150, 1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "insert into TRANSACTIONS (ID, CREATED_DATE,PRODUCT_NAME, PRODUCT_PRICE,PRODUCT_QUANTITY, REWARD_POINTS,USER_ID) values (102, CURRENT_DATE, 'motog',25.0, 7, 200, 1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM TRANSACTIONS", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(statements = "DELETE FROM USER", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getRewardsSuccessTest() {
        String url = baseUrl.concat("/getRewards");
        HttpHeaders headers = new HttpHeaders();
        headers.add("userName","uma");
        HttpEntity<String> httpEntity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<Object> responseEntity = testRestTemplate.exchange(url, HttpMethod.GET, httpEntity, Object.class);
        Map<String, Object> responseBody = (HashMap<String, Object>) responseEntity.getBody();
        Map<String, Object> responseMapObj = (HashMap<String, Object>) responseBody.get("data");
        Assertions.assertEquals(true, responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
        Assertions.assertEquals("SUCCESS", responseBody.get("responseStatus"));
        Assertions.assertEquals(450, responseMapObj.get("rewardPoints"));
        Assertions.assertEquals(3, transactionsRepository.findAll().size());
        Assertions.assertEquals(1, userRepository.findAll().size());
    }

    @Test
    @Sql(statements = "insert into USER (USER_ID, USER_NAME) values (1, 'uma')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "insert into TRANSACTIONS (ID, CREATED_DATE,PRODUCT_NAME, PRODUCT_PRICE,PRODUCT_QUANTITY, REWARD_POINTS,USER_ID) values (100, CURRENT_DATE, 'apple',25.0, 5, 100, 1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "insert into TRANSACTIONS (ID, CREATED_DATE,PRODUCT_NAME, PRODUCT_PRICE,PRODUCT_QUANTITY, REWARD_POINTS,USER_ID) values (101, CURRENT_DATE-10, 'google',25.0, 6, 150, 1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "insert into TRANSACTIONS (ID, CREATED_DATE,PRODUCT_NAME, PRODUCT_PRICE,PRODUCT_QUANTITY, REWARD_POINTS,USER_ID) values (102, CURRENT_DATE-20, 'motog',25.0, 7, 200, 1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM TRANSACTIONS", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(statements = "DELETE FROM USER", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getRewardsByMonthlySuccessTest() {
        String url = baseUrl.concat("/getRewardsByMonthly");
        HttpHeaders headers = new HttpHeaders();
        headers.add("userName","uma");
        HttpEntity<String> httpEntity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<Object> responseEntity = testRestTemplate.exchange(url, HttpMethod.GET, httpEntity, Object.class);
        Map<String, Object> responseBody = (HashMap<String, Object>) responseEntity.getBody();
        Map<String, Object> responseMapObj = (HashMap<String, Object>) responseBody.get("data");
        Assertions.assertEquals(true, responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
        Assertions.assertEquals("SUCCESS", responseBody.get("responseStatus"));
        Assertions.assertEquals(450, responseMapObj.get("rewardPoints"));
        Assertions.assertEquals(3, transactionsRepository.findAll().size());
        Assertions.assertEquals(1, userRepository.findAll().size());
    }

    @Test
    @Sql(statements = "insert into USER (USER_ID, USER_NAME) values (1, 'uma')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "insert into TRANSACTIONS (ID, CREATED_DATE,PRODUCT_NAME, PRODUCT_PRICE,PRODUCT_QUANTITY, REWARD_POINTS,USER_ID) values (100, CURRENT_DATE, 'Melon',25.0, 5, 100, 1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "insert into TRANSACTIONS (ID, CREATED_DATE,PRODUCT_NAME, PRODUCT_PRICE,PRODUCT_QUANTITY, REWARD_POINTS,USER_ID) values (101, CURRENT_DATE-40, 'JackFruit',25.0, 6, 150, 1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "insert into TRANSACTIONS (ID, CREATED_DATE,PRODUCT_NAME, PRODUCT_PRICE,PRODUCT_QUANTITY, REWARD_POINTS,USER_ID) values (102, CURRENT_DATE-60, 'grapes',25.0, 7, 200, 1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM TRANSACTIONS", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(statements = "DELETE FROM USER", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getRewardsByQuarterlySuccessTest() {
        String url = baseUrl.concat("/getRewardsByQuarterly");
        HttpHeaders headers = new HttpHeaders();
        headers.add("userName","uma");
        HttpEntity<String> httpEntity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<Object> responseEntity = testRestTemplate.exchange(url, HttpMethod.GET, httpEntity, Object.class);
        Map<String, Object> responseBody = (HashMap<String, Object>) responseEntity.getBody();
        Map<String, Object> responseMapObj = (HashMap<String, Object>) responseBody.get("data");
        Assertions.assertEquals(true, responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
        Assertions.assertEquals("SUCCESS", responseBody.get("responseStatus"));
        Assertions.assertEquals(450, responseMapObj.get("rewardPoints"));
        Assertions.assertEquals(3, transactionsRepository.findAll().size());
        Assertions.assertEquals(1, userRepository.findAll().size());
    }


    @Test
    @Sql(statements = "insert into USER (USER_ID, USER_NAME) values (1, 'uma')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "insert into TRANSACTIONS (ID, CREATED_DATE,PRODUCT_NAME, PRODUCT_PRICE,PRODUCT_QUANTITY, REWARD_POINTS,USER_ID) values (100, CURRENT_DATE, 'Melon',25.0, 5, 100, 1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "insert into TRANSACTIONS (ID, CREATED_DATE,PRODUCT_NAME, PRODUCT_PRICE,PRODUCT_QUANTITY, REWARD_POINTS,USER_ID) values (101, CURRENT_DATE, 'JackFruit',25.0, 6, 150, 1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "insert into TRANSACTIONS (ID, CREATED_DATE,PRODUCT_NAME, PRODUCT_PRICE,PRODUCT_QUANTITY, REWARD_POINTS,USER_ID) values (102, CURRENT_DATE, 'grapes',25.0, 7, 200, 1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM TRANSACTIONS", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(statements = "DELETE FROM USER", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getRewardsErrorTest() {
        String url = baseUrl.concat("/getRewards");
        HttpHeaders headers = new HttpHeaders();
        headers.add("userName","jeswanth");
        HttpEntity<String> httpEntity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<Object> responseEntity = testRestTemplate.exchange(url, HttpMethod.GET, httpEntity, Object.class);
        Map<String, Object> responseBody = (HashMap<String, Object>) responseEntity.getBody();
        Assertions.assertEquals(true, responseEntity.getStatusCode().is4xxClientError());
        Assertions.assertEquals("ERROR", responseBody.get("responseStatus"));
    }

    @Test
    @Sql(statements = "insert into USER (USER_ID, USER_NAME) values (1, 'uma')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "insert into TRANSACTIONS (ID, CREATED_DATE,PRODUCT_NAME, PRODUCT_PRICE,PRODUCT_QUANTITY, REWARD_POINTS,USER_ID) values (100, CURRENT_DATE, 'Melon',25.0, 5, 100, 1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "insert into TRANSACTIONS (ID, CREATED_DATE,PRODUCT_NAME, PRODUCT_PRICE,PRODUCT_QUANTITY, REWARD_POINTS,USER_ID) values (101, CURRENT_DATE-10, 'JackFruit',25.0, 6, 150, 1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "insert into TRANSACTIONS (ID, CREATED_DATE,PRODUCT_NAME, PRODUCT_PRICE,PRODUCT_QUANTITY, REWARD_POINTS,USER_ID) values (102, CURRENT_DATE-20, 'grapes',25.0, 7, 200, 1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM TRANSACTIONS", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(statements = "DELETE FROM USER", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getRewardsByMonthlyErrorTest() {
        String url = baseUrl.concat("/getRewardsByMonthly");
        HttpHeaders headers = new HttpHeaders();
        headers.add("userName","jeswanth");
        HttpEntity<String> httpEntity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<Object> responseEntity = testRestTemplate.exchange(url, HttpMethod.GET, httpEntity, Object.class);
        Map<String, Object> responseBody = (HashMap<String, Object>) responseEntity.getBody();
        Assertions.assertEquals(true, responseEntity.getStatusCode().is4xxClientError());
        Assertions.assertEquals("ERROR", responseBody.get("responseStatus"));
    }

    @Test
    @Sql(statements = "insert into USER (USER_ID, USER_NAME) values (1, 'uma')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "insert into TRANSACTIONS (ID, CREATED_DATE,PRODUCT_NAME, PRODUCT_PRICE,PRODUCT_QUANTITY, REWARD_POINTS,USER_ID) values (100, CURRENT_DATE, 'Melon',25.0, 5, 100, 1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "insert into TRANSACTIONS (ID, CREATED_DATE,PRODUCT_NAME, PRODUCT_PRICE,PRODUCT_QUANTITY, REWARD_POINTS,USER_ID) values (101, CURRENT_DATE-40, 'JackFruit',25.0, 6, 150, 1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "insert into TRANSACTIONS (ID, CREATED_DATE,PRODUCT_NAME, PRODUCT_PRICE,PRODUCT_QUANTITY, REWARD_POINTS,USER_ID) values (102, CURRENT_DATE-60, 'grapes',25.0, 7, 200, 1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM TRANSACTIONS", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(statements = "DELETE FROM USER", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getRewardsByQuarterlyErrorTest() {
        String url = baseUrl.concat("/getRewardsByQuarterly");
        HttpHeaders headers = new HttpHeaders();
        headers.add("userName","jeswanth");
        HttpEntity<String> httpEntity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<Object> responseEntity = testRestTemplate.exchange(url, HttpMethod.GET, httpEntity, Object.class);
        Map<String, Object> responseBody = (HashMap<String, Object>) responseEntity.getBody();
        Assertions.assertEquals(true, responseEntity.getStatusCode().is4xxClientError());
        Assertions.assertEquals("ERROR", responseBody.get("responseStatus"));
    }

}




