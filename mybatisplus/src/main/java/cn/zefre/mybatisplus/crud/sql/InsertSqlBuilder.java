package cn.zefre.mybatisplus.crud.sql;

import cn.zefre.mybatisplus.crud.mapper.GenericMapper;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * INSERT语句建造者
 * 可以构造插入单条数据和多条数据的语句
 *
 * @author pujian
 * @date 2022/6/21 15:44
 */
public final class InsertSqlBuilder extends SqlBuilder {

    /**
     * INSERT语句VALUES预处理集
     */
    private final Queue<String[]> preparedValues = new LinkedList<>();

    /**
     * 构造函数
     *
     * @param tableName   表名
     * @param fields      插入的字段列表
     * @param insertCount 插入条数
     * @author pujian
     * @date 2022/6/24 10:23
     */
    public InsertSqlBuilder(String tableName, List<String> fields, int insertCount) {
        super(tableName, fields, null);
        if (CollectionUtils.isEmpty(fields)) {
            throw new IllegalArgumentException("插入字段列表不能为空");
        }
        prepareValues(fields, insertCount);
    }

    /**
     * 预处理VALUES
     *
     * @param fields      插入的字段列表
     * @param insertCount 插入条数
     * @author pujian
     * @date 2022/6/24 10:19
     */
    private void prepareValues(List<String> fields, int insertCount) {
        for (int i = 0; i < insertCount; i++) {
            List<String> row = new ArrayList<>(fields.size());
            for (String field : fields) {
                row.add("#{" + GenericMapper.VALUES_PARAM + "[" + i + "]." + field + "}");
            }
            preparedValues.offer(row.toArray(new String[0]));
        }
    }

    @Override
    public String build() {
        SQL sql = new SQL().INSERT_INTO(tableName)
                .INTO_COLUMNS(fields.toArray(new String[0]))
                .INTO_VALUES(preparedValues.remove());
        // 插入多条数据
        if (!preparedValues.isEmpty()) {
            String[] value;
            while ((value = preparedValues.poll()) != null) {
                sql.ADD_ROW();
                sql.INTO_VALUES(value);
            }
        }
        return sql.toString();
    }
}
