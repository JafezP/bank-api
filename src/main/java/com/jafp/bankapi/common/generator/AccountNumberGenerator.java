package com.jafp.bankapi.common.generator;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AccountNumberGenerator {

  public String generate(){

    return UUID.randomUUID()
            .toString()
            .replace("-", "")
            .substring(0, 12);
  }

}