package com.projeto.store.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "payment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    private String paymentId;
    private String userId;
    private String userName;
    private String userEmail;
    private String creditCard;
    private int cvv;
    private double amount;
    private List<Jogo> jogos;
    private boolean isPaid;
    private String paymentDate;
    private String jogoId;
    private String jogoNome;
   
}
