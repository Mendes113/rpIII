package com.projeto.store.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.store.model.Jogo;
import com.projeto.store.repository.JogoRepository;

@Service
public class JogoService {
    
    @Autowired
    private JogoRepository jogoRepository;

    public Jogo createJogo(Jogo jogo) {
        jogo.setJogoId(UUID.randomUUID().toString().split("-")[0]);
        return jogoRepository.save(jogo);
    }


    public List<Jogo> getAllJogos() {
        return jogoRepository.findAll();
    }

    public Jogo getJogoById(String id) {
        return jogoRepository.findById(id).orElse(null);
    }

    public Jogo getJogoByNome(String nome) {
        return jogoRepository.findByNome(nome);
    }

    public List<Jogo> getJogoByCategoria(String categoria) {
        return jogoRepository.findByCategoria(categoria);
    }

    public Jogo updateJogo(String jogoId, Jogo jogoRequest) {
        Jogo existingJogo = jogoRepository.findById(jogoId).orElse(null);
        if (existingJogo != null) {
            existingJogo.setNome(jogoRequest.getNome());
            existingJogo.setDescricao(jogoRequest.getDescricao());
            existingJogo.setPreco(jogoRequest.getPreco());
            existingJogo.setCategoria(jogoRequest.getCategoria());
            return jogoRepository.save(existingJogo);
        } else {
            return null; // Handle case when jogo does not exist
        }
    }


    public String deleteJogo(String jogoId) {
        jogoRepository.deleteById(jogoId);
        return jogoId;
    }


   public boolean verifyExistingJogoByName(String nome) {
    Jogo existingJogo = jogoRepository.findByNome(nome);
    return existingJogo != null;
}

public Double getPriceById(String jogoId) {
    Jogo existingJogo = jogoRepository.findById(jogoId).orElse(null);
    if (existingJogo != null) {
        return existingJogo.getPreco();
    } else {
        return null; // Handle case when jogo does not exist
    }
}

public String getNameById(String jogoId) {
    Jogo existingJogo = jogoRepository.findById(jogoId).orElse(null);
    if (existingJogo != null) {
        return existingJogo.getNome();
    } else {
        return null; // Handle case when jogo does not exist
    }
}



}

