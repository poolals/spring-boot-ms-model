package br.com.poolals.employee.mock;


import br.com.poolals.employee.Employee;

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
