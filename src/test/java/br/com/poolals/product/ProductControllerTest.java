package br.com.poolals.product;

import br.com.poolals.SpringBootMsModelApplication;
import br.com.poolals.product.mock.PageableRequestMock;
import br.com.poolals.product.mock.PageableResponseMock;
import br.com.poolals.product.mock.ProductRequestMock;
import br.com.poolals.product.mock.ProductResponseMock;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jayway.jsonpath.JsonPath;
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

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SuppressWarnings({"unchecked", "rawtypes"})
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
        verify(productService, times(0)).create(any(ProductRequest.class));
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
        verify(productService, times(0)).create(any(ProductRequest.class));
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
        verify(productService, times(0)).create(any(ProductRequest.class));
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
        verify(productService, times(0)).create(any(ProductRequest.class));
    }

    @Test
    public void createProduct_WhenRequestValid_ExpectedProductReponse() throws Exception {
        ProductRequest productRequest = ProductRequestMock.validRequest();
        ProductResponse productResponse = ProductResponseMock.validResponse();
        when(productService.create(productRequest)).thenReturn(productResponse);

        MvcResult mvcResult = mockMvc.perform(post(PRODUCT_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(productRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();

        Assert.assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus());
        Assert.assertTrue(Objects.requireNonNull(mvcResult.getResponse().getHeader("location")).contains(PRODUCT_BASE_URL));
        Assert.assertEquals(convertObjectToJsonString(productResponse), mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void createProduct_WhenProductAlreadyExist_ExpectedException() throws Exception {
        ProductRequest productRequest = ProductRequestMock.validRequest();
        doThrow(ProductAlreadyExistException.class).when(productService).create(productRequest);

        MvcResult mvcResult = mockMvc.perform(post(PRODUCT_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(productRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();

        Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void listProducts_WhenThereAreNoRegisteredProducts_ExpectedException() throws Exception {
        PageableRequest pageableRequest = PageableRequestMock.validRequest();
        doThrow(ProductNotFoundException.class).when(productService).list(any());

        MvcResult mvcResult = mockMvc.perform(get(PRODUCT_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", String.valueOf(pageableRequest.getPage()))
                        .param("size", String.valueOf(pageableRequest.getSize()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();

        Assert.assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
        verify(productService, times(1)).list(any());
    }

    @Test
    public void listProducts_WhenThereAreRegisteredProducts_ExpectedListOfProducts() throws Exception {
        List<ProductResponse> productResponses = Arrays.asList(ProductResponseMock.validResponse(), ProductResponseMock.validResponse());
        PageableResponse pageableResponses = PageableResponseMock.validPageableReponse();
        pageableResponses.setData(productResponses);
        PageableRequest pageableRequest = PageableRequestMock.validRequest();
        when(productService.list(any())).thenReturn(pageableResponses);

        MvcResult mvcResult = mockMvc.perform(get(PRODUCT_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", String.valueOf(pageableRequest.getPage()))
                        .param("size", String.valueOf(pageableRequest.getSize()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();

        Assert.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        int responseSizeList = JsonPath.parse(mvcResult.getResponse().getContentAsString()).read("$.data.length()");
        Assert.assertEquals(productResponses.size(), responseSizeList);
        verify(productService, times(1)).list(any());
    }

    @Test
    public void listProducts_WhenPageIsLessThanZero_ExpectedException() throws Exception {
        PageableRequest pageableRequest = PageableRequestMock.validRequest();
        pageableRequest.setPage(-1);

        MvcResult mvcResult = mockMvc.perform(get(PRODUCT_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", String.valueOf(pageableRequest.getPage()))
                        .param("size", String.valueOf(pageableRequest.getSize()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();

        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
        verify(productService, times(0)).list(any(PageableRequest.class));
    }

    @Test
    public void listProducts_WhenPageIsLessThanOne_ExpectedException() throws Exception {
        PageableRequest pageableRequest = PageableRequestMock.validRequest();
        pageableRequest.setSize(-1);

        MvcResult mvcResult = mockMvc.perform(get(PRODUCT_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", String.valueOf(pageableRequest.getPage()))
                        .param("size", String.valueOf(pageableRequest.getSize()))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();

        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
        verify(productService, times(0)).list(any(PageableRequest.class));
    }

    private String convertObjectToJsonString(Object templateOrder) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        return mapper.writeValueAsString(templateOrder);
    }

}
