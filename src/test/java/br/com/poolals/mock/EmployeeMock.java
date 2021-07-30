package br.com.poolals.mock;


import br.com.poolals.entity.Employee;

public final class EmployeeMock {

    private EmployeeMock() {
    }

    public static Employee validEmployee() {
        return Employee.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@email.com")
                .build();
    }

}
