package br.com.poolals.employee;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value = "EmployeeResponse")
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {

    @ApiModelProperty(name = "id", notes = "Employee Identifier")
    private Long id;

    @ApiModelProperty(name = "firstName", notes = "Employee First Name")
    private String firstName;

    @ApiModelProperty(name = "lastName", notes = "Employee Last Name")
    private String lastName;

    @ApiModelProperty(name = "email", notes = "Employee email")
    private String email;

}
