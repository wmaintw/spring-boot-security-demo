package com.tw.security.demo;

import com.tw.security.demo.dao.OrderDao;
import com.tw.security.demo.dao.UserDao;
import com.tw.security.demo.domain.DealerUser;
import com.tw.security.demo.domain.User;
import com.tw.security.demo.domain.UserRole;
import com.tw.security.demo.domain.dto.LoginRequestDto;
import com.tw.security.demo.domain.dto.TokenDto;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import utils.TestDataUtils;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseTest {
    protected static final String AUTH_PATH = "/token";
    protected static final String D1_STORE1_SC1 = "d1-store1-sc1";
    protected static final String D1_STORE1_SC2 = "d1-store1-sc2";
    protected static final String D1_STORE2_SC1 = "d1-store2-sc1";
    protected static final String D1_STORE2_SC2 = "d1-store2-sc2";
    protected static final String D1_GENERAL_MANAGER = "d1-dm";
    protected static final String D2_STORE1_SC1 = "d1-store1-sc1";
    protected static final String D2_DEALER_MANAGER = "d2-dm";
    protected static final String ADMIN = "bob";
    protected static final String CUSTOMER1 = "customer1";
    protected static final String CUSTOMER2 = "customer2";
    protected static final String CUSTOMER3 = "customer3";
    protected static final String CUSTOMER4 = "customer4";
    protected static final String PASSWORD = "123";
    protected static final String WRONG_USERNAME = "WRONG_USERNAME";
    protected static final String WRONG_PASSWORD = "WRONG_PASSWORD";

    @Autowired
    protected TestRestTemplate restTemplate;

    protected TestDataUtils testDataUtils;

    protected HttpHeaders headers;

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrderDao orderDao;

    @Before
    public void before() throws Exception {
        testDataUtils = new TestDataUtils();
        testDataUtils.setJdbcTemplate(jdbcTemplate);
        testDataUtils.setUserDao(userDao);
        testDataUtils.setOrderDao(orderDao);

        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        givenACleanStage();
    }

    protected void givenACleanStage() {
        testDataUtils.deleteAllUsers();
        testDataUtils.deleteAllOrders();
        testDataUtils.deleteAllDealerUsers();
    }

    protected User givenAnExistingCustomer(String username) throws Exception {
        return testDataUtils.createUser(username, PASSWORD, UserRole.ROLE_CUSTOMER);
    }

    protected User givenAnExistingServiceConsultant(String username, Integer storeId) throws Exception {
        User user = testDataUtils.createUser(username, PASSWORD, UserRole.ROLE_SERVICE_CONSULTANT);

        DealerUser dealerUser = new DealerUser();
        dealerUser.setUserId(user.getId());
        dealerUser.setStoreId(storeId);
        dealerUser.setName(username);
        testDataUtils.createDealerUser(dealerUser);

        return user;
    }

    protected User givenAnExistingDealerGeneralManager(String username) throws Exception {
        return testDataUtils.createUser(username, PASSWORD, UserRole.ROLE_DEALER_GENERAL_MANAGER);
    }

    protected User givenAnExistingAdmin(String username) throws Exception {
        return testDataUtils.createUser(username, PASSWORD, UserRole.ROLE_ADMIN);
    }

    protected TokenDto login(User user) {
        ResponseEntity<TokenDto> responseEntity = login(user.getUsername(), PASSWORD, TokenDto.class);
        return responseEntity.getBody();
    }

    protected ResponseEntity login(String username, String password, Class clazz) {
        LoginRequestDto loginRequestDto = new LoginRequestDto(username, password);
        HttpEntity<LoginRequestDto> requestEntity = new HttpEntity<>(loginRequestDto, headers);

        return restTemplate.exchange(AUTH_PATH, POST, requestEntity, clazz);
    }

    protected ResponseEntity<String> logout(String tokenId) {
        headers.add("Authorization", tokenId);
        HttpEntity<String> requestEntity = new HttpEntity<>("", headers);

        return restTemplate.exchange(AUTH_PATH, DELETE, requestEntity, String.class);
    }

    protected HttpHeaders attachTokenToRequestHeader(String tokenId) {
        if (headers.containsKey(AUTHORIZATION)) {
            headers.remove(AUTHORIZATION);
        }

        headers.add(AUTHORIZATION, tokenId);
        return headers;
    }

    protected ResponseEntity get(String path, TokenDto tokenDto, Class responseType) {
        attachTokenToRequestHeader(tokenDto.getTokenId());
        HttpEntity<String> requestEntity = new HttpEntity<>(EMPTY, headers);
        return exchange(path, GET, requestEntity, responseType);
    }

    protected ResponseEntity post(String path, TokenDto tokenDto, Object requestObject, Class responseType) {
        attachTokenToRequestHeader(tokenDto.getTokenId());
        HttpEntity requestEntity = new HttpEntity<>(requestObject, headers);
        return exchange(path, POST, requestEntity, responseType);
    }

    protected ResponseEntity exchange(String path, HttpMethod httpMethod, HttpEntity requestEntity, Class responseType) {
        return restTemplate.exchange(path, httpMethod, requestEntity, responseType);
    }
}

