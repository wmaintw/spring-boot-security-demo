package com.tw.security.demo.aop;

import com.tw.security.demo.aop.annotation.CheckOwnership;
import com.tw.security.demo.aop.annotation.ParamToCheck;
import com.tw.security.demo.aop.ownershipchecker.OwnershipChecker;
import com.tw.security.demo.domain.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Aspect
@Component
public class OwnershipCheckMonitor {

    @Autowired
    private ApplicationContext context;

    @Before("@annotation(com.tw.security.demo.aop.annotation.CheckOwnership)")
    public void performOwnershipCheck(JoinPoint joinPoint) throws Exception {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        OwnershipChecker checker = getCheckerFromMethodAnnotation(joinPoint);
        if (!checker.isSafeToSkip(currentUser)) {
            checker.check(currentUser, new TargetResourceId(String.valueOf(getParamValueToBeChecked(joinPoint))));
        }
    }

    private Object getParamValueToBeChecked(JoinPoint joinPoint) throws Exception {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Parameter[] parameters = method.getParameters();

        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].isAnnotationPresent(ParamToCheck.class)) {
                return joinPoint.getArgs()[i];
            }
        }

        throw new Exception("Invalid security permission check configuration.");
    }

    private OwnershipChecker getCheckerFromMethodAnnotation(JoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        CheckOwnership checkOwnershipAnnotation = method.getAnnotation(CheckOwnership.class);

        String ownershipCheckerBeanName = checkOwnershipAnnotation.value();
        return (OwnershipChecker) context.getBean(ownershipCheckerBeanName);
    }
}
