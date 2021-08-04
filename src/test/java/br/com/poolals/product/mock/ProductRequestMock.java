package br.com.poolals.product.mock;

import br.com.poolals.product.ProductRequest;

public class ProductRequestMock {

    public static ProductRequest validRequest() {
        return ProductRequest.builder()
                .productNumber(1L)
                .name("Celular")
                .build();
    }

}
