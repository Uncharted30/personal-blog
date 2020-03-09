package com.pan.blog.service.interfaces;

import com.pan.blog.entities.OperationLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;

public interface IOperationLogService {

  Page<OperationLog> listOperationLogs(Integer pageSize, Integer page);

  void deleteOperationLog(Long id);

  @Async
  void addOperationLog(ProceedingJoinPoint proceedingJoinPoint, OperationLog operationLog);
}
