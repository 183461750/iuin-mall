package com.iuin.component.base.config;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.HibernateValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * validate参数校验默认的是一个参数校验失败后，还会继续校验后面的参数
 * 通过这个配置改成：校验参数时只要出现校验失败的情况，就立即抛出对应的异常，结束校验，不再进行后续的校验
 *
 * @author fa
 */
@RequiredArgsConstructor
public class ValidationConfig {

    private final Validator validator;

    @Bean
    @ConditionalOnMissingBean(Validator.class)
    public Validator validator() {
        //failFast的意思只要出现校验失败的情况，就立即结束校验，不再进行后续的校验。
        try (ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure().failFast(true).buildValidatorFactory()) {
            return validatorFactory.getValidator();
        }
    }

    @Bean
    @ConditionalOnMissingBean(MethodValidationPostProcessor.class)
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        MethodValidationPostProcessor methodValidationPostProcessor = new MethodValidationPostProcessor();
        methodValidationPostProcessor.setValidator(validator);
        return methodValidationPostProcessor;
    }

}
