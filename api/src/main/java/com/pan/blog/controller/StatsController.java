package com.pan.blog.controller;

import com.pan.blog.service.StatsService;
import com.pan.blog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/stats")
public class StatsController {

  @Autowired
  private StatsService statsService;

  @GetMapping("/general")
  public Result getGeneralStats() {
    return new Result(this.statsService.getGeneralStats());
  }
}
