package cn.zefre.common.conversion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author pujian
 * @date 2023/1/30 15:05
 */
public class IntegerCurrencyJacksonTest {

    @Test
    public void testSerialization() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String value = objectMapper.writeValueAsString(new MoneyWrapper(1024));
        System.out.println(value);
    }

    @Test
    public void testDeserialization() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        MoneyWrapper moneyWrapper = objectMapper.readValue("{\"money\":10.24}", MoneyWrapper.class);
        assertEquals(new Integer(1024), moneyWrapper.getMoney());
    }

}
