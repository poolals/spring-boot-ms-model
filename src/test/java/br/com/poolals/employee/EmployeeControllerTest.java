package br.com.poolals.employee;

import br.com.poolals.SpringBootMsModelApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = SpringBootMsModelApplication.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected EmployeeService employeeService;

    @Test
    public void deleteEmployee_WhenPassedValidEmployeeId_ExpectedStatusNoContent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/employees/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(employeeService).delete(anyLong());
    }

    @Test
    public void deleteEmployee_WhenPassedInvalidEmployeeId_ExpectedStatusNoContent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/employees/a")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(employeeService, times(0)).delete(anyLong());
    }

    @Test
    public void deleteEmployee_WhenPassedValidEmployeeIdAndEmployeeNotFound_ExpectedStatusNotFound() throws Exception {
        doThrow(EmployeeNotFoundException.class).when(employeeService).delete(anyLong());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/employees/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(employeeService).delete(anyLong());
    }

}