package cn.zefre.common.conversion.jackson;

import cn.zefre.common.conversion.converter.BigDecimalToIntegerCurrencyConverter;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
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

    private BigDecimalToIntegerCurrencyConverter delegate = new BigDecimalToIntegerCurrencyConverter();

    @Override
    public Integer deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        BigDecimal money;
        JsonToken currentToken = jsonParser.currentToken();
        if (currentToken == JsonToken.VALUE_NUMBER_INT) {
            money = new BigDecimal(jsonParser.getIntValue());
        } else if (currentToken == JsonToken.VALUE_NUMBER_FLOAT) {
            money = jsonParser.getDecimalValue();
        } else if (currentToken == JsonToken.VALUE_STRING) {
            // 如果是字符串，尝试转换为BigDecimal
            money = new BigDecimal(jsonParser.getText());
        } else {
            throw new JsonParseException(jsonParser, "无法将" + currentToken + "类型的值反序列化到" + BigDecimal.class);
        }
        return delegate.convert(money);
    }

}
