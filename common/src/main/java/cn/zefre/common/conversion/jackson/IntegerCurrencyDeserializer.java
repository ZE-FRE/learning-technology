package cn.zefre.common.conversion.jackson;

import cn.zefre.common.conversion.ConversionConstant;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * 将货币金额反序列化为{@link Integer}，以分为单位
 *
 * @author pujian
 * @date 2023/1/30 15:44
 */
public class IntegerCurrencyDeserializer extends JsonDeserializer<Integer> {

    @Override
    public Integer deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return new BigDecimal(p.getValueAsString()).multiply(ConversionConstant.ONE_HUNDRED).intValue();
    }

}
