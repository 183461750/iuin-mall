package com.iuin.ssoserver.api.model.req;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * @author fa
 */
@Data
public class StudentReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "学生名称不能为null")
    private String name;

}
