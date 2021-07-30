package br.com.poolals.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class EmployeeAlreadyExistException extends RuntimeException {

    public EmployeeAlreadyExistException(String message) {
        super(message);
    }

}
