package br.com.poolals.service;

import br.com.poolals.entity.Employee;
import br.com.poolals.enums.SortDirection;
import br.com.poolals.enums.SortField;
import br.com.poolals.exception.EmployeeAlreadyExistException;
import br.com.poolals.exception.EmployeeNotFoundException;
import br.com.poolals.model.request.EmployeeRequest;
import br.com.poolals.model.request.EmployeeRequestPageable;
import br.com.poolals.model.response.EmployeeResponse;
import br.com.poolals.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public Page<EmployeeResponse> getAllEmployees(EmployeeRequestPageable requestPageable) {
        Pageable pagingPageable = getPageable(requestPageable);

        Page<Employee> pageResult = employeeRepository.findAll(pagingPageable);
        List<EmployeeResponse> responseList = new ArrayList<>();
        pageResult.forEach(employee ->
                responseList.add(modelMapper.map(employee, EmployeeResponse.class)));

        return new PageImpl<>(responseList, pagingPageable, responseList.size());
    }

    private Pageable getPageable(EmployeeRequestPageable requestPageable) {
        if (requestPageable.getSortField() == null)
            requestPageable.setSortField(SortField.FIRSTNAME);

        if (requestPageable.getSortDirection() == null)
            requestPageable.setSortDirection(SortDirection.ASCENDING);

        Sort sortBy = Sort.by(
                Sort.Direction.valueOf(requestPageable.getSortDirection().getValue()),
                requestPageable.getSortField().getValue());

        return PageRequest.of(requestPageable.getPage(), requestPageable.getSize(), sortBy);
    }

    public EmployeeResponse findById(Long employeeId) throws EmployeeNotFoundException {
        Employee employee = getEmployee(employeeId);

        return modelMapper.map(employee, EmployeeResponse.class);
    }

    public EmployeeResponse save(EmployeeRequest request) {
        employeeRepository.findByEmail(request.getEmail())
                .ifPresent(employee -> {
                    throw new EmployeeAlreadyExistException("Employee already exist with email: " + request.getEmail());
                });

        Employee employee = modelMapper.map(request, Employee.class);

        return modelMapper.map(employeeRepository.save(employee), EmployeeResponse.class);
    }

    public EmployeeResponse update(Long employeeId, EmployeeRequest request) throws EmployeeAlreadyExistException {
        Employee employee = getEmployee(employeeId);

        BeanUtils.copyProperties(request, employee);

        Employee employeeSaved = employeeRepository.save(employee);

        return modelMapper.map(employeeSaved, EmployeeResponse.class);
    }

    public void delete(Long employeeId) throws EmployeeNotFoundException {
        Employee employee = getEmployee(employeeId);

        employeeRepository.delete(employee);
    }

    private Employee getEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found for this id :: " + employeeId));
    }

}
