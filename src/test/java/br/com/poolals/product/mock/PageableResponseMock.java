package br.com.poolals.product.mock;

import br.com.poolals.product.NextPage;
import br.com.poolals.product.PageableResponse;
import br.com.poolals.product.PaginationDetails;
import br.com.poolals.product.PreviousPage;

import java.util.Collections;

@SuppressWarnings("rawtypes")
public class PageableResponseMock {

    public static PageableResponse validPageableReponse() {
        NextPage nextPage = NextPage.builder()
                .pageNumber(null)
                .hasNext(false)
                .build();

        PreviousPage previousPage = PreviousPage.builder()
                .pageNumber(null)
                .hasPrevious(false)
                .build();

        PaginationDetails paginationDetails = PaginationDetails.builder()
                .pageNumber(0)
                .pageCount(10)
                .totalCount(10L)
                .nextPage(nextPage)
                .previousPage(previousPage)
                .isFirstPage(true)
                .isLastPage(true)
                .build();

        return PageableResponse.builder()
                .data(Collections.emptyList())
                .paginationDetails(paginationDetails)
                .build();
    }

}
