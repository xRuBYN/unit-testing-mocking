package com.endava.internship.mocking.service;

import com.endava.internship.mocking.model.Payment;
import com.endava.internship.mocking.model.Status;
import com.endava.internship.mocking.model.User;
import com.endava.internship.mocking.repository.PaymentRepository;
import com.endava.internship.mocking.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    private UserRepository userRepository = Mockito.mock(UserRepository.class);
    private PaymentRepository paymentRepository = Mockito.mock(PaymentRepository.class);

    private ValidationService validationService = Mockito.mock(ValidationService.class);

    private PaymentService paymentService;

    private ArgumentCaptor<Payment> argumentCaptor;
    private User user;

    private Payment payment;


    @BeforeEach
    void setUp() {
        paymentService = new PaymentService(userRepository, paymentRepository, validationService);
        argumentCaptor = ArgumentCaptor.forClass(Payment.class);
        user = new User(1, "John", Status.ACTIVE);
        payment = new Payment(1, 2000.1, "Payment from user John");

    }

    @Test
    void testCreatePaymentIfAddInRepoItShouldExist() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(paymentRepository.save(argumentCaptor.capture())).thenReturn(payment);

        Payment createPayment = paymentService.createPayment(user.getId(), 2000.1);

        Assertions.assertEquals(payment, createPayment);

        Mockito.verify(validationService).validateAmount(2000.1);
        Mockito.verify(validationService).validateUserId(user.getId());
        Mockito.verify(validationService).validateUser(user);
    }

    @Test
    void testEditMessage() {
        String newMessage = "";
        when(paymentRepository.editMessage(payment.getPaymentId(), newMessage)).thenReturn(payment);
        Payment paymentNewMessage = paymentService.editPaymentMessage(payment.getPaymentId(), newMessage);

        Assertions.assertEquals(paymentNewMessage, payment);

        Mockito.verify(validationService).validatePaymentId(payment.getPaymentId());
        Mockito.verify(validationService).validateMessage(newMessage);
    }

    @Test
    void testGetAllByAmountExceeding() {
        Payment payment1 = new Payment(2, 100d, "No message");
        Payment payment2 = new Payment(3, 1000d, "No message");
        Payment payment3 = new Payment(4, 10000d, "No message");
        Payment payment4 = new Payment(5, 100001d, "No message");

        List<Payment> list = Arrays.asList(payment1, payment2, payment3, payment4);

        when(paymentRepository.findAll()).thenReturn(list);

        List<Payment> paymentsAmountList = paymentService.getAllByAmountExceeding(100000d);

        Assertions.assertEquals(Collections.singletonList(payment4), paymentsAmountList);
    }
}
