package com.endava.internship.mocking.service;


import com.endava.internship.mocking.model.Payment;
import com.endava.internship.mocking.model.Status;
import com.endava.internship.mocking.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BasicValidationServiceTest {


    private BasicValidationService basicValidationService;

    private User user;
    private Payment payment;

    @BeforeEach
    void setUp() {
        basicValidationService = new BasicValidationService();
        user = new User(1, "John", Status.INACTIVE);
        payment = new Payment(user.getId(),2000.1,null);
    }

    @Test
    void testValidateUserId() {
        try {
            basicValidationService.validateUserId(null);
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals(e.getMessage(),"User id must not be null");
        }
    }

    @Test
    void testValidateUser() {
        try {
            basicValidationService.validateUser(user);
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals(e.getMessage(),"User with id " + user.getId() + " not in ACTIVE status");
        }
    }

    @Test
    void testValidateMessage() {
        try {
           basicValidationService.validateMessage(null);
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals(e.getMessage(),"Payment message must not be null");
        }
    }

    @Test
    void testValidationAmountWhenIsNull() {
        try {
            basicValidationService.validateAmount(null);
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals(e.getMessage(),"Amount must not be null");
        }
    }

    @Test
    void testValidationAmountWhenIsLessThenZero() {
        try {
            basicValidationService.validateAmount(-5d);
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals(e.getMessage(),"Amount must be greater than 0");
        }
    }

    @Test
    void testValidatePaymentId() {
        try{
            basicValidationService.validatePaymentId(null);
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals(e.getMessage(),"Payment id must not be null");
        }
    }
}
