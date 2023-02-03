package cn.zefre.common.conversion;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author pujian
 * @date 2023/1/30 16:14
 */
@WebMvcTest(controllers = DateFormatTest.DateConfig.TestController.class)
public class DateFormatTest {

    @Resource
    private MockMvc mockMvc;

    @Configuration
    static class DateConfig {
        @RestController
        static class TestController {
            @PostMapping("testRequestParamDate")
            @JsonFormat(pattern = "yyyy~MM~dd", timezone = "GMT+8", locale = "zh")
            public Date testRequestParamDate(@DateTimeFormat(pattern = "yyyy|MM|dd") Date date) {
                return date;
            }

            @Data
            static class DateReq {
                @JsonFormat(pattern = "yyyy|MM|dd", timezone = "GMT+8", locale = "zh")
                private Date date;
            }

            @PostMapping("testRequestBodyDate")
            public DateReq testRequestBodyDate(@RequestBody DateReq dateReq) {
                return dateReq;
            }
        }
    }


    @Test
    public void testRequestParamDate() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/testRequestParamDate");
        requestBuilder.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        requestBuilder.queryParam("date", "2023|01|30");
        ResultActions actions = mockMvc.perform(requestBuilder);
        System.out.println(actions.andReturn().getResponse().getContentAsString());
    }

    @Test
    public void testRequestBodyDate() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/testRequestBodyDate");
        requestBuilder.contentType(MediaType.APPLICATION_JSON);
        requestBuilder.content("{\"date\": \"2023|01|30\"}");
        ResultActions actions = mockMvc.perform(requestBuilder);
        System.out.println("date = " + actions.andReturn().getResponse().getContentAsString());
    }

}
