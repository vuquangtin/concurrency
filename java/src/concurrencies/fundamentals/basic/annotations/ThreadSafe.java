package basic.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 课程里用来标记【线程安全】的类或者写法
 */

/**
 * 注解标示的对象
 */
@Target(ElementType.TYPE)
/**
 * 注解的运行范围
 */
@Retention(RetentionPolicy.SOURCE)
public @interface ThreadSafe {

    String value() default "";
}
