package com.iuin.ssoserver.maintest.test4;

import cn.hutool.core.annotation.AnnotationUtil;
import com.iuin.ssoserver.SsoServerServiceApplication;
import lombok.Data;
import org.springframework.context.annotation.Import;

import java.util.Arrays;

/**
 * @author fa
 */
@Data
public class TestMain {

    public static void main(String[] args) {
        Import[] tests = AnnotationUtil.getCombinationAnnotations(SsoServerServiceApplication.class, Import.class);
        System.out.println(Arrays.toString(tests));
    }

}
