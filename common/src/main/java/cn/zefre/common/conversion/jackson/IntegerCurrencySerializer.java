package cn.zefre.common.conversion.jackson;

import cn.zefre.common.conversion.annotation.IntegerCurrency;
import cn.zefre.common.conversion.converter.IntegerCurrencyToBigDecimalConverter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * 序列化{@link IntegerCurrency}注解标注的{@link Integer}字段或参数
 *
 * @author pujian
 * @date 2023/1/30 15:44
 */
public class IntegerCurrencySerializer extends JsonSerializer<Integer> {

    private IntegerCurrencyToBigDecimalConverter delegate = new IntegerCurrencyToBigDecimalConverter();

    @Override
    public void serialize(Integer value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeNumber(delegate.convert(value));
    }

}
