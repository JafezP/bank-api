package com.jafp.bankapi.common.generator;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TransferReferenceGenerator {

  public String generate(){

    return "TRF-" +
            UUID.randomUUID()
                    .toString()
                    .replace("-", "")
                    .substring(0, 8)
                    .toUpperCase();
  }
}