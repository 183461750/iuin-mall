package com.iuin.ssoserver.api.model.req;

import lombok.Data;

import javax.validation.constraints.NotNull;
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
