package cn.zefre.mybatisplus.crud.sql;

import cn.zefre.mybatisplus.crud.where.Where;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * SELECT语句建造者
 *
 * @author pujian
 * @date 2022/6/21 15:41
 */
public final class SelectSqlBuilder extends SqlBuilder {

    public SelectSqlBuilder(String tableName, List<String> fields, Where where) {
        super(tableName, fields, where);
        if (CollectionUtils.isEmpty(fields)) {
            throw new IllegalArgumentException("查询字段列表不能为空");
        }
    }

    @Override
    public String build() {
        SQL sql = new SQL()
                .SELECT(String.join(",", fields))
                .FROM(tableName);
        if (where != null) {
            sql.WHERE(where.getAsString());
        }
        return sql.toString();
    }
}
