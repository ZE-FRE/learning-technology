package cn.zefre.mybatisplus.crud.sql;

import cn.zefre.mybatisplus.crud.where.Where;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * sql语句建造者
 *
 * @author pujian
 * @date 2022/6/21 14:17
 */
public abstract class SqlBuilder {
    /**
     * 表名
     */
    protected final String tableName;
    /**
     * 字段列表
     */
    protected final List<String> fields;
    /**
     * WHERE条件
     */
    protected final Where where;


    protected SqlBuilder(String tableName, List<String> fields, Where where) {
        if (StringUtils.isEmpty(tableName)) {
            throw new IllegalArgumentException("tableName不能为空");
        }
        this.tableName = tableName;
        this.fields = fields;
        this.where = where;
    }

    /**
     * 构造完整的预处理sql语句
     *
     * @return java.lang.String
     * @author pujian
     * @date 2022/6/21 15:41
     */
    public abstract String build();

}
