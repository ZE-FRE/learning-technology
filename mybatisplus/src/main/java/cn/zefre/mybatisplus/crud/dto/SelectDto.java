package cn.zefre.mybatisplus.crud.dto;

import cn.zefre.mybatisplus.crud.Condition;
import cn.zefre.mybatisplus.crud.page.PageModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

/**
 * @author pujian
 * @date 2022/6/24 14:00
 */
@Data
@ApiModel(value = "查询Dto")
public class SelectDto {

    /**
     * 表名
     */
    @NotEmpty(message = "表名不能为空")
    @ApiModelProperty(value = "表名", required = true)
    private String tableName;

    /**
     * 查询字段列表
     */
    @NotEmpty(message = "查询字段列表不能为空")
    @ApiModelProperty(value = "查询字段列表", required = true)
    private List<String> fields;

    /**
     * 筛选条件
     */
    @ApiModelProperty(value = "筛选条件")
    private Condition condition;

    /**
     * 分页参数
     */
    @ApiModelProperty(value = "分页参数")
    private PageModel<Map<String, Object>> pageModel;

}
