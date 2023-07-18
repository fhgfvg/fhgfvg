package io.common;

import javax.validation.constraints.NotNull;

/**
 * Author: ZhaoXing

 */
public class test01 {

    //    Nullable和NotNull
//    @Nullable
    public Object sellGoods(@NotNull String name, @NotNull Object o) {
        System.out.println(name + "正在销售" + o.toString());
        return null;
    }
}
