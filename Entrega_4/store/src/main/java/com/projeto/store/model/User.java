package com.projeto.store.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String userId;
    private String name;
    private String email;
    private String password;
    private String dob;
    private List<String> platform;
    private List<String> plataformasQuePossui;
    private List<String> purchaseHistory;
    private String gender;
    private String addres;
    private String role;
    private long creditCard;
    private List<Jogo> jogos;
}
