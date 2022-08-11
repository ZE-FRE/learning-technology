package cn.zefre.mybatisplus.crud.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * 通用sql操作Mapper
 *
 * @author pujian
 * @date 2022/6/16 17:17
 */
public interface GenericMapper {

    String VALUES_PARAM = "values";

    String SET_PARAM_MAP = "setMap";

    String WHERE_PARAM_MAP = "whereMap";

    /**
     * 插入操作
     *
     * @param sql    insert语句
     * @param values 待插入数据
     * @return int 成功插入数
     * @author pujian
     * @date 2022/6/24 9:32
     */
    @Insert("${sql}")
    int insert(@Param("sql") String sql, @Param(VALUES_PARAM) List<? extends Map<String, Object>> values);

    /**
     * 修改操作
     *
     * @param sql      update语句
     * @param setMap   set语句参数值
     * @param whereMap where语句参数值
     * @return int 成功修改数
     * @author pujian
     * @date 2022/6/24 9:36
     */
    @Update("${sql}")
    int update(@Param("sql") String sql, @Param(SET_PARAM_MAP) Map<String, Object> setMap, @Param(WHERE_PARAM_MAP) Map<String, Object> whereMap);

    /**
     * 删除操作
     *
     * @param sql      delete语句
     * @param whereMap where语句参数值
     * @return int 成功删除数
     * @author pujian
     * @date 2022/6/21 13:48
     */
    @Delete("${sql}")
    int delete(@Param("sql") String sql, @Param(WHERE_PARAM_MAP) Map<String, Object> whereMap);

    /**
     * 单条查询
     *
     * @param sql      单条查询sql
     * @param whereMap where语句参数值
     * @return Map<String, Object>
     * @author pujian
     * @date 2022/6/21 13:49
     */
    @Select("${sql}")
    Map<String, Object> selectOne(@Param("sql") String sql, @Param(WHERE_PARAM_MAP) Map<String, Object> whereMap);

    /**
     * 列表查询
     *
     * @param sql      列表查询sql
     * @param whereMap where语句参数值
     * @return java.util.List
     * @author pujian
     * @date 2022/6/21 13:50
     */
    @Select("${sql}")
    List<Map<String, Object>> selectList(@Param("sql") String sql, @Param(WHERE_PARAM_MAP) Map<String, Object> whereMap);

    /**
     * 分页查询
     *
     * @param sql      分页查询sql
     * @param page     分页参数
     * @param whereMap where语句参数值
     * @return Page
     * @author pujian
     * @date 2022/6/21 13:50
     */
    @Select("${sql}")
    <P extends IPage<Map<String, Object>>> P selectPage(@Param("sql") String sql,
                                                        P page,
                                                        @Param(WHERE_PARAM_MAP) Map<String, Object> whereMap);

}
