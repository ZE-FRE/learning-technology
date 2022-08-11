package cn.zefre.mybatisplus.crud;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 筛选条件树
 * 前端传参
 *
 * @author pujian
 * @date 2022/6/22 18:06
 */
@Data
@ApiModel(value = "筛选条件")
public class Condition {

    @ApiModelProperty(value = "连词")
    private ConjunctionEnum conjunction;

    /**
     * 与{@link Condition#children}互斥
     */
    @ApiModelProperty(value = "表达式，与children互斥")
    private Expression expression;

    /**
     * 与{@link Condition#expression}互斥
     */
    @ApiModelProperty(value = "子条件，与expression互斥")
    private List<Condition> children;


    public void setExpression(Expression expression) {
        if (expression == null) {
            return;
        }
        if (!CollectionUtils.isEmpty(children)) {
            throw new IllegalStateException("expression和children只能存在一个");
        }
        this.expression = expression;
    }

    public void setChildren(List<Condition> children) {
        if (CollectionUtils.isEmpty(children)) {
            return;
        }
        if (expression != null) {
            throw new IllegalStateException("expression和children只能存在一个");
        }
        this.children = children;
    }

    public boolean isAtomic() {
        return expression != null;
    }

}
