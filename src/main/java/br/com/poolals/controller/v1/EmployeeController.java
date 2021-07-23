package br.com.poolals.controller.v1;

import br.com.poolals.exception.ResourceNotFoundException;
import br.com.poolals.model.request.EmployeeRequest;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/v1")
@Api(value = "Employee Management System")
@RequiredArgsConstructor
public class EmployeeController {

    private static final String URI_EMPLOYEES = "/employees";
    private static final String URI_EMPLOYEES_WITH_ID = URI_EMPLOYEES + "/{id}";

    private final EmployeeService employeeService;

    @ApiOperation(value = "See a paged list of available employees", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 404, message = "No employees were found")
    })
    @GetMapping(value = URI_EMPLOYEES)
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees(
            @ApiParam(value = "Page number", example = "0") @RequestParam(defaultValue = "0") Integer pageNo,
            @ApiParam(value = "Page size", example = "20") @RequestParam(defaultValue = "10") Integer pageSize,
            @ApiParam(value = "Sort by") @RequestParam(defaultValue = "id") String sortBy
    ) {
        List<EmployeeResponse> list = employeeService.getAllEmployees(pageNo, pageSize, sortBy);
        return status(HttpStatus.OK).body(list);
    }

    @ApiOperation(value = "Get an employee by Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 404, message = "Employee not found")
    })
    @GetMapping(value = URI_EMPLOYEES_WITH_ID)
    public ResponseEntity<EmployeeResponse> getEmployeeById(
            @ApiParam(value = "Employee id from which employee object will retrieve", required = true)
            @PathVariable(value = "id") Long employeeId) throws ResourceNotFoundException {
        EmployeeResponse employee = employeeService.findById(employeeId);
        return status(HttpStatus.OK).body(employee);
    }

    @ApiOperation(value = "Add an employee")
    @PostMapping(value = URI_EMPLOYEES)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Employee successfully saved"),
            @ApiResponse(code = 400, message = "Invalid request data"),
            @ApiResponse(code = 422, message = "Fields validation error")
    })
    public ResponseEntity<EmployeeResponse> createEmployee(
            @ApiParam(value = "Employee object store in database table", required = true)
            @Valid @RequestBody EmployeeRequest request) throws URISyntaxException {
        EmployeeResponse employeeResponse = employeeService.save(request);
        return ResponseEntity.created(new URI(URI_EMPLOYEES)).body(employeeResponse);
    }

    @ApiOperation(value = "Update an employee")
    @PutMapping(value = URI_EMPLOYEES_WITH_ID)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee successfully updated"),
            @ApiResponse(code = 400, message = "Invalid request data"),
            @ApiResponse(code = 404, message = "Employee not found"),
            @ApiResponse(code = 422, message = "Fields validation error")
    })
    public ResponseEntity<EmployeeResponse> updateEmployee(
            @ApiParam(value = "Employee Id to update employee object", required = true)
            @PathVariable(value = "id") Long employeeId,
            @ApiParam(value = "Update employee object", required = true)
            @Valid
            @RequestBody EmployeeRequest request) throws ResourceNotFoundException {
        EmployeeResponse employeeResponse = employeeService.update(employeeId, request);
        return status(HttpStatus.OK).body(employeeResponse);
    }

    @ApiOperation(value = "Delete an employee")
    @DeleteMapping(value = URI_EMPLOYEES_WITH_ID)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Employee successfully deleted"),
            @ApiResponse(code = 404, message = "Employee not found")
    })
    public ResponseEntity<EmployeeResponse> deleteEmployee(
            @ApiParam(value = "Employee Id from which employee object will delete from database table", required = true)
            @PathVariable(value = "id") Long employeeId) throws ResourceNotFoundException {
        employeeService.delete(employeeId);
        return ResponseEntity.noContent().build();
    }

}
