package com.pan.blog.aspect;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * logging aspect
 */
@Aspect
@Component
public class LogAspect {

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Pointcut("execution(* com.pan.blog.controller.*.*(..))")
  public void log() {
  }

  @Before("log()")
  public void before(JoinPoint joinPoint) {
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = attributes.getRequest();
    String url = request.getRequestURL().toString();
    String ip = request.getRemoteAddr();
    String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
    Object[] args = joinPoint.getArgs();
    logger.info(new RequestLog(url, ip, classMethod, args).toString());
  }

  @After("log()")
  public void after() {

  }

  @AfterReturning(returning = "result", pointcut = "log()")
  public void afterReturning(Object result) {
    logger.info("Result : {}", result);
  }

  private class RequestLog {
    private String url;
    private String ip;
    private String classMethod;
    private Object[] args;

    public RequestLog(String url, String ip, String classMethod, Object[] args) {
      this.url = url;
      this.ip = ip;
      this.classMethod = classMethod;
      this.args = args;
    }

    public String getUrl() {
      return url;
    }

    public String getIp() {
      return ip;
    }

    public String getClassMethod() {
      return classMethod;
    }

    public Object[] getArgs() {
      return args;
    }

    @Override
    public String toString() {
      return "{" +
          "url='" + url + '\'' +
          ", ip='" + ip + '\'' +
          ", classMethod='" + classMethod + '\'' +
          ", args=" + Arrays.toString(args) +
          '}';
    }
  }
}
