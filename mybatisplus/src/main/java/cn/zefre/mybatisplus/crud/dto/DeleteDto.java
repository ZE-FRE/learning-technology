package cn.zefre.mybatisplus.crud.dto;

import cn.zefre.mybatisplus.crud.Condition;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author pujian
 * @date 2022/6/24 14:07
 */
@Data
@ApiModel(value = "删除Dto")
public class DeleteDto {

    /**
     * 表名
     */
    @NotEmpty(message = "表名不能为空")
    @ApiModelProperty(value = "表名", required = true)
    private String tableName;

    /**
     * 筛选条件
     */
    @ApiModelProperty(value = "筛选条件")
    private Condition condition;
}
