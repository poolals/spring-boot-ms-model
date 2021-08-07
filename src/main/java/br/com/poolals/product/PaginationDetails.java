package br.com.poolals.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaginationDetails {
    private Long totalCount;
    private Integer pageCount;
    private Integer pageNumber;
    private NextPage nextPage;
    private PreviousPage previousPage;
    private Boolean isFirstPage;
    private Boolean isLastPage;

    @SuppressWarnings("rawtypes")
    public PaginationDetails(Page page) {
        this.totalCount = page.getTotalElements();
        this.pageCount = page.getNumberOfElements();
        this.pageNumber = page.getNumber();
        this.isFirstPage = page.isFirst();
        this.isLastPage = page.isLast();

        this.nextPage = new NextPage(page);
        this.previousPage = new PreviousPage(page);
    }

}
