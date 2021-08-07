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
public class NextPage {

    private Integer pageNumber;
    private boolean hasNext;

    @SuppressWarnings("rawtypes")
    public NextPage(Page page) {
        this.hasNext = page.hasNext();
        if (page.hasNext()) {
            Pageable nextPage = page.nextPageable();
            this.pageNumber = nextPage.getPageNumber();
        }
    }

}
