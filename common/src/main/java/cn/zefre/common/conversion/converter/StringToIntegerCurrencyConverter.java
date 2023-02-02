package cn.zefre.common.conversion.converter;

import cn.zefre.common.conversion.annotation.IntegerCurrency;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;

/**
 * 将用{@link String}表示的货币金额转换为用{@link Integer}来表示，以分为单位
 * 只对用{@link IntegerCurrency}注解标注的{@link Integer}字段或参数进行转换
 *
 * @author pujian
 * @date 2023/1/29 17:37
 */
public class StringToIntegerCurrencyConverter implements ConditionalGenericConverter {

    private BigDecimalToIntegerCurrencyConverter delegate = new BigDecimalToIntegerCurrencyConverter();

    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return sourceType.getType() == String.class && targetType.hasAnnotation(IntegerCurrency.class);
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(String.class, Integer.class));
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        return delegate.convert(new BigDecimal(source.toString()));
    }

}
