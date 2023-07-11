package com.projeto.store.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.projeto.store.model.User;

public interface UserRepository  extends MongoRepository<User, String>{

    User findByEmail(String email);
    User findByUserId(String userId);
    @Query("{ 'name' : ?0 }")
    List<User> findByName(String name);
    @Query("{ 'role' : ?0 }")
    List<User> findByRole(String role);
    
}
