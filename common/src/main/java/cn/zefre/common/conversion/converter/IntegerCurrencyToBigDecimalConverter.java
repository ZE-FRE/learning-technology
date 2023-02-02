package cn.zefre.common.conversion.converter;

import cn.zefre.common.conversion.ConversionConstant;
import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;

/**
 * @author pujian
 * @date 2023/2/1 17:45
 */
public class IntegerCurrencyToBigDecimalConverter implements Converter<Integer, BigDecimal> {

    @Override
    public BigDecimal convert(Integer money) {
        // 除以100，四舍五入保留两位小数
        return new BigDecimal(money).divide(ConversionConstant.ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP);
    }

}
