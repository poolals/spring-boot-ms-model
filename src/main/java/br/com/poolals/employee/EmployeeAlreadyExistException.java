package br.com.poolals.employee;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class EmployeeAlreadyExistException extends RuntimeException {

    public EmployeeAlreadyExistException(String message) {
        super(message);
    }

}
