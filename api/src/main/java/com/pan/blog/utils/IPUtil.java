package com.pan.blog.utils;

import javax.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

public class IPUtil {

  public static String getIp(HttpServletRequest request) {
//    String ip = request.getHeader("X-Real-IP");
//    if (!StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
//      return ip;
//    }

    String ip = request.getHeader("X-Forwarded-For");
    if (!StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
      int index = ip.indexOf(",");
      if (index == -1) {
        return ip;
      } else {
        return ip.substring(0, index);
      }
    }

    return request.getRemoteAddr();
  }
}
