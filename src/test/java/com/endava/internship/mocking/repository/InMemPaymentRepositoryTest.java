package com.endava.internship.mocking.repository;

import com.endava.internship.mocking.model.Payment;
import com.endava.internship.mocking.model.Status;
import com.endava.internship.mocking.model.User;
import com.endava.internship.mocking.repository.InMemPaymentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class InMemPaymentRepositoryTest {

    private InMemPaymentRepository repository;

    private User user1;
    private User user2;
    private Payment payment;
    private Payment payment1;

    @BeforeEach
    void setUp() {
        user1 = new User(1, "John", Status.ACTIVE);
        user2 = new User(2, "Maria", Status.ACTIVE);
        payment = new Payment(user1.getId(),2000.1,null);
        payment1 = new Payment(user2.getId(),2000.3,null);
        repository = new InMemPaymentRepository();
    }

    @Test
    void testSaveMethod() {
        Assertions.assertEquals(repository.save(payment),payment);
    }

    @Test
    void testFindAllElementsIntoRepo() {
        List<Payment> payments = new ArrayList<>();
        payments.add(payment);
        Assertions.assertEquals(repository.save(payment),payment);
        Assertions.assertEquals(payments,repository.findAll());
    }

    @Test
    void testFindAllElementsIfRepoIsNull() {
        List<Payment> payments = new ArrayList<>();
        Assertions.assertEquals(payments,repository.findAll());
    }

    @Test
    void testFindAllElementsIntoRepoByID() {
        Assertions.assertEquals(repository.save(payment1),payment1);
        Assertions.assertEquals(repository.save(payment),payment);
        Assertions.assertEquals(Optional.of(payment), repository.findById(payment.getPaymentId()));
    }

    @Test
    void testEditMessage() {
        String editMessage = "new message";
        repository.save(payment);
        repository.editMessage(payment.getPaymentId(),editMessage);
        Optional<Payment> newPayment = repository.findById(payment.getPaymentId());
        Assertions.assertEquals(newPayment.get().getMessage(),editMessage);
    }
}
