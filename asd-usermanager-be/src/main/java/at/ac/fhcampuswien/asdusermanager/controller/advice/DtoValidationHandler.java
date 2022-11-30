package at.ac.fhcampuswien.asdusermanager.controller.advice;

import at.ac.fhcampuswien.asdusermanager.dto.ErrorDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class DtoValidationHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(ex.getBindingResult().getAllErrors().stream().
                map(objectError ->
                {
                    String field = ((FieldError) objectError).getField();
                    String message = field + " " + Objects.requireNonNull(objectError.getDefaultMessage());
                    return new ErrorDTO(field, message, Instant.now(), ((ServletWebRequest) request).getRequest().getRequestURI());
                })
                .collect(Collectors.toList()), HttpStatus.BAD_REQUEST);
    }
}
