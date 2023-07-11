package com.projeto.store.services;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.projeto.store.services.UserService.*(..))")
    public void beforeUserMethods(JoinPoint joinPoint) {
        System.out.println("Before executing UserService method: " + joinPoint.getSignature());

        // Verificar o usuário logado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUser = authentication.getName();
        System.out.println("Logged in user: " + loggedInUser);
        
        // Acesso ao perfil do usuário e outras lógicas relacionadas podem ser implementadas aqui
        // Por exemplo, você pode chamar um método do serviço de usuário para obter o perfil do usuário com base no nome de usuário
        // userService.getUserProfile(loggedInUser);
    }

    @AfterReturning(pointcut = "execution(* com.projeto.store.services.UserService.*(..))", returning = "result")
    public void afterReturningUserMethods(JoinPoint joinPoint, Object result) {
        System.out.println("After executing UserService method: " + joinPoint.getSignature());
        System.out.println("Method returned: " + result);
    }

    // Adicione outros métodos de aspecto conforme necessário
}
