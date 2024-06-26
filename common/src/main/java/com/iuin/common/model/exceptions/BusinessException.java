package com.iuin.common.model.exceptions;


import com.iuin.common.enums.ResponseCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义业务异常类，用于全局异常处理
 *
 * @author 万宁
 * @version 2.0.0
 * @since 2020-05-30
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException {

    public BusinessException(ResponseCodeEnum code) {
        super(ResponseCodeEnum.getByCode(code.getCode()).getMessage());
        this.code = code;
        this.errorCode = code.getCode();
        this.msg = super.getMessage();
    }

    public BusinessException(ResponseCodeEnum code, String msg) {
        super(msg);
        this.code = code;
        this.errorCode = code.getCode();
        this.msg = msg;
    }

    public BusinessException(String msg) {
        super(msg);
        this.code = ResponseCodeEnum.BUSINESS_ERROR;
        this.errorCode = code.getCode();
        this.msg = msg;
    }

    public BusinessException(int errorCode, String msg) {
        super(msg);
        this.code = ResponseCodeEnum.getByCode(errorCode);
        this.errorCode = code.getCode();
        this.msg = msg;
    }

    private ResponseCodeEnum code;
    private int errorCode;
    private String msg;
}
