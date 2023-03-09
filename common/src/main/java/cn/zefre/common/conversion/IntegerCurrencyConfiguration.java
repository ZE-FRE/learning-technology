package cn.zefre.common.conversion;

import cn.zefre.common.conversion.converter.StringToIntegerCurrencyConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author pujian
 * @date 2023/1/30 20:20
 */
//@Configuration
public class IntegerCurrencyConfiguration {

    /**
     * 注册String -> Integer的转换器
     *
     * @return org.springframework.web.servlet.config.annotation.WebMvcConfigurer
     * @author pujian
     * @date 2023/1/30 20:29
     */
    @Bean
    public WebMvcConfigurer integerCurrencyConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addFormatters(FormatterRegistry registry) {
                // 移除spring注册的String -> Number转换器，因为它的优先级较高，不移除无法匹配到咱们自定义的转换器
                // 移除后不用担心String -> Number的转换问题
                // 因为TypeConverterDelegate里面还有兜底的默认PropertyEditor，用于String -> Number的转换
                registry.removeConvertible(String.class, Number.class);
                // 添加咱们自定义的String -> Integer转换器
                registry.addConverter(new StringToIntegerCurrencyConverter());
            }
        };
    }

}
