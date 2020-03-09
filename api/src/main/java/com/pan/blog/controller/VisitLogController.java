package com.pan.blog.controller;

import com.pan.blog.annotation.OperationLog;
import com.pan.blog.entities.VisitLog;
import com.pan.blog.service.VisitLogService;
import com.pan.blog.utils.Result;
import java.util.Calendar;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/visitLog")
public class VisitLogController {

  @Autowired
  private VisitLogService visitLogService;

  @GetMapping({"/{pageSize}/{page}"})
  public Result listVisitLog(@PathVariable Integer pageSize,
      @PathVariable(required = false) Integer page,
      @RequestParam(required = false) Integer startYear,
      @RequestParam(required = false) Integer startMonth,
      @RequestParam(required = false) Integer startDay,
      @RequestParam(required = false) Integer endYear,
      @RequestParam(required = false) Integer endMonth,
      @RequestParam(required = false) Integer endDay) {
    page = page == null ? 0 : page;
    Date startDate = null, endDate = null;
    if (startYear != null) {
      Calendar start = Calendar.getInstance();
      start.set(startYear, startMonth, startDay);
      startDate = start.getTime();
    }
    if (endYear != null) {
      Calendar end = Calendar.getInstance();
      end.set(endYear, endMonth, endDay);
      endDate = end.getTime();
    }
    Page<VisitLog> visitLogs = this.visitLogService
        .listVisitLog(pageSize, page, startDate, endDate);
    return new Result(visitLogs);
  }

  @DeleteMapping("/{id}")
  @OperationLog("Delete visit log")
  public Result deleteVisitLog(@PathVariable Long id) {
    this.visitLogService.deleteVisitLog(id);
    return new Result();
  }
}
