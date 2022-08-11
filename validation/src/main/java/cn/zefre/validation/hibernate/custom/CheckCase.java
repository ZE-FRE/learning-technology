package cn.zefre.validation.hibernate.custom;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;


/**
 * 自定义约束
 * 验证字符串大小写
 *
 * @author pujian
 * @date 2021/10/19 10:26
 */
@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
// 将CheckCase注解标记为约束注解，指定对应的validator类(可以指定多个，如果有需要的话)
@Constraint(validatedBy = CheckCaseValidator.class)
@Documented
@Repeatable(CheckCase.List.class)
public @interface CheckCase {
    String message() default "CheckCase validation error";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    CaseMode value();

    @Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        CheckCase[] value();
    }
}
