package cn.zefre.common.conversion.annotation;

import cn.zefre.common.conversion.jackson.IntegerCurrencyDeserializer;
import cn.zefre.common.conversion.jackson.IntegerCurrencySerializer;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注明一个{@link Integer}的字段或参数是用于表示货币的，以分为单位
 *
 * @author pujian
 * @date 2023/1/29 18:46
 */
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = IntegerCurrencySerializer.class)
@JsonDeserialize(using = IntegerCurrencyDeserializer.class)
public @interface IntegerCurrency {

}
