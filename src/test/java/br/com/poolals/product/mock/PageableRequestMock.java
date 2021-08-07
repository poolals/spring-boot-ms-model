package br.com.poolals.product.mock;

import br.com.poolals.product.PageableRequest;

public class PageableRequestMock {

    public static PageableRequest validRequest() {
        return PageableRequest.builder()
                .page(0)
                .size(20)
                .build();
    }

}
