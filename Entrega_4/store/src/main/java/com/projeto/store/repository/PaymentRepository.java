package com.projeto.store.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.projeto.store.model.Payment;

public interface PaymentRepository extends MongoRepository<Payment, String>{

    Payment findByPaymentId(String paymentId);
    Payment findByUserId(String userId);
    
}
