package cn.zefre.mybatisplus.crud.dto;

import cn.zefre.mybatisplus.crud.Condition;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Map;

/**
 * @author pujian
 * @date 2022/6/24 14:06
 */
@Data
@ApiModel(value = "修改Dto")
public class UpdateDto {

    /**
     * 表名
     */
    @NotEmpty(message = "表名不能为空")
    @ApiModelProperty(value = "表名", required = true)
    private String tableName;

    /**
     * SET
     */
    @NotEmpty(message = "SET不能为空")
    @ApiModelProperty(value = "SET", required = true)
    private Map<String, Object> setMap;

    /**
     * 筛选条件
     */
    @ApiModelProperty(value = "筛选条件")
    private Condition condition;

}
