package com.projeto.store.Aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
@Aspect
@Component
public class JogoServiceAJ {
    

    @Before("execution(* com.projeto.store.services.JogoService.*(..))")
    public void beforeGameMethods() {
        System.out.println("Before executing JogoService method: ");
    }
    @AfterReturning(pointcut = "execution(* com.projeto.store.services.JogoService.*(..))", returning = "result")
    public void afterReturningGameMethods(Object result) {
        System.out.println("After executing JogoService method: ");
        System.out.println("Method returned: " + result);
    }

    @After("execution(* com.projeto.store.services.JogoService.*(..))")
    public void afterGameMethods() {
        System.out.println("After executing JogoService method: ");
    }
    
}


// package com.projeto.store.services;

// import org.aspectj.lang.JoinPoint;
// import org.aspectj.lang.annotation.AfterReturning;
// import org.aspectj.lang.annotation.Aspect;
// import org.aspectj.lang.annotation.Before;
// import org.springframework.stereotype.Component;

// @Aspect
// @Component
// public class LoggingAspect {

//     @Before("execution(* com.projeto.store.services.UserService.*(..))")
//     public void beforeUserMethods(JoinPoint joinPoint) {
//         System.out.println("Before executing UserService method: " + joinPoint.getSignature());
//     }

//     @AfterReturning(pointcut = "execution(* com.projeto.store.services.UserService.*(..))", returning = "result")
//     public void afterReturningUserMethods(JoinPoint joinPoint, Object result) {
//         System.out.println("After executing UserService method: " + joinPoint.getSignature());
//         System.out.println("Method returned: " + result);
//     }


    
//     // Adicione outros métodos de aspecto conforme necessário
// }