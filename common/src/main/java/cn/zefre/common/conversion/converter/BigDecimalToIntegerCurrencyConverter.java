package cn.zefre.common.conversion.converter;

import cn.zefre.common.conversion.ConversionConstant;
import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;

/**
 * @author pujian
 * @date 2023/2/1 17:52
 */
public class BigDecimalToIntegerCurrencyConverter implements Converter<BigDecimal, Integer> {

    @Override
    public Integer convert(BigDecimal money) {
        // 乘以100
        return money.multiply(ConversionConstant.ONE_HUNDRED).intValue();
    }

}
