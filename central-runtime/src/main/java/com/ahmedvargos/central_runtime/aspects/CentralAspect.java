package com.ahmedvargos.central_runtime.aspects;


import android.util.Log;

import com.ahmedvargos.central_runtime.Central;
import com.ahmedvargos.central_runtime.annotations.RestrictToType;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

@Aspect
public class CentralAspect {
    private static final String RESTRICT_TO_TYPE = "execution(@com.ahmedvargos.central_runtime.annotations.RestrictToType * * (..))";
    private static final String LOG_ENTER_METHOD = "execution(@com.ahmedvargos.central_runtime.annotations.LogAtEntry * * (..))";

    @Pointcut(value = RESTRICT_TO_TYPE)
    public void RestrictToTypeAnnotationMethod() {

    }

    @Pointcut(value = LOG_ENTER_METHOD)
    public void LogAtEntryAnnotationMethod() {

    }

    @Around("RestrictToTypeAnnotationMethod()")
    public void checkMethodType(ProceedingJoinPoint joinPoint) throws Throwable{ //, RestrictToRatePlan annotation
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RestrictToType annotation = method.getAnnotation(RestrictToType.class);

        for (int i : annotation.type()) {
            if(Central.getType() == i){
                joinPoint.proceed();
            }
        }

    }

    @Before("LogAtEntryAnnotationMethod()")
    public void enterLogMethod(JoinPoint joinPoint) throws Throwable{ //, RestrictToRatePlan annotation
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        Log.v(method.getDeclaringClass().getSimpleName(), "The " + method.getName() + " has been accessed!");
    }
}
