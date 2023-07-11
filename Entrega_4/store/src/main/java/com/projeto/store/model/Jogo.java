package com.projeto.store.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "jogos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Jogo {
    @Id
    private String jogoId;
    private String nome;
    private String descricao;
    private double preco;
    private String categoria;
    private List<String> plataformList;
    // private String image; // tentar usar a url da imagem aqui

}
