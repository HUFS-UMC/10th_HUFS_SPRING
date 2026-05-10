package com.wonho.sample_project.global.api.code;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@Builder
public class ValidationErrorCode implements BaseErrorCode {

    @JsonIgnore
    private List<FieldError> fieldErrors;

    ValidationErrorCode(List<FieldError> fieldErrors){
        this.fieldErrors = fieldErrors;
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getCode() {
        return "VALIDATION_ERROR";
    }

    @Override
    public Object getMessage() {
        return fieldErrors;
    }

    @Getter
    @Builder
    @Setter
    public static class FieldError{
        private String field;
        private String value;
        private String reason;
    }
}
