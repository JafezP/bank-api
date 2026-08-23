package com.jafp.bankapi.common.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record ErrorResponse(

        LocalDateTime timestamp,

        Integer status,

        String error,

        String message,

        String path,

        Map<String, List<String>> errors

) {}