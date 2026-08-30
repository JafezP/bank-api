package com.jafp.bankapi.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI bankApiOpenAPI(){

    return new OpenAPI()
            .info(
                    new Info()
                            .title("Bank API")
                            .description(
                                    "API REST para gestión bancaria de clientes, cuentas y transacciones"
                            )
                            .version("1.0.0")
            );

  }

}