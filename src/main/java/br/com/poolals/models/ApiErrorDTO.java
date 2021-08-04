package br.com.poolals.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorDTO {

    private Date timestamp;
    private Integer status;
    private String code;
    private Set<ErrorDTO> errors;

}
