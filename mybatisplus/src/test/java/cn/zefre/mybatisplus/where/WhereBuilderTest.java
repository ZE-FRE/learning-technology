package cn.zefre.mybatisplus.where;

import cn.zefre.mybatisplus.crud.Condition;
import cn.zefre.mybatisplus.crud.where.Where;
import cn.zefre.mybatisplus.crud.where.WhereBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.io.Resources;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author pujian
 * @date 2022/6/29 14:06
 */
public class WhereBuilderTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testSingularCondition() throws JsonProcessingException {
        String json =
                "{" +
                "    \"expression\": {" +
                "        \"field\": \"zh_name\"," +
                "        \"operator\": \"EQ\"," +
                "        \"value\": \"张三\"" +
                "    }" +
                "}";
        Condition condition = objectMapper.readValue(json, Condition.class);
        Where where = WhereBuilder.build(condition);
        String expectedWhereSql = "zh_name = #{whereMap.zh_name}";
        Assert.assertNotNull(where);
        Assert.assertEquals(expectedWhereSql, where.getAsString());
    }

    @Test
    public void testPluralCondition() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("plural_condition.json");
        Condition condition = objectMapper.readValue(inputStream, Condition.class);
        Where where = WhereBuilder.build(condition);
        String expectedWhereSql = "staff_name = #{whereMap.staff_name} AND staff_id = #{whereMap.staff_id}";
        Assert.assertNotNull(where);
        Assert.assertEquals(expectedWhereSql, where.getAsString());
    }

    @Test
    public void testComplicatedCondition() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("complicated_condition.json");
        Condition condition = objectMapper.readValue(inputStream, Condition.class);
        Where where = WhereBuilder.build(condition);
        String expectedWhereSql = "(zh_name LIKE CONCAT(#{whereMap.zh_name},'%') " +
                "OR (en_name = #{whereMap.en_name} AND field_length " +
                "IN (#{whereMap.field_length[0]},#{whereMap.field_length[1]}))) " +
                "AND data_element_id = #{whereMap.data_element_id}";
        Assert.assertNotNull(where);
        Assert.assertEquals(expectedWhereSql, where.getAsString());
    }

}
