package com.iuin.component.base.holders;

import lombok.RequiredArgsConstructor;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author fa
 */
@Component
@RequiredArgsConstructor
public class EnvHolder implements EnvironmentAware {

    private static Environment env;

    @Override
    public void setEnvironment(Environment environment) {
        EnvHolder.env = environment;
    }

    public static String getAppName() {
        return env.getProperty("spring.application.name", "Unknown");
    }

}
