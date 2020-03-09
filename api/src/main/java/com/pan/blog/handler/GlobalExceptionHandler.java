package com.pan.blog.handler;

import com.pan.blog.utils.Result;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.session.ExpiredSessionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

  Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  public Result MethodArgumentNotValidExceptionHandle(MethodArgumentNotValidException exception){
    exception.printStackTrace();
    BindingResult result = exception.getBindingResult();
    StringBuilder errorMsg = new StringBuilder() ;

    if (result.hasErrors()) {
      List<FieldError> fieldErrors = result.getFieldErrors();
      fieldErrors.forEach(error -> {
        System.out.println("field" + error.getField() + ", msg:" + error.getDefaultMessage());
        errorMsg.append(error.getDefaultMessage()).append("!");
      });
    }

    exception.printStackTrace();
    return new Result(-1,errorMsg.toString() );
  }

  @ExceptionHandler(value = Exception.class)
  public Result exception(Exception e) {
    e.printStackTrace();
    return new Result(e);
  }

  @ExceptionHandler(value = UnauthorizedException.class)
  public Result handleUnauthorizedException(UnauthorizedException e) {
    logger.debug("UnauthorizedException", e);
    return new Result(HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(value = AuthenticationException.class)
  public Result handleAuthenticationException(AuthenticationException e) {
    logger.debug("AuthenticationException", e);
    return new Result(HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(value = AuthorizationException.class)
  public Result handleAuthorizationException(AuthorizationException e) {
    logger.debug("AuthorizationException", e);
    return new Result(HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(value = ExpiredSessionException.class)
  public Result handleExpiredSessionException(ExpiredSessionException e) {
    logger.debug("ExpiredSessionException", e);
    return new Result(HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public Result handleEntityNotFoundException(EntityNotFoundException e) {
    logger.debug("EntityNotFoundException", e);
    return new Result(e);
  }
}
