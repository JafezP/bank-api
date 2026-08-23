package com.jafp.bankapi.common.exception;

public class ResourceNotFoundException
        extends RuntimeException {

  public ResourceNotFoundException(String message){
    super(message);
  }
}
