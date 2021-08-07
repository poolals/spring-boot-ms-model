package br.com.poolals.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PreviousPage {

    private Integer pageNumber;
    private boolean hasPrevious;

    @SuppressWarnings("rawtypes")
    public PreviousPage(Page page) {
        this.hasPrevious = page.hasPrevious();
        if (page.hasPrevious()) {
            Pageable previousPage = page.previousPageable();
            this.pageNumber = previousPage.getPageNumber();
        }
    }

}
