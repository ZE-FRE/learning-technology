package cn.zefre.common.conversion.jackson;

import cn.zefre.common.conversion.ConversionConstant;
import cn.zefre.common.conversion.annotation.IntegerCurrency;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * 序列化{@link IntegerCurrency}注解标注的{@link Integer}字段或参数
 *
 * @author pujian
 * @date 2023/1/30 15:44
 */
public class IntegerCurrencySerializer extends JsonSerializer<Integer> {

    @Override
    public void serialize(Integer value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        // 除以100，四舍五入保留两位小数
        BigDecimal money = new BigDecimal(value).divide(ConversionConstant.ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP);
        gen.writeNumber(money);
    }

}
