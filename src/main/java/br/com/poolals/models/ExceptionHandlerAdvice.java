package br.com.poolals.models;

import br.com.poolals.product.ProductAlreadyExistException;
import br.com.poolals.product.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorDTO> handlerMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        log.error("Exception {}, Message: {}", exception.getClass().getName(), exception.getMessage());

        Set<ErrorDTO> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> buildError(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toSet());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseErrorBuilder(HttpStatus.BAD_REQUEST, errors));
    }

    @ExceptionHandler(ProductAlreadyExistException.class)
    public ResponseEntity<ApiErrorDTO> handlerMethodArgumentNotValid(ProductAlreadyExistException exception) {
        log.error("Exception {}, Message: {}", exception.getClass().getName(), exception.getMessage());

        String paramter = exception.getClass().getPackageName().substring(exception.getClass().getPackageName().lastIndexOf('.') + 1);
        ErrorDTO error = buildError(StringUtils.capitalize(paramter), exception.getMessage());
        Set<ErrorDTO> errors = Set.of(error);

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(baseErrorBuilder(HttpStatus.UNPROCESSABLE_ENTITY, errors));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiErrorDTO> handlerMethodArgumentNotValid(ProductNotFoundException exception) {
        log.error("Exception {}, Message: {}", exception.getClass().getName(), exception.getMessage());

        String paramter = exception.getClass().getPackageName().substring(exception.getClass().getPackageName().lastIndexOf('.') + 1);
        ErrorDTO error = buildError(StringUtils.capitalize(paramter), exception.getMessage());
        Set<ErrorDTO> errors = Set.of(error);

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(baseErrorBuilder(HttpStatus.NOT_FOUND, errors));
    }

    private ErrorDTO buildError(String parameter, String message) {
        return new ErrorDTO(parameter, message);
    }

    private ApiErrorDTO baseErrorBuilder(HttpStatus httpStatus, Set<ErrorDTO> errorList) {
        return new ApiErrorDTO(
                new Date(),
                httpStatus.value(),
                httpStatus.name(),
                errorList);
    }

}
