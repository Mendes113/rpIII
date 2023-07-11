package com.projeto.store.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.projeto.store.model.Jogo;

public interface JogoRepository extends MongoRepository<Jogo, String> {
    
    Jogo findByNome(String nome);

    @Query("{ 'categoria' : ?0 }")
    List <Jogo> findByCategoria(String categoria);
}
