package com.pan.blog.aspect;

import com.pan.blog.entities.OperationLog;
import com.pan.blog.exceptions.CommonException;
import com.pan.blog.service.OperationLogService;
import com.pan.blog.utils.IPUtil;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class OperationLogAspect {

  @Autowired
  private OperationLogService operationLogService;

  @Pointcut("@annotation(com.pan.blog.annotation.OperationLog)")
  public void pointCut(){
  }

  @Around("pointCut()")
  public Object around(ProceedingJoinPoint proceedingJoinPoint) {
    Object result = null;
    Long startTime = System.currentTimeMillis();
    try {
      result = proceedingJoinPoint.proceed();
    } catch (Throwable throwable) {
      throwable.printStackTrace();
      throw new CommonException(throwable.getMessage());
    }
    Long endTime = System.currentTimeMillis();
    OperationLog operationLog = new OperationLog();
    operationLog.setTimeSpent(endTime - startTime);
    ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
        .getRequestAttributes();
    HttpServletRequest request = Objects.requireNonNull(requestAttributes).getRequest();
    String ip = IPUtil.getIp(request);
    operationLog.setIp(ip);
    this.operationLogService.addOperationLog(proceedingJoinPoint, operationLog);
    return result;
  }
}
