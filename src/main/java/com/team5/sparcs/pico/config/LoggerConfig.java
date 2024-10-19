package com.team5.sparcs.pico.config;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Configuration
public class LoggerConfig {
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Around("@annotation(com.team5.sparcs.pico.config.Logging) && @annotation(logging)")
    public Object aroundLogger(ProceedingJoinPoint joinPoint, Logging logging) throws Throwable {
        RequestLog requestLog = new RequestLog();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        requestLog.setUri(request.getRequestURI());

        String httpMethod = request.getMethod();

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = methodSignature.getName();

        logger.info(" ====== [{}]{} [{}.{}()] start ======", httpMethod, requestLog.getUri(), className, methodName);

        Object result = null;
        try {
            result = joinPoint.proceed();
            requestLog.setResult("success");
        } catch (Throwable t) {
            requestLog.setResult("fail");
            throw t;
        } finally {
            requestLog.setMethod(methodName);
            requestLog.setAction(logging.action());

            logger.info(" ====== [{}]{} [{}.{}()] end ======", httpMethod, requestLog.getUri(), className, methodName);
        }
        return result;
    }
}