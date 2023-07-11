package com.projeto.store.services;

import java.util.List;
import java.util.UUID;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.projeto.store.model.Payment;
import com.projeto.store.repository.PaymentRepository;

import ch.qos.logback.classic.Logger;


@Service
public class PaymentService {
    
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(PaymentService.class);


    @Autowired
    private PaymentRepository paymentRepository;

  
    //crud

    public Payment createPayment(Payment payment) {
    LOGGER.info("Creating payment: {}", payment);
    payment.setPaymentId(UUID.randomUUID().toString().split("-")[0]);
    Payment createdPayment = paymentRepository.save(payment);
    LOGGER.info("Payment created: {}", createdPayment);
    return createdPayment;
}
    public Payment getPaymentById(String paymentId) {
    LOGGER.info("Retrieving payment by ID: {}", paymentId);
    Payment payment = paymentRepository.findByPaymentId(paymentId);
    LOGGER.info("Retrieved payment: {}", payment);
    return payment;
}

    public Payment getPaymentByUserId(String userId){
        return paymentRepository.findByUserId(userId);
    }

    public Payment updatePayment(String paymentId, Payment paymentRequest){
        Payment existingPayment = paymentRepository.findByPaymentId(paymentId);
        if(existingPayment != null){
            existingPayment.setUserId(paymentRequest.getUserId());
            existingPayment.setUserName(paymentRequest.getUserName());
            existingPayment.setUserEmail(paymentRequest.getUserEmail());
        
            existingPayment.setCreditCard(paymentRequest.getCreditCard());
            existingPayment.setJogos(paymentRequest.getJogos());
            existingPayment.setPaid(paymentRequest.isPaid());
            existingPayment.setPaymentDate(paymentRequest.getPaymentDate());
            return paymentRepository.save(existingPayment);
        }else{
            return null; // Handle case when user does not exist
        }
    }

    public String deletePayment(String paymentId){
        paymentRepository.deleteById(paymentId);
        return paymentId;
    }


    public boolean existPayment(String paymentId){
        Payment existingPayment = paymentRepository.findByPaymentId(paymentId);
        if(existingPayment != null){
            return true;
        }else{
            return false;
        }
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    // public List<Payment> getAllPaymentsByUserId(String userId) {
    //     return paymentRepository.findAllById()
    // }

    public boolean approvePayment(String paymentId){
        Payment existingPayment = paymentRepository.findByPaymentId(paymentId);
        if(existingPayment != null){
            
            existingPayment.setPaid(true);
            return true;
        }else{
            return false;
        }
    }

  


// private List<Jogo> getJogosByIds(List<String> jogoIds) {
//     List<Jogo> jogos = new ArrayList<>();
//     for (String jogoId : jogoIds) {
//         Jogo jogo = jogoService.getJogoById(jogoId);
//         if (jogo != null) {
//             jogos.add(jogo);
//         }
//     }
//     return jogos;
// }





}