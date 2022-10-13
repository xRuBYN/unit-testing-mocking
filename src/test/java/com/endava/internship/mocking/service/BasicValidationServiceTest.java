package com.endava.internship.mocking.service;

import com.endava.internship.mocking.model.Status;
import com.endava.internship.mocking.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BasicValidationServiceTest {
    private BasicValidationService basicValidationService;
    private User user;

    @BeforeEach
    void setUp() {
        basicValidationService = new BasicValidationService();
        user = new User(1, "John", Status.INACTIVE);
    }

    @Test
    void testValidateUserId() {
        IllegalArgumentException il = assertThrows(IllegalArgumentException.class,
                () -> basicValidationService.validateUserId(null));
        assertEquals("User id must not be null", il.getMessage());

    }

    @Test
    void testValidateUser() {
        IllegalArgumentException il = assertThrows(IllegalArgumentException.class,
                () -> basicValidationService.validateUser(user));
        assertEquals("User with id " + user.getId() + " not in ACTIVE status", il.getMessage());
    }

    @Test
    void testValidateMessage() {
        IllegalArgumentException il = assertThrows(IllegalArgumentException.class,
                () -> basicValidationService.validateMessage(null));
        assertEquals("Payment message must not be null", il.getMessage());
    }

    @Test
    void testValidationAmountWhenIsNull() {
        IllegalArgumentException il = assertThrows(IllegalArgumentException.class,
                () -> basicValidationService.validateAmount(null));
        assertEquals("Amount must not be null", il.getMessage());

    }

    @Test
    void testValidationAmountWhenIsLessThenZero() {
        IllegalArgumentException il = assertThrows(IllegalArgumentException.class,
                () -> basicValidationService.validateAmount(-5d));
        assertEquals("Amount must be greater than 0", il.getMessage());
    }

    @Test
    void testValidatePaymentId() {
        IllegalArgumentException il = assertThrows(IllegalArgumentException.class,
                () -> basicValidationService.validatePaymentId(null));
        assertEquals("Payment id must not be null", il.getMessage());
    }
}
