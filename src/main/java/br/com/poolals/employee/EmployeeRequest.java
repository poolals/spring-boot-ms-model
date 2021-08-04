package br.com.poolals.employee;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder(builderClassName = "EmployeeRequestBuilder")
@ApiModel(value = "EmployeeRequest")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EmployeeRequest {

    @ApiModelProperty(name = "firstName", notes = "Employee First Name")
    private String firstName;

    @ApiModelProperty(name = "lastName", notes = "Employee Last Name")
    private String lastName;

    @ApiModelProperty(name = "email", notes = "Employee email")
    private String email;

}
