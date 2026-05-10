package com.wonho.sample_project.global.api.code;

import org.springframework.http.HttpStatus;

public interface BaseErrorCode {
    HttpStatus getStatus();
    String getCode();
    Object getMessage();
}
