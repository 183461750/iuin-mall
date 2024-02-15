package com.iuin.component.pluggable_annotation.take_time.agent;// MyAgent.java

import com.iuin.component.pluggable_annotation.take_time.agent.test2.MyClassFileTransformer;

import java.lang.instrument.Instrumentation;

public class Agent {
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("你好👋premain");
        inst.addTransformer(new TimeTrackingTransformer());
    }
}
