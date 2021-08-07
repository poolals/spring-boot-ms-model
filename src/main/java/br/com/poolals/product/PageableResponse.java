package br.com.poolals.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageableResponse<T> {

    private List<T> data;
    private PaginationDetails paginationDetails;

    @SuppressWarnings({"unchecked", "rawtypes"})
    public PageableResponse(Page page) {
        this.data = page.getContent();
        paginationDetails = new PaginationDetails(page);
    }

}
