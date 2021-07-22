package br.com.poolals.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value = "EmployeeRequest")
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {

    @ApiModelProperty(name = "firstName", notes = "Employee First Name")
    private String firstName;

    @ApiModelProperty(name = "lastName", notes = "Employee Last Name")
    private String lastName;

    @ApiModelProperty(name = "email", notes = "Employee email")
    private String email;

}
