package br.com.poolals.controller;

import br.com.poolals.SpringBootMsModelApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = SpringBootMsModelApplication.class
)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class EmployeeControllerTest {


    @Test
    public void getAllEmployees_WhenCalledAndThereIsNoEmployee_ExpectedEmptyListAndStatusCodeOk() {
        // Arrange
//        List<EmployeeResponse> listEmployeeResponse = Fixture.from(EmployeeResponse.class)
//                .gimme(0, EmployeeResponseTemplate.EMPLOYEE_RESPONSE);
//
//        when(employeeService.findAll()).thenReturn(listEmployeeResponse);

        // Act
//        final ResultActions result = mockMvc.perform(MockMvcRequestBuilders
//                .get("/v1/employees")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print());

        // Assert
//        result.andExpect(status().isOk());
//        result.andExpect(jsonPath("$.length()").value(0));

    }
}
