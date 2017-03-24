package com.tw.security.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.security.demo.domain.Order;
import com.tw.security.demo.domain.User;
import com.tw.security.demo.domain.dto.TokenDto;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class OrderAccessPermissionCheckTest extends BaseTest {

    @Autowired
    private ObjectMapper objectMapper;

    // ------------------------------
    // Order list

    @Test
    public void should_return_all_the_orders_given_current_user_has_admin_role() throws Exception {
        User admin = givenAnExistingAdmin(ADMIN);
        TokenDto adminToken = login(admin);

        ResponseEntity<String> response = get("/orders", adminToken, String.class);

        assertThat(response.getStatusCode(), is(OK));
        List<Order> orders = objectMapper.readValue(response.getBody(), List.class);
        assertThat(orders.size(), is(4));
    }

    @Test
    public void request_should_be_rejected_when_try_to_get_all_the_orders_given_current_user_has_customer_role() throws Exception {
        User customer = givenAnExistingCustomer(CUSTOMER1);
        TokenDto customerToken = login(customer);

        ResponseEntity<ErrorResponseDto> response = get("/orders", customerToken, ErrorResponseDto.class);

        assertThat(response.getStatusCode(), is(UNAUTHORIZED));
        ErrorResponseDto errorResponseDto = response.getBody();
        assertThat(errorResponseDto.getErrorMessage(), is("Access is denied"));
    }

    // ------------------------------
    // Single order

    @Test
    public void should_return_order_resource_given_current_customer_is_the_owner() throws Exception {
        User customer = givenAnExistingCustomer(CUSTOMER1);
        TokenDto customerToken = login(customer);

        Integer storeId = 1;
        Order orderInDB = testDataUtils.createOrder(customer.getId(), storeId);

        int orderIdOfGivenDefaultCustomer = orderInDB.getId();
        ResponseEntity<Order> response = get(String.format("/orders/%s", orderIdOfGivenDefaultCustomer), customerToken, Order.class);
        assertThat(response.getStatusCode(), is(OK));

        Order order = response.getBody();
        assertThat(order.getOwnerId(), is(customerToken.getUserId()));
    }

    @Test
    public void should_not_return_order_resource_given_current_customer_is_not_the_owner() throws Exception {
        User customer1 = givenAnExistingCustomer(CUSTOMER1);
        User customer2 = givenAnExistingCustomer(CUSTOMER2);
        Integer storeId = 1;
        Order orderInDB = testDataUtils.createOrder(customer1.getId(), storeId);

        TokenDto customerToken = login(customer2);

        int orderIdOfAnotherCustomer = orderInDB.getId();
        ResponseEntity<ErrorResponseDto> response = get(String.format("/orders/%s", orderIdOfAnotherCustomer), customerToken, ErrorResponseDto.class);
        assertThat(response.getStatusCode(), is(UNAUTHORIZED));

        ErrorResponseDto errorResponseDto = response.getBody();
        assertThat(errorResponseDto.getErrorMessage(), is("Access denied as you are not the owner of the resource."));
    }

    @Test
    public void should_return_order_resource_given_current_service_consultant_is_in_charge_of_this_order() throws Exception {
        User customer = givenAnExistingCustomer(CUSTOMER1);
        Integer storeId = 1;
        Order orderInDB = testDataUtils.createOrder(customer.getId(), storeId);

        User serviceConsultant = givenAnExistingServiceConsultant(D1_STORE1_SC1, storeId);
        TokenDto d1_store1_sc1 = login(serviceConsultant);

        int orderIdUnderD1Store1 = orderInDB.getId();
        ResponseEntity<Order> response = get(String.format("/orders/%s", orderIdUnderD1Store1), d1_store1_sc1, Order.class);
        assertThat(response.getStatusCode(), is(OK));

        Order order = response.getBody();
        assertThat(order.getOwnerId(), not(d1_store1_sc1.getUserId()));
    }

    @Test
    public void should_not_return_order_resource_given_current_service_consultant_is_not_in_charge_of_this_resource() throws Exception {
        User customer1 = givenAnExistingCustomer(CUSTOMER1);
        Integer storeId = 1;
        Order orderInDB = testDataUtils.createOrder(customer1.getId(), storeId);

        Integer anotherStoreId = 99;
        User serviceConsultant = givenAnExistingServiceConsultant(D1_STORE1_SC1, anotherStoreId);
        TokenDto d1_store1_sc1 = login(serviceConsultant);

        int orderIdUnderD1Store2 = orderInDB.getId();
        ResponseEntity<ErrorResponseDto> response = get(String.format("/orders/%s", orderIdUnderD1Store2), d1_store1_sc1, ErrorResponseDto.class);
        assertThat(response.getStatusCode(), is(UNAUTHORIZED));

        ErrorResponseDto errorResponseDto = response.getBody();
        assertThat(errorResponseDto.getErrorMessage(), is("Access denied as you are not in charge of the resource."));
    }

    @Test
    public void should_return_order_resource_given_current_user_has_admin_role() throws Exception {
        User customer = givenAnExistingCustomer(CUSTOMER1);
        Integer storeId = 1;
        Order orderInDB = testDataUtils.createOrder(customer.getId(), storeId);

        User admin = givenAnExistingAdmin(ADMIN);
        TokenDto adminToken = login(admin);

        int orderIdOfDefaultCustomer = orderInDB.getId();
        ResponseEntity<Order> response = get(String.format("/orders/%s", orderIdOfDefaultCustomer), adminToken, Order.class);
        assertThat(response.getStatusCode(), is(OK));

        Order order = response.getBody();
        assertThat(order.getOwnerId(), not(adminToken.getUserId()));
    }
}
