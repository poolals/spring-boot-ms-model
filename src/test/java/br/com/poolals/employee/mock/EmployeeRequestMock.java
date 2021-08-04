package br.com.poolals.employee.mock;

import br.com.poolals.employee.EmployeeRequest;

public final class EmployeeRequestMock {

    private EmployeeRequestMock() {
    }

    public static EmployeeRequest validEmployeeRequest() {
        return EmployeeRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@email.com")
                .build();
    }

}
