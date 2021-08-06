package br.com.poolals.product.mock;

import br.com.poolals.product.Product;

public class ProductMock {

    public static Product validProduct() {
        return Product.builder()
                .productNumber(1L)
                .name("Celular")
                .build();
    }

}
