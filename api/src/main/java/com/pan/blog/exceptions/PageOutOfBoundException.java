package com.pan.blog.exceptions;

public class PageOutOfBoundException extends RuntimeException {

  public PageOutOfBoundException() {
    super("Page out of bound");
  }
}
