package com.projeto.store.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.projeto.store.model.Jogo;
import com.projeto.store.model.User;
import com.projeto.store.repository.UserRepository;

import ch.qos.logback.classic.Logger;


@Service
public class UserService {
    
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JogoService jogoService;

    public User createUser(User user) {
    if (verifyExistingUser(user.getEmail())) {
        LOGGER.info("User already exists for email: {}", user.getEmail());
        return null;
    }

    user.setUserId(UUID.randomUUID().toString().split("-")[0]);
    User createdUser = userRepository.save(user);
    
    LOGGER.info("User created: {}", createdUser.getUserId());

    return createdUser;
}


    public User getUserById(String userId){
        return userRepository.findByUserId(userId);
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User updateUser(String userId, User userRequest){
        User existingUser = userRepository.findByUserId(userId);
        if(existingUser != null){
           existingUser.setName(userRequest.getName());
            existingUser.setEmail(userRequest.getEmail());
            existingUser.setPassword(userRequest.getPassword());
            existingUser.setRole(userRequest.getRole());
            existingUser.setCreditCard(userRequest.getCreditCard());
            existingUser.setJogos(userRequest.getJogos());
            existingUser.setAddres(userRequest.getAddres());
            existingUser.setPlatform(userRequest.getPlatform());

            return userRepository.save(existingUser);
        }else{
            return null; // Handle case when user does not exist
        }
    }
    

     public String deleteUser(String userId) {
        LOGGER.info("Deleting user with ID: {}", userId);
        userRepository.deleteById(userId);
        return userId;
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getUserByName(String name) {
        return userRepository.findByName(name);
    }

    public List<User> getUserByRole(String role) {
        return userRepository.findByRole(role);
    }

    public boolean authenticateUser(String email, String password) {
    LOGGER.info("Authenticating user with email: {}", email);
    User user = userRepository.findByEmail(email);
    if (user != null && user.getPassword().equals(password)) {
        LOGGER.info("User authenticated successfully: {}", user);
        return true;
    }
    LOGGER.warn("User authentication failed for email: {}", email);
    return false;
    }

    public User getUserByEmailAndPassword(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

        public List<Jogo> getUserGames(String userId) {
        User user = userRepository.findByUserId(userId);
        if (user != null) {
            return user.getJogos();
        }
        return null;
    }

    public User getCurrentUser(String email) {
        return userRepository.findByEmail(email);
    }
    
    public User updateJogos(String userId, List<Jogo> jogos) {
    User existingUser = userRepository.findByUserId(userId);
    if (existingUser != null) {
        existingUser.setJogos(jogos);
        return userRepository.save(existingUser);
    } else {
        return null; //caso usario n exista
    }
}


    public boolean verifyExistingUser(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }


public void addJogo(String userId, String jogoId) {
    User user = userRepository.findByUserId(userId);
    if (user != null) {
        Jogo jogo = jogoService.getJogoById(jogoId);
        if (jogo != null) {
            List<Jogo> jogos = user.getJogos();
            if (jogos == null) {
                jogos = new ArrayList<>();
            }
            jogos.add(jogo);
            user.setJogos(jogos);
            userRepository.save(user);
        }
    }
}


    public String getUserIdByEmail(String email) {
    User user = userRepository.findByEmail(email);
    if (user != null) {
        String userId = user.getUserId();
        return userId;
    }
    return null;
}


public boolean verifyExistingGameInAccount(String userId, String jogoId) {
    User user = userRepository.findByUserId(userId);
    if (user != null) {
        List<Jogo> jogos = user.getJogos();
        if (jogos != null) {
            for (Jogo jogo : jogos) {
                if (jogo.getJogoId().equals(jogoId)) {
                    return true;
                }
            }
        }
    }
    return false;
}


public User updateRole(String email, String role) {
    User user = userRepository.findByEmail(email);
    if (user != null) {
        user.setRole(role);
        return userRepository.save(user);
    }
    return null;
}

public String deleteUserByEmail(String email) {
    User user = getUserByEmail(email);
    if (user != null) {
        LOGGER.info("Deleting user with email: {}", email);
        userRepository.deleteById(user.getUserId());
        return "Usuário excluído com sucesso";
    } else {
        LOGGER.info("User not found for email: {}", email);
        return "Usuário não encontrado";
    }
}


}
