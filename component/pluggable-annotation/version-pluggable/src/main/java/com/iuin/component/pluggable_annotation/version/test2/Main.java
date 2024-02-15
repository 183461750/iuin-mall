package com.iuin.component.pluggable_annotation.version.test2;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

/**
 * @author fa
 */
public class Main {

    public static void main(String[] args) {
//        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
//        String[] ops = {"-processor", "com.lang.tool.CheckGetterProcessor",
//                "src/main/test/com/iuin/component/pluggable_annotation/version/test2/Foo.java"};
//        int compilationResult = compiler.run(null, null, null, ops);
//// javac -cp /CLASSPATH/TO/CheckGetterProcessor -processor bar.CheckGetterProcessor Foo.java

        Foo foo = new Foo();

        System.out.println(foo);

    }

}
