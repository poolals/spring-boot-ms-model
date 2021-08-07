package br.com.poolals.product;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageableRequest {

    @Min(value = 0, message = "Page index must not be less than zero!")
    @ApiParam(value = "page index", defaultValue = "0")
    private int page;

    @Min(value = 1, message = "Page size must not be less than one!")
    @ApiParam(value = "page size", defaultValue = "20")
    private int size;

}
