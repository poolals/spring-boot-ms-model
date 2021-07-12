package br.com.poolals.controller;

import br.com.poolals.model.response.EmployeeResponse;
import br.com.poolals.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/v1/employees")
@Api(value = "Employee Management System")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @ApiOperation(value = "See a paged list of available employees", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list")
    })
    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees(
            @ApiParam(value = "Page number") @RequestParam(defaultValue = "0") Integer pageNo,
            @ApiParam(value = "Page size") @RequestParam(defaultValue = "10") Integer pageSize,
            @ApiParam(value = "Sort by") @RequestParam(defaultValue = "id") String sortBy
    ) {
        List<EmployeeResponse> list = employeeService.getAllEmployees(pageNo, pageSize, sortBy);
        return status(HttpStatus.OK).body(list);
    }

}
