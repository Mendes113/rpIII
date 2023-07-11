package com.projeto.store.Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PaymentServiceAJ {

    @Before("execution(* com.projeto.store.services.PaymentService.*(..))")
    public void beforePaymentMethods(JoinPoint joinPoint) {
        System.out.println("Before executing PaymentService method: " + joinPoint.getSignature());
    }

    @AfterReturning(pointcut = "execution(* com.projeto.store.services.PaymentService.*(..))", returning = "result")
    public void afterReturningPaymentMethods(JoinPoint joinPoint, Object result) {
        System.out.println("After executing PaymentService method: " + joinPoint.getSignature());
        System.out.println("Method returned: " + result);
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