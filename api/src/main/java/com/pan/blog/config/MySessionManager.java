package com.pan.blog.config;

import java.io.Serializable;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

public class MySessionManager extends DefaultWebSessionManager {

  @Override
  protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
    HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
    String token = httpServletRequest.getHeader("Authorization");

    if (!StringUtils.isEmpty(token)) {
      request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,
          "Stateless Request");
      request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, token);
      request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
      return token;
    } else {
      return super.getSessionId(request, response);
    }
  }
}
