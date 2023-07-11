package com.projeto.store.services;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.projeto.store.services.UserService.*(..))")
    public void beforeUserMethods(JoinPoint joinPoint) {
        System.out.println("Before executing UserService method: " + joinPoint.getSignature());

        // Verificar o usuário logado
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String loggedInUser = request.getUserPrincipal().getName();
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

    @Before("execution(* com.projeto.store.services.UserService.deleteUser(..)) && args(userId)")
    public void beforeDeleteUser(String userId) {
        LOGGER.info("Deleting user with ID: {}", userId);
    }
}
