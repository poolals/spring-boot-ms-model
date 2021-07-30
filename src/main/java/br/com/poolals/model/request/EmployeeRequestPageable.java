package br.com.poolals.model.request;

import br.com.poolals.enums.SortDirection;
import br.com.poolals.enums.SortField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.PositiveOrZero;

@EqualsAndHashCode(callSuper = false)
@Data
@ApiModel(value = "EmployeeRequestPageable")
public class EmployeeRequestPageable extends EmployeeRequest {

    @Builder(builderMethodName = "employeeRequestPageableBuilder")
    public EmployeeRequestPageable(String firstName, String lastName, String email, Integer page, Integer size, SortField sortField, SortDirection sortDirection) {
        super(firstName, lastName, email);
        this.page = page;
        this.size = size;
        this.sortField = sortField;
        this.sortDirection = sortDirection;
    }

    @PositiveOrZero
    @ApiParam(value = "page index", defaultValue = "0")
    private Integer page;

    @PositiveOrZero
    @ApiParam(value = "page size", defaultValue = "20")
    private Integer size;

    @ApiParam(value = "sort field", defaultValue = "FIRSTNAME")
    private SortField sortField;

    @ApiParam(value = "sort direction", defaultValue = "ASCENDING")
    private SortDirection sortDirection;

}
