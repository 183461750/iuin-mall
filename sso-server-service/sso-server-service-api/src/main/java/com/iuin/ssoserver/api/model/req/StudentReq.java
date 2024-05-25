package com.iuin.ssoserver.api.model.req;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author fa
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(message = "学生名称不能为null")
    private String name;

}
