package cn.zefre.mybatisplus.crud;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 表达式
 * 如：
 * new Expression("id", SqlOperator.EQ, 1);  表示 id = 1
 * new Expression("name", SqlOperator.LIKE, "张"); 表示 name LIKE '%张%'
 *
 * @author pujian
 * @date 2022/6/21 13:11
 */
@Data
@ApiModel(value = "表达式")
@NoArgsConstructor
@AllArgsConstructor
public class Expression {

    /**
     * 字段
     */
    @NotEmpty
    @ApiModelProperty(value = "字段")
    private String field;

    /**
     * 操作符
     */
    @NotNull
    @ApiModelProperty(value = "操作符")
    private SqlOperator operator;

    /**
     * 字段值
     */
    @NotNull
    @ApiModelProperty(value = "字段值")
    private Object value;

}
