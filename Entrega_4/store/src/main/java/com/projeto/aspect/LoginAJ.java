package com.projeto.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ch.qos.logback.classic.Logger;

@Aspect
@Component
public class LoginAJ {
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(LoginAJ.class);

    @Pointcut("execution(* com.projeto.store.services.UserService.authenticateUser(..))")
    public void authenticateUserPointcut() {
    }

    @AfterReturning("authenticateUserPointcut() && args(email, password)")
    public void logSuccessfulLogin(String email, String password) {
        LOGGER.info("Successful login with email: {}", email);
    }

    @AfterReturning(value = "authenticateUserPointcut() && args(email, password)", returning = "result")
    public void logFailedLogin(String email, String password, boolean result) {
        if (!result) {
            LOGGER.warn("Failed login attempt with email: {}", email);
        }
    }
}
