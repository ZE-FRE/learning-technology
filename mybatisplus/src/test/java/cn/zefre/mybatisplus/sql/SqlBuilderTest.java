package cn.zefre.mybatisplus.sql;

import cn.zefre.mybatisplus.crud.ConjunctionEnum;
import cn.zefre.mybatisplus.crud.Expression;
import cn.zefre.mybatisplus.crud.SqlOperator;
import cn.zefre.mybatisplus.crud.sql.DeleteSqlBuilder;
import cn.zefre.mybatisplus.crud.sql.InsertSqlBuilder;
import cn.zefre.mybatisplus.crud.sql.SelectSqlBuilder;
import cn.zefre.mybatisplus.crud.sql.UpdateSqlBuilder;
import cn.zefre.mybatisplus.crud.where.AtomicWhere;
import cn.zefre.mybatisplus.crud.where.CompositeWhere;
import cn.zefre.mybatisplus.crud.where.RootWhere;
import cn.zefre.mybatisplus.crud.where.Where;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author pujian
 * @date 2022/6/21 14:40
 */
public class SqlBuilderTest {

    /**
     * 数据库字段名
     */
    private List<String> fields = Arrays.asList("data_element_id", "zh_name");

    /**
     * 数据库表名
     */
    private String tableName = "t_data_element";

    @Test
    public void testBuildInsertSql() {
        String sql = new InsertSqlBuilder(tableName, fields, 8).build();
        System.out.println(sql);
    }

    @Test
    public void testBuildUpdateSql() {
        // 构造Where条件：zh_name = '张三' OR en_name = 'zhangsan'
        Where where = new RootWhere();
        where.addChild(new AtomicWhere(null, new Expression("zh_name", SqlOperator.EQ, "张三")));
        where.addChild(new AtomicWhere(ConjunctionEnum.OR, new Expression("en_name", SqlOperator.EQ, "zhangsan")));

        String sql = new UpdateSqlBuilder(tableName, fields, where).build();
        String expectedSql = "UPDATE t_data_element\n" +
                "SET data_element_id=#{setMap.data_element_id}, zh_name=#{setMap.zh_name}\n" +
                "WHERE (zh_name = #{whereMap.zh_name} OR en_name = #{whereMap.en_name})";
        Assert.assertEquals(expectedSql, sql);
    }

    @Test
    public void testBuildDeleteSql() {
        // 构造Where条件：data_element_id >= 1
        Where where = new AtomicWhere(null, new Expression("data_element_id", SqlOperator.GE, 1));
        String sql = new DeleteSqlBuilder(tableName, where).build();
        String expectedSql = "DELETE FROM t_data_element\n" +
                "WHERE (data_element_id >= #{whereMap.data_element_id})";
        Assert.assertEquals(expectedSql, sql);
    }

    @Test
    public void testBuildSelectSql() {
        // (zh_name LIKE '%员工' OR (en_name = 'vip_price' AND field_length < 60)) AND data_element_id <= 4
        Where where = new RootWhere();
        CompositeWhere compositeWhere = new CompositeWhere(null);
        compositeWhere.addChild(new AtomicWhere(null, new Expression("zh_name", SqlOperator.ENDS_WITH, "员工")));

        CompositeWhere compositeWhere1 = new CompositeWhere(ConjunctionEnum.OR);
        compositeWhere1.addChild(new AtomicWhere(null, new Expression("en_name", SqlOperator.EQ, "vip_price")));
        compositeWhere1.addChild(new AtomicWhere(ConjunctionEnum.AND, new Expression("field_length", SqlOperator.LT, 60)));
        compositeWhere.addChild(compositeWhere1);

        where.addChild(compositeWhere);
        where.addChild(new AtomicWhere(ConjunctionEnum.AND, new Expression("data_element_id", SqlOperator.LE, 4)));

        String sql = new SelectSqlBuilder(tableName, fields, where).build();
        System.out.println(sql);
    }

}
