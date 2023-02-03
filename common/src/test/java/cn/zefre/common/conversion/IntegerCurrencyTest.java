package cn.zefre.common.conversion;

import cn.zefre.common.conversion.annotation.IntegerCurrency;
import cn.zefre.common.conversion.converter.StringToIntegerCurrencyConverter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author pujian
 * @date 2023/1/29 17:51
 */
@WebMvcTest(controllers = IntegerCurrencyTest.Config.TestController.class)
public class IntegerCurrencyTest {

    @Resource
    private MockMvc mockMvc;


    @Configuration
    static class Config {

        @RestController
        static class TestController {
            @PostMapping("testRequestParam")
            public List<Integer> testRequestParam(@RequestParam @IntegerCurrency Integer money, @RequestParam Integer commonInt) {
                return Arrays.asList(money, commonInt);
            }

            @PostMapping("testRequestBody")
            public Integer testRequestBody(@RequestBody MoneyWrapper moneyWrapper) {
                return moneyWrapper.getMoney();
            }
        }

        @TestConfiguration
        static class CustomConverterWebConfiguration implements WebMvcConfigurer {
            @Override
            public void addFormatters(FormatterRegistry registry) {
                // 移除spring注册的String -> Number转换器，因为它的优先级较高，不移除无法匹配到咱们自定义的转换器
                // 移除后不用担心String -> Number的转换问题
                // 因为TypeConverterDelegate里面还有兜底的默认PropertyEditor，用于String -> Number的转换
                registry.removeConvertible(String.class, Number.class);
                // 添加咱们自定义的String -> Integer转换器
                registry.addConverter(new StringToIntegerCurrencyConverter());
            }
        }
    }


    @Test
    public void testRequestParam() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/testRequestParam");
        requestBuilder.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        requestBuilder.queryParam("money", "10.24");
        requestBuilder.queryParam("commonInt", "1024");
        String response = mockMvc.perform(requestBuilder)
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<Integer> integers = new ObjectMapper().readValue(response, new TypeReference<List<Integer>>() { });
        assertEquals(new Integer(1024), integers.get(0));
        assertEquals(new Integer(1024), integers.get(1));
    }

    @Test
    public void testRequestBody() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/testRequestBody");
        requestBuilder.contentType(MediaType.APPLICATION_JSON);
        requestBuilder.content("{\"money\": 10.24}");
        String money = mockMvc.perform(requestBuilder)
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertEquals(new Integer(1024), Integer.valueOf(money));
    }

}
