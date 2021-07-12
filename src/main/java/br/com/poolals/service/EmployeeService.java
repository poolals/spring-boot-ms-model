package br.com.poolals.service;

import br.com.poolals.entity.Employee;
import br.com.poolals.model.response.EmployeeResponse;
import br.com.poolals.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public List<EmployeeResponse> getAllEmployees(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Employee> pageResult = employeeRepository.findAll(paging);
        if (pageResult.hasContent()) {
            return pageResult.getContent().stream()
                    .map(entity -> modelMapper.map(entity, EmployeeResponse.class))
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

}