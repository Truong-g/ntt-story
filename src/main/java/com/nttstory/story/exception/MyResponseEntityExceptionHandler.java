package com.nttstory.story.exception;

import com.nttstory.story.dto.BaseResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.UnexpectedTypeException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class MyResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(SQLException.class)
    public BaseResponse<String, Object> handleSqlException(SQLException e, WebRequest request) {
        return new BaseResponse<String, Object>(HttpStatus.FORBIDDEN.value(), e.getLocalizedMessage(), null);
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(UnexpectedTypeException.class)
    public BaseResponse<String, Object> handleUnexpectedTypeException(UnexpectedTypeException e, WebRequest request) {
        return new BaseResponse<String, Object>(HttpStatus.FORBIDDEN.value(), e.getMessage(), null);
    }

    @Override
    @Nullable
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        BaseResponse<String, Object> response = new BaseResponse<>(status.value(), ex.getLocalizedMessage(), null);
        return ResponseEntity.badRequest().body(response);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().stream().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        BaseResponse<Map<String, String>, Object> response = new BaseResponse<>(status.value(), errors, null);
        return ResponseEntity.badRequest().body(response);
    }


    @Override
    public ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        BaseResponse<String, Object> response = new BaseResponse<>(status.value(), ex.getLocalizedMessage(), null);
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ResponseEntity<Object> handleNoSuchElementException(ItemNotFoundException ex, WebRequest request) {
        BaseResponse<String, Object> response = new BaseResponse<>(HttpStatus.FORBIDDEN.value(), ex.getLocalizedMessage(), null);
        return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        BaseResponse<String, Object> response = new BaseResponse<>(HttpStatus.FORBIDDEN.value(), "Incorrect password", null);
        return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(response);
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ResponseEntity<Object> handleInternalAuthenticationServiceException(InternalAuthenticationServiceException ex, WebRequest request) {
        BaseResponse<String, Object> response = new BaseResponse<>(HttpStatus.FORBIDDEN.value(), "Invalid username or password", null);
        return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(response);
    }

    @ExceptionHandler(MalformedJwtException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> handleMalformedJwtException(MalformedJwtException e, WebRequest request) {
        BaseResponse<String, Object> response = new BaseResponse<>(HttpStatus.UNAUTHORIZED.value(), "Invalid token", null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(response);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> handleExpiredJwtException(ExpiredJwtException e, WebRequest request) {
        BaseResponse<String, Object> response = new BaseResponse<>(HttpStatus.UNAUTHORIZED.value(), "Token has expired", null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(response);
    }
    @ExceptionHandler(UnsupportedJwtException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> handleUnsupportedJwtException(UnsupportedJwtException e, WebRequest request) {
        BaseResponse<String, Object> response = new BaseResponse<>(HttpStatus.UNAUTHORIZED.value(), "Token unsupported", null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e, WebRequest request) {
        BaseResponse<String, Object> response = new BaseResponse<>(HttpStatus.UNAUTHORIZED.value(), e.getLocalizedMessage(), null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(response);
    }



}
