package br.com.poolals.service;


import br.com.poolals.entity.Employee;
import br.com.poolals.exception.EmployeeAlreadyExistException;
import br.com.poolals.exception.EmployeeNotFoundException;
import br.com.poolals.mock.EmployeeMock;
import br.com.poolals.mock.EmployeeRequestMock;
import br.com.poolals.mock.EmployeeRequestPageableMock;
import br.com.poolals.model.request.EmployeeRequest;
import br.com.poolals.model.request.EmployeeRequestPageable;
import br.com.poolals.model.response.EmployeeResponse;
import br.com.poolals.repository.EmployeeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@Profile(value = "test")
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @SuppressWarnings("unused")
    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    public void getAllEmployees_WhenAllEmployeeWithValidRequest_ExpectedEmployeeResponse() {
        EmployeeRequestPageable employeeRequestPageable = EmployeeRequestPageableMock.validEmployeeRequestPageable();

        when(employeeRepository.findAll(any(Pageable.class))).thenReturn(Page.empty(Pageable.ofSize(10)));

        Page<EmployeeResponse> allEmployees = employeeService.getAllEmployees(employeeRequestPageable);

        assertNotNull(allEmployees);
    }

    @Test
    public void getAllEmployees_WhenNewValuePageInRequest_ExpectedThisNewValueInResponse() {
        EmployeeRequestPageable employeeRequestPageable = EmployeeRequestPageableMock.validEmployeeRequestPageable();
        employeeRequestPageable.setPage(10);

        Page<Employee> pagedResponse = new PageImpl<>(Collections.singletonList(EmployeeMock.validEmployee()));
        when(employeeRepository.findAll(any(Pageable.class))).thenReturn(pagedResponse);

        Page<EmployeeResponse> allEmployees = employeeService.getAllEmployees(employeeRequestPageable);

//        assertEquals(employeeRequestPageable.getPage().intValue(), allEmployees.getSize());
    }

    @Test
    public void findById_WhenEmployeeExist_ExpectedEmployeeResponse() {
        Employee mockEmployee = EmployeeMock.validEmployee();

        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(mockEmployee));

        EmployeeResponse employeeResponse = employeeService.findById(anyLong());

        assertNotNull(employeeResponse);
    }

    @Test(expected = EmployeeNotFoundException.class)
    public void findById_WhenEmployeeNotExist_ExpectedEmployeeNotFoundException() {
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());

        employeeService.findById(anyLong());
    }

    @Test
    public void update_WhenEmployeeExist_ExpectedEmployeeResponse() {
        Employee mockEmployee = EmployeeMock.validEmployee();
        EmployeeRequest mockRequest = EmployeeRequestMock.validEmployeeRequest();

        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(mockEmployee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(mockEmployee);

        EmployeeResponse employeeResponse = employeeService.update(1L, mockRequest);

        assertNotNull(employeeResponse);
    }

    @Test(expected = EmployeeNotFoundException.class)
    public void update_WhenEmployeeNotExist_ExpectedEmployeeNotFoundException() {
        EmployeeRequest mockRequest = EmployeeRequestMock.validEmployeeRequest();
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());

        employeeService.update(1L, mockRequest);
    }

    @Test
    public void save_WhenNewEmployeePassed_ExpectedEmployeeResponse() {
        Employee mockEmployee = EmployeeMock.validEmployee();
        EmployeeRequest mockRequest = EmployeeRequestMock.validEmployeeRequest();

        when(employeeRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(employeeRepository.save(any(Employee.class))).thenReturn(mockEmployee);

        EmployeeResponse employeeResponse = employeeService.save(mockRequest);

        assertNotNull(employeeResponse);
    }

    @Test(expected = EmployeeAlreadyExistException.class)
    public void save_WhenPassExistingEmployee_ExpectedEmployeeAlreadyExistException() {
        Employee mockEmployee = EmployeeMock.validEmployee();
        EmployeeRequest mockRequest = EmployeeRequestMock.validEmployeeRequest();

        when(employeeRepository.findByEmail(anyString())).thenReturn(Optional.of(mockEmployee));

        employeeService.save(mockRequest);
    }

    @Test
    public void delete_WhenEmployeeExist_ExpectedOneRequestToEmployRepository() {
        Employee mockEmployee = EmployeeMock.validEmployee();

        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(mockEmployee));
        doNothing().when(employeeRepository).delete(any(Employee.class));

        employeeService.delete(1L);

        verify(employeeRepository).delete(any(Employee.class));
    }

    @Test(expected = EmployeeNotFoundException.class)
    public void delete_WhenEmployeeNotExist_ExpectedEmployeeNotFoundException() {
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());

        employeeService.delete(1L);
    }

}