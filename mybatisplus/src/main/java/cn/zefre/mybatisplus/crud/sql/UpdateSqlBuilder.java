package cn.zefre.mybatisplus.crud.sql;

import cn.zefre.mybatisplus.crud.mapper.GenericMapper;
import cn.zefre.mybatisplus.crud.where.Where;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * UPDATE语句建造者
 *
 * @author pujian
 * @date 2022/6/21 15:46
 */
public final class UpdateSqlBuilder extends SqlBuilder {

    /**
     * 构造函数
     *
     * @param tableName 表名
     * @param fields    SET子句字段列表
     * @param where     WHERE条件
     * @author pujian
     * @date 2022/6/24 11:13
     */
    public UpdateSqlBuilder(String tableName, List<String> fields, Where where) {
        super(tableName, fields, where);
        if (CollectionUtils.isEmpty(fields)) {
            throw new IllegalArgumentException("SET子句字段列表不能为空");
        }
    }

    private static final String CONNECTION = "=#{" + GenericMapper.SET_PARAM_MAP + ".";

    @Override
    public String build() {
        SQL sql = new SQL().UPDATE(tableName);
        for (String field : fields) {
            String set = field + CONNECTION + field + "}";
            sql.SET(set);
        }
        if (where != null) {
            sql.WHERE(where.getAsString());
        }
        return sql.toString();
    }
}
