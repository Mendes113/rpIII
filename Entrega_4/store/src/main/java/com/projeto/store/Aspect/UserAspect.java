package com.projeto.store.Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.projeto.store.model.User;

@Aspect
@Component
public class UserAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAspect.class);

    @Before("execution(* com.projeto.store.services.UserService.createUser(..))")
    public void beforeCreateUser(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args.length > 0 && args[0] instanceof User) {
            User user = (User) args[0];
            LOGGER.info("Creating user: {}", user);
        }
    }

    @AfterReturning("execution(* com.projeto.store.services.UserService.deleteUser(..))")
    public void afterDeleteUser(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args.length > 0 && args[0] instanceof String) {
            String userId = (String) args[0];
            LOGGER.info("Deleting user with ID: {}", userId);
        }
    }

}
