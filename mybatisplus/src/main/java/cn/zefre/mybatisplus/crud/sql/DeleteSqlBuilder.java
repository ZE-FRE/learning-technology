package cn.zefre.mybatisplus.crud.sql;

import cn.zefre.mybatisplus.crud.where.Where;
import org.apache.ibatis.jdbc.SQL;

/**
 * DELETE语句建造者
 *
 * @author pujian
 * @date 2022/6/21 15:46
 */
public final class DeleteSqlBuilder extends SqlBuilder {

    public DeleteSqlBuilder(String tableName, Where where) {
        super(tableName, null, where);
    }

    @Override
    public String build() {
        SQL sql = new SQL().DELETE_FROM(tableName);
        if (where != null) {
            sql.WHERE(where.getAsString());
        }
        return sql.toString();
    }
}
