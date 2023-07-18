package io.common.annotation;

/**
 * Author: ZhaoXing
 */

import java.lang.annotation.*;

/**
 * 权限校验
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface AuthCheck {
    /**
     * 具有任一身份
     * @return
     */
    String[] anyRole() default "";

    /**
     * 必须为某一角色
     * @return
     */
    String mustRole() default "";
}
