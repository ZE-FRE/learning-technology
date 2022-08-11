package cn.zefre.mybatisplus.crud.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

/**
 * @author pujian
 * @date 2022/6/24 14:01
 */
@Data
@ApiModel(value = "插入Dto")
public class InsertDto {

    /**
     * 表名
     */
    @NotEmpty(message = "表名不能为空")
    @ApiModelProperty(value = "表名", required = true)
    private String tableName;

    /**
     * VALUES
     */
    @NotEmpty(message = "VALUES不能为空")
    @ApiModelProperty(value = "VALUES", required = true)
    private List<Map<String, Object>> values;
}
