package cn.zefre.mybatisplus.crud.controller;

import cn.zefre.base.web.annotation.WrapResponse;
import cn.zefre.mybatisplus.crud.ExpressionUtil;
import cn.zefre.mybatisplus.crud.dto.DeleteDto;
import cn.zefre.mybatisplus.crud.dto.InsertDto;
import cn.zefre.mybatisplus.crud.dto.SelectDto;
import cn.zefre.mybatisplus.crud.dto.UpdateDto;
import cn.zefre.mybatisplus.crud.page.PageModel;
import cn.zefre.mybatisplus.crud.service.CrudService;
import cn.zefre.mybatisplus.crud.sql.DeleteSqlBuilder;
import cn.zefre.mybatisplus.crud.sql.InsertSqlBuilder;
import cn.zefre.mybatisplus.crud.sql.SelectSqlBuilder;
import cn.zefre.mybatisplus.crud.sql.UpdateSqlBuilder;
import cn.zefre.mybatisplus.crud.where.Where;
import cn.zefre.mybatisplus.crud.where.WhereBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 通用crud操作接口
 *
 * @author pujian
 * @date 2022/6/21 11:14
 */
@WrapResponse
@Validated
@RestController
@RequestMapping("/crud")
@Api(value = "CrudRest", tags = "通用crud操作接口")
public class CrudRest {

    @Resource
    private CrudService crudService;


    /**
     * 新增
     *
     * @param dto dto
     * @return int
     * @author pujian
     * @date 2022/6/24 14:39
     */
    @PostMapping(value = "insert")
    @ApiOperation(value = "新增", httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public int insert(@Valid @RequestBody InsertDto dto) {
        String tableName = dto.getTableName();
        List<Map<String, Object>> values = dto.getValues();
        // fillPrimaryKey(tableName, values);
        List<String> fields = new ArrayList<>(values.get(0).keySet());
        String sql = new InsertSqlBuilder(tableName, fields, values.size()).build();
        return crudService.insert(sql, values);
    }

    /**
     * 修改
     *
     * @param dto dto
     * @return int
     * @author pujian
     * @date 2022/6/24 14:39
     */
    @PostMapping(value = "update")
    @ApiOperation(value = "修改", httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public int update(@Valid @RequestBody UpdateDto dto) {
        String tableName = dto.getTableName();
        Map<String, Object> setMap = dto.getSetMap();
        List<String> fields = new ArrayList<>(setMap.keySet());
        Where where = WhereBuilder.build(dto.getCondition());
        String sql = new UpdateSqlBuilder(tableName, fields, where).build();
        return crudService.update(sql, setMap, ExpressionUtil.getWhereMap(where));
    }

    /**
     * 删除
     *
     * @param dto dto
     * @return int
     * @author pujian
     * @date 2022/6/24 14:39
     */
    @PostMapping(value = "delete")
    @ApiOperation(value = "删除", httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public int delete(@Valid @RequestBody DeleteDto dto) {
        String tableName = dto.getTableName();
        Where where = WhereBuilder.build(dto.getCondition());
        String sql = new DeleteSqlBuilder(tableName, where).build();
        return crudService.delete(sql, ExpressionUtil.getWhereMap(where));
    }

    /**
     * 详情，查询单条
     *
     * @param dto dto
     * @return Map<String, Object>
     * @author pujian
     * @date 2022/6/24 14:40
     */
    @PostMapping(value = "detail")
    @ApiOperation(value = "详情，查询单条", httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> detail(@Valid @RequestBody SelectDto dto) {
        String tableName = dto.getTableName();
        Where where = WhereBuilder.build(dto.getCondition());
        String sql = new SelectSqlBuilder(tableName, dto.getFields(), where).build();
        Map<String, Object> whereMap = ExpressionUtil.getWhereMap(where);
        return crudService.selectOne(sql, whereMap);
    }

    /**
     * 查询列表
     *
     * @param dto dto
     * @return List<Map < String, Object>>
     * @author pujian
     * @date 2022/6/24 14:40
     */
    @PostMapping(value = "list")
    @ApiOperation(value = "查询列表", httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Map<String, Object>> list(@Valid @RequestBody SelectDto dto) {
        String tableName = dto.getTableName();
        Where where = WhereBuilder.build(dto.getCondition());
        String sql = new SelectSqlBuilder(tableName, dto.getFields(), where).build();
        Map<String, Object> whereMap = ExpressionUtil.getWhereMap(where);
        return crudService.selectList(sql, whereMap);
    }

    /**
     * 分页查询
     *
     * @param dto dto
     * @return PageResDto
     * @author pujian
     * @date 2022/6/24 14:40
     */
    @PostMapping(value = "listPage")
    @ApiOperation(value = "分页查询", httpMethod = "POST",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public PageModel<Map<String, Object>> listPage(@Valid @RequestBody SelectDto dto) {
        String tableName = dto.getTableName();
        Where where = WhereBuilder.build(dto.getCondition());
        String sql = new SelectSqlBuilder(tableName, dto.getFields(), where).build();
        Map<String, Object> whereMap = ExpressionUtil.getWhereMap(where);
        return crudService.selectPage(sql, dto, whereMap);
    }

}
