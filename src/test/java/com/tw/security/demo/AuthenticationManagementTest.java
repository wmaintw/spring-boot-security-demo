package com.tw.security.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.security.demo.dao.UserDao;
import com.tw.security.demo.domain.User;
import com.tw.security.demo.domain.dto.TokenDto;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.Set;

import static com.tw.security.demo.domain.UserRole.ROLE_CUSTOMER;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.*;

public class AuthenticationManagementTest extends BaseTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserDao userDao;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void should_login_successfully_when_everything_is_correct() throws Exception {
        User user = new User(1, CUSTOMER1, PASSWORD, ROLE_CUSTOMER);
        userDao.insert(user);

        ResponseEntity<TokenDto> responseEntity = login(CUSTOMER1, PASSWORD, TokenDto.class);

        assertThat(responseEntity.getStatusCode(), is(CREATED));

        TokenDto tokenDto = responseEntity.getBody();
        assertThat(tokenDto.getTokenId(), is(notNullValue()));
        assertThat(tokenDto.getUsername(), is(CUSTOMER1));
    }

    @Test
    public void login_should_be_failed_as_username_is_incorrect() throws Exception {
        testDataUtils.createUser(CUSTOMER1, PASSWORD, ROLE_CUSTOMER);

        ResponseEntity<ErrorResponseDto> responseEntity = login(WRONG_USERNAME, PASSWORD, ErrorResponseDto.class);

        assertThat(responseEntity.getStatusCode(), is(UNAUTHORIZED));

        ErrorResponseDto errorResponseDto = responseEntity.getBody();
        assertThat(errorResponseDto.getErrorMessage(), is("Invalid username or password."));
    }

    @Test
    public void login_should_be_failed_as_password_is_incorrect() throws Exception {
        testDataUtils.createUser(CUSTOMER1, PASSWORD, ROLE_CUSTOMER);

        ResponseEntity<ErrorResponseDto> responseEntity = login(CUSTOMER1, WRONG_PASSWORD, ErrorResponseDto.class);

        assertThat(responseEntity.getStatusCode(), is(UNAUTHORIZED));

        ErrorResponseDto errorResponseDto = responseEntity.getBody();
        assertThat(errorResponseDto.getErrorMessage(), is("Invalid username or password."));
    }

    @Test
    public void response_should_be_the_same_when_either_username_or_password_is_incorrect() throws Exception {
        testDataUtils.createUser(CUSTOMER1, PASSWORD, ROLE_CUSTOMER);

        ResponseEntity<ErrorResponseDto> responseOfIncorrectUsername = login(WRONG_USERNAME, PASSWORD, ErrorResponseDto.class);
        ErrorResponseDto errorBodyOfUsernameIncorrect = responseOfIncorrectUsername.getBody();

        ResponseEntity<ErrorResponseDto> responseOfIncorrectPassword = login(CUSTOMER1, WRONG_PASSWORD, ErrorResponseDto.class);
        ErrorResponseDto errorBodyOfPasswordIncorrect = responseOfIncorrectPassword.getBody();

        assertThat(responseOfIncorrectUsername.getStatusCode(), is(responseOfIncorrectPassword.getStatusCode()));
        assertThat(errorBodyOfUsernameIncorrect.getErrorCode(), is(errorBodyOfPasswordIncorrect.getErrorCode()));
        assertThat(errorBodyOfUsernameIncorrect.getErrorMessage(), is(errorBodyOfPasswordIncorrect.getErrorMessage()));
    }

    @Test
    public void token_value_should_change_randomly_when_each_successful_login_per_user() throws Exception {
        testDataUtils.createUser(CUSTOMER1, PASSWORD, ROLE_CUSTOMER);

        Set<String> tokens = new HashSet<>();
        int amountOfLoginRequest = 10;

        for (int i = 0; i < amountOfLoginRequest; i++) {
            ResponseEntity<TokenDto> loginResponse = login(CUSTOMER1, PASSWORD, TokenDto.class);
            tokens.add(loginResponse.getBody().getTokenId());
        }

        assertThat(tokens.size(), is(amountOfLoginRequest));
    }

    @Test
    public void token_value_should_be_unique_for_each_user() throws Exception {
        testDataUtils.createUser(CUSTOMER1, PASSWORD, ROLE_CUSTOMER);
        testDataUtils.createUser(CUSTOMER2, PASSWORD, ROLE_CUSTOMER);

        ResponseEntity<TokenDto> firstResponse = login(CUSTOMER1, PASSWORD, TokenDto.class);
        TokenDto firstToken = firstResponse.getBody();

        ResponseEntity<TokenDto> secondResponse = login(CUSTOMER2, PASSWORD, TokenDto.class);
        TokenDto secondToken = secondResponse.getBody();

        assertThat(firstToken.getUsername(), not(secondToken.getUsername()));
        assertThat(firstToken.getTokenId(), not(secondToken.getTokenId()));
    }

    @Test
    public void token_length_should_be_at_least_16_chars() throws Exception {
        testDataUtils.createUser(CUSTOMER1, PASSWORD, ROLE_CUSTOMER);

        ResponseEntity<TokenDto> responseEntity = login(CUSTOMER1, PASSWORD, TokenDto.class);
        String tokenId = responseEntity.getBody().getTokenId();
        assertThat(tokenId.length(), greaterThanOrEqualTo(16));
    }

    @Test
    public void should_logout_successfully() throws Exception {
        testDataUtils.createUser(CUSTOMER1, PASSWORD, ROLE_CUSTOMER);

        ResponseEntity<TokenDto> responseEntity = login(CUSTOMER1, PASSWORD, TokenDto.class);
        TokenDto tokenDto = responseEntity.getBody();

        ResponseEntity<String> logoutResponse = logout(tokenDto.getTokenId());

        assertThat(logoutResponse.getStatusCode(), is(ACCEPTED));
        assertThat(logoutResponse.getBody(), is(nullValue()));
    }

    @Test
    public void should_logout_successfully_even_no_active_session_exists() throws Exception {
        ResponseEntity<String> logoutResponse = logout("token_that_no_longer_active");

        assertThat(logoutResponse.getStatusCode(), is(ACCEPTED));
        assertThat(logoutResponse.getBody(), is(nullValue()));
    }

    @Test
    public void token_should_be_invalid_after_logout() throws Exception {
        testDataUtils.createUser(CUSTOMER1, PASSWORD, ROLE_CUSTOMER);

        ResponseEntity<TokenDto> loginResponse = login(CUSTOMER1, PASSWORD, TokenDto.class);
        String tokenId = loginResponse.getBody().getTokenId();

        headers.add("Authorization", tokenId);
        HttpEntity<String> requestEntity = new HttpEntity<>("", headers);
        ResponseEntity<String> profileResponse = restTemplate.exchange("/profile/me", GET, requestEntity, String.class);
        assertThat(profileResponse.getStatusCode(), is(OK));

        ResponseEntity<String> logoutResponse = logout(tokenId);
        assertThat(logoutResponse.getStatusCode(), is(ACCEPTED));

        ResponseEntity<String> profileResponseAfterLogout = restTemplate.exchange("/profile/me", GET, requestEntity, String.class);
        assertThat(profileResponseAfterLogout.getStatusCode(), is(FORBIDDEN));
    }
}
