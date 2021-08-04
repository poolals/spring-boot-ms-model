package br.com.poolals.product;

import br.com.poolals.SpringBootMsModelApplication;
import br.com.poolals.product.mock.ProductRequestMock;
import br.com.poolals.product.mock.ProductResponseMock;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Objects;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = SpringBootMsModelApplication.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ProductControllerTest {

    public static final String PRODUCT_BASE_URL = "/v1/products";

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected ProductService productService;

    @Test
    public void createProduct_WhenRequestWithProductNumberIsNull_ExpectedBadRequest() throws Exception {
        // Arrange
        ProductRequest productRequest = ProductRequestMock.validRequest();
        productRequest.setProductNumber(null);

        // Act
        MvcResult mvcResult = mockMvc.perform(post(PRODUCT_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(productRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();

        // Assert
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void createProduct_WhenRequestWithNameIsNull_ExpectedBadRequest() throws Exception {
        ProductRequest productRequest = ProductRequestMock.validRequest();
        productRequest.setName(null);

        MvcResult mvcResult = mockMvc.perform(post(PRODUCT_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(productRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();

        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void createProduct_WhenRequestWithNameIsNullBlank_ExpectedBadRequest() throws Exception {
        ProductRequest productRequest = ProductRequestMock.validRequest();
        productRequest.setName("");

        MvcResult mvcResult = mockMvc.perform(post(PRODUCT_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(productRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();

        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void createProduct_WhenRequestWithNameOnlySpace_ExpectedBadRequest() throws Exception {
        ProductRequest productRequest = ProductRequestMock.validRequest();
        productRequest.setName(" ");

        MvcResult mvcResult = mockMvc.perform(post(PRODUCT_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(productRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();

        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void createProduct_WhenRequestValid_ExpectedProductReponse() throws Exception {
        ProductRequest productRequest = ProductRequestMock.validRequest();
        when(productService.create(productRequest)).thenReturn(ProductResponseMock.validResponse());

        MvcResult mvcResult = mockMvc.perform(post(PRODUCT_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(productRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();

        Assert.assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus());
        Assert.assertTrue(Objects.requireNonNull(mvcResult.getResponse().getHeader("location"))
                .contains(PRODUCT_BASE_URL));
    }

    @Test
    public void createProduct_WhenProductAlreadyExist_ExpectedException() throws Exception {
        ProductRequest productRequest = ProductRequestMock.validRequest();
        doThrow(AlreadyExistException.class).when(productService).create(productRequest);

        MvcResult mvcResult = mockMvc.perform(post(PRODUCT_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(productRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();

        Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), mvcResult.getResponse().getStatus());
    }

    private String convertObjectToJsonString(Object templateOrder) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(templateOrder);
    }

}
