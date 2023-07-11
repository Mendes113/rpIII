package com.projeto.store.Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.projeto.store.model.Jogo;
import com.projeto.store.services.JogoService;
@Aspect
@Component
public class JogoServiceAJ {
    // The `@AfterReturning` annotation is used in Aspect-Oriented Programming (AOP) to specify that
    // the annotated method should be executed after a specific method is successfully executed and
    // returns a value.

    @Autowired
    private JogoService jogoService;
         private static final Logger LOGGER = LoggerFactory.getLogger(JogoServiceAJ.class);
@AfterReturning("execution(* com.projeto.store.services.JogoService.createJogo(..))")
public void afterCreateJogo(JoinPoint joinPoint) {
    Object[] args = joinPoint.getArgs();
    if (args.length > 0 && args[0] instanceof Jogo) {
        Jogo jogo = (Jogo) args[0];
        LOGGER.info("Jogo created: {}", jogo);
    }
}

@AfterReturning("execution(* com.projeto.store.services.JogoService.deleteJogo(..))")
public void afterDeleteJogo(JoinPoint joinPoint) {
    Object[] args = joinPoint.getArgs();
    if (args.length > 0 && args[0] instanceof String) {
        String jogoId = (String) args[0];
        Jogo jogo = jogoService.getJogoById(jogoId);
        if (jogo != null) {
            LOGGER.info("Jogo deleted: {}", jogo);
        }
    }
}

}