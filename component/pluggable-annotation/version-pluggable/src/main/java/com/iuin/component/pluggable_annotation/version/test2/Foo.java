package com.iuin.component.pluggable_annotation.version.test2;

import com.iuin.component.pluggable_annotation.version.TrisceliVersion;
import lombok.ToString;

@ToString
@CheckGetter
class Foo {

    @CheckGetter
    int a;
    @CheckGetter
    static int b;

    @TrisceliVersion
    static int version;

    Foo() {
    }

    void setA(int newA) {
    }

}
