package com.iuin.component.pluggable_annotation.take_time.agent.test2;// MyAgent.java

import java.lang.instrument.Instrumentation;

public class MyAgent {
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("你好👋premain");
        inst.addTransformer(new MyClassFileTransformer());
    }
}
