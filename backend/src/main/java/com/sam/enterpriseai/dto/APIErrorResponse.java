package com.sam.enterpriseai.dto;

import java.time.Instant;

public record APIErrorResponse(

        Instant timestamp,

        int status,

        String error,

        String message,

        String path

) {
}