package br.com.poolals.employee.mock;

import br.com.poolals.enums.SortDirection;
import br.com.poolals.enums.SortField;
import br.com.poolals.employee.EmployeeRequestPageable;

public class EmployeeRequestPageableMock {

    private EmployeeRequestPageableMock() {
    }

    public static EmployeeRequestPageable validEmployeeRequestPageable() {
        return EmployeeRequestPageable.employeeRequestPageableBuilder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@email.com")
                .page(0)
                .size(20)
                .sortField(SortField.FIRSTNAME)
                .sortDirection(SortDirection.ASCENDING)
                .build();
    }

}
