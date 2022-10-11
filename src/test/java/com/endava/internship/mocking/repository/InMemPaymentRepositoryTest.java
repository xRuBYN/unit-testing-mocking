package com.endava.internship.mocking.repository;

import com.endava.internship.mocking.model.Payment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.List;
import java.util.ArrayList;
import java.util.Optional;


public class InMemPaymentRepositoryTest {

    private InMemPaymentRepository repository;

    private Payment payment;
    private Payment payment1;

    @BeforeEach
    void setUp() {
        payment = new Payment(1, 2000.1, null);
        payment1 = new Payment(2, 2000.3, null);
        repository = new InMemPaymentRepository();
    }

    @Test
    void testSaveMethod() {
        Assertions.assertEquals(repository.save(payment), payment);
    }

    @Test
    void testFindAllElementsIntoRepo() {
        List<Payment> payments = new ArrayList<>();
        payments.add(payment);

        Assertions.assertEquals(payment, repository.save(payment));
        Assertions.assertEquals(payments, repository.findAll());
    }

    @Test
    void testFindAllElementsIfRepoIsNull() {
        List<Payment> payments = new ArrayList<>();
        Assertions.assertEquals(payments, repository.findAll());
    }

    @Test
    void testFindAllElementsIntoRepoByID() {
        Assertions.assertEquals(repository.save(payment1), payment1);
        Assertions.assertEquals(repository.save(payment), payment);
        Assertions.assertEquals(Optional.of(payment), repository.findById(payment.getPaymentId()));
    }

    @Test
    void testEditMessage() {
        String editMessage = "new message";

        repository.save(payment);
        repository.editMessage(payment.getPaymentId(), editMessage);

        Optional<Payment> newPayment = repository.findById(payment.getPaymentId());
        Assertions.assertEquals(newPayment.get().getMessage(), editMessage);
    }
}
