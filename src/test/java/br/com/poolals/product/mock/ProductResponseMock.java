package br.com.poolals.product.mock;

import br.com.poolals.product.ProductResponse;

public class ProductResponseMock {

    public static ProductResponse validResponse() {
        return ProductResponse.builder()
                .id(1L)
                .productNumber(1L)
                .name("ProductName")
                .build();
    }

}
