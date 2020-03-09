package com.pan.blog.controller;

import com.pan.blog.entities.OperationLog;
import com.pan.blog.service.OperationLogService;
import com.pan.blog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/operationLog")
public class OperationLogController {

  @Autowired
  private OperationLogService operationLogService;

  @GetMapping("/{pageSize}/{page}")
  public Result listOperationLogs(@PathVariable Integer pageSize,
      @PathVariable(required = false) Integer page) {
    page = page == null ? 0 : page;
    Page<OperationLog> operationLogs = this.operationLogService.listOperationLogs(pageSize, page);
    return new Result(operationLogs);
  }

  @DeleteMapping("/{id}")
  @com.pan.blog.annotation.OperationLog("Delete operation log")
  public Result deleteOperationLog(@PathVariable Long id) {
    this.operationLogService.deleteOperationLog(id);
    return new Result();
  }
}
