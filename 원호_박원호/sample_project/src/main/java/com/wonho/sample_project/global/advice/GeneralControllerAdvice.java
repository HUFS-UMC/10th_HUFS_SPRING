package com.wonho.sample_project.global.advice;

import com.wonho.sample_project.global.api.ApiResponse;
import com.wonho.sample_project.global.api.code.BaseErrorCode;
import com.wonho.sample_project.global.api.code.ValidationErrorCode;
import com.wonho.sample_project.global.exception.GeneralHttpException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GeneralControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ValidationErrorCode handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final List<ValidationErrorCode.FieldError> fieldErrors = getFieldErrors(e.getBindingResult());

        return ValidationErrorCode.builder().fieldErrors(fieldErrors).build();
    }

    private List<ValidationErrorCode.FieldError> getFieldErrors(BindingResult bindingResult) {
        final List<FieldError> errors = bindingResult.getFieldErrors();
        return errors.parallelStream()
                .map(error -> ValidationErrorCode.FieldError.builder()
                        .reason(error.getDefaultMessage())
                        .field(error.getField())
                        .value((String) error.getRejectedValue())
                        .build())
                .collect(Collectors.toList());
    }

    @ExceptionHandler(GeneralHttpException.class)
    protected ResponseEntity<?> handleGeneralException(GeneralHttpException e){
        ApiResponse<BaseErrorCode> response = ApiResponse.onFailure(e.getErrorCode(), null);
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(response);
    }
}
