package ru.geekbrains.backend.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    @Before("@annotation(ru.geekbrains.backend.annotation.MethodInfo)")
    public void beforeCallingMethod(JoinPoint point) {
        System.out.println("Method: " + point.getSignature().getName() + " in: "
                + point.getSignature().getDeclaringType() + " starts");
    }

    @AfterReturning("@annotation(ru.geekbrains.backend.annotation.MethodInfo)")
    public void afterCallingMethod(JoinPoint point) {
        System.out.println("Method: " + point.getSignature().getName() + " in: "
                + point.getSignature().getDeclaringType() + " is completed");
    }
}
