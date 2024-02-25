package com.iuin.component.yapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 元组
 *
 * @author whm
 * @version 2.0.0
 * @since 2023-04-13
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TupleResp<T1, T2> implements Serializable {
    private static final long serialVersionUID = 426087300195900139L;

    T1 t1;
    T2 t2;
}
