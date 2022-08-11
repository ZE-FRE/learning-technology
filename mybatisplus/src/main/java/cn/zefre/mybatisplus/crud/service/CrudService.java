package cn.zefre.mybatisplus.crud.service;

import cn.zefre.mybatisplus.crud.dto.SelectDto;
import cn.zefre.mybatisplus.crud.page.PageModel;

import java.util.List;
import java.util.Map;

/**
 * @author pujian
 * @date 2022/6/24 15:02
 */
public interface CrudService {

    /**
     * 插入操作
     *
     * @param sql    insert语句
     * @param values 待插入数据
     * @return int 成功插入数
     * @author pujian
     * @date 2022/6/24 15:05
     */
    int insert(String sql, List<? extends Map<String, Object>> values);

    /**
     * 修改操作
     *
     * @param sql      update语句
     * @param setMap   set语句参数值
     * @param whereMap where语句参数值
     * @return int 成功修改数
     * @author pujian
     * @date 2022/6/24 15:05
     */
    int update(String sql, Map<String, Object> setMap, Map<String, Object> whereMap);

    /**
     * 删除操作
     *
     * @param sql      delete语句
     * @param whereMap where语句参数值
     * @return int 成功删除数
     * @author pujian
     * @date 2022/6/24 15:05
     */
    int delete(String sql, Map<String, Object> whereMap);

    /**
     * 单条查询
     *
     * @param sql      单条查询sql
     * @param whereMap where语句参数值
     * @return Map<String, Object>
     * @author pujian
     * @date 2022/6/24 15:15
     */
    Map<String, Object> selectOne(String sql, Map<String, Object> whereMap);

    /**
     * 列表查询
     *
     * @param sql      列表查询sql
     * @param whereMap where语句参数值
     * @return java.util.List
     * @author pujian
     * @date 2022/6/24 15:15
     */
    List<Map<String, Object>> selectList(String sql, Map<String, Object> whereMap);

    /**
     * 分页查询
     *
     * @param sql      分页查询sql
     * @param dto      dto
     * @param whereMap where语句参数值
     * @return PageModel<Map < String, Object>>
     * @author pujian
     * @date 2022/6/24 15:15
     */
    PageModel<Map<String, Object>> selectPage(String sql, SelectDto dto, Map<String, Object> whereMap);

}
