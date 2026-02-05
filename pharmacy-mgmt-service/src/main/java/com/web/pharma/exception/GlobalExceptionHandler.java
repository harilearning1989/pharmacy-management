package com.web.pharma.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;
import java.util.Map;

//@RestControllerAdvice
public class GlobalExceptionHandler {

    /*@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ProblemDetail handleMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpServletRequest request) {

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.METHOD_NOT_ALLOWED);
        problem.setTitle(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase());
        problem.setDetail(ex.getMessage());
        problem.setInstance(URI.create(request.getRequestURI()));

        return problem;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle(HttpStatus.BAD_REQUEST.getReasonPhrase());
        problem.setDetail("Validation failed");
        problem.setInstance(URI.create(request.getRequestURI()));

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));

        problem.setProperty("errors", errors);
        return problem;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneric(
            Exception ex,
            HttpServletRequest request) {

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problem.setTitle(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        problem.setDetail(ex.getMessage());
        problem.setInstance(URI.create(request.getRequestURI()));

        return problem;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrityViolation(
            DataIntegrityViolationException ex,
            HttpServletRequest request) {

        String rootMessage = ex.getMostSpecificCause().getMessage();

        ProblemDetail problem =
                ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        problem.setTitle("Invalid request data");

        // Custom friendly message for gender constraint
        if (rootMessage != null && rootMessage.contains("chk_gender")) {
            problem.setDetail("Invalid gender value. Allowed values are M, F, or O.");
        } else {
            problem.setDetail(rootMessage);
        }

        problem.setInstance(URI.create(request.getRequestURI()));
        return problem;
    }*/

    /*@ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrityViolation(
            DataIntegrityViolationException ex,
            HttpServletRequest request) {

        String rootMessage = ex.getMostSpecificCause().getMessage();

        ProblemDetail problem =
                ProblemDetail.forStatus(HttpStatus.CONFLICT);

        problem.setTitle("Duplicate resource");

        if (rootMessage != null) {
            if (rootMessage.contains("uq_customers_phone")) {
                problem.setDetail("Phone number already exists.");
            } else if (rootMessage.contains("chk_gender")) {
                problem.setDetail("Invalid gender value. Allowed values are M, F, or O.");
            } else {
                problem.setDetail("Invalid data submitted.");
            }
        }

        problem.setInstance(URI.create(request.getRequestURI()));
        return problem;
    }*/

    /*@ExceptionHandler(CustomerNotFoundException.class)
    public ProblemDetail handleCustomerNotFound(CustomerNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Customer Not Found");
        problemDetail.setDetail("Customer with ID " + ex.getCustomerId() + " does not exist.");
        problemDetail.setType(URI.create("https://example.com/problems/customer-not-found"));
        return problemDetail;
    }

    @ExceptionHandler(RuntimeException.class)
    public ProblemDetail handleRuntimeException(RuntimeException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setTitle("Internal Server Error");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setType(URI.create("https://example.com/problems/internal-error"));
        return problemDetail;
    }*/

    /*@ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(
            IllegalArgumentException ex) {

        return ResponseEntity.badRequest()
                .body(Map.of("error", ex.getMessage()));
    }*/


}


