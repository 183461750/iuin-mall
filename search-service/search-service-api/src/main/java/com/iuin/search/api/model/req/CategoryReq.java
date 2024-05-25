package com.iuin.search.api.model.req;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author fa
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(message = "名称不能为null")
    private String name;

}
