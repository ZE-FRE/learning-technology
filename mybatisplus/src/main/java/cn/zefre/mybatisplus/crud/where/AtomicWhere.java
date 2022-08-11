package cn.zefre.mybatisplus.crud.where;

import cn.zefre.mybatisplus.crud.ConjunctionEnum;
import cn.zefre.mybatisplus.crud.Expression;
import cn.zefre.mybatisplus.crud.ExpressionUtil;

import java.util.List;

/**
 * WHERE条件基本单位
 * new AtomicWhere(null, new Expression("data_element_id", SqlOperator.GE, 1));
 * 表示：data_element_id >= 1
 * new AtomicWhere(ConjunctionEnum.AND, new Expression("en_name", SqlOperator.STARTS_WITH, "zhang"));
 * 表示：AND en_name LIKE '%zhang'
 *
 * @author pujian
 * @date 2022/6/22 14:46
 */
public final class AtomicWhere extends Where {

    private final Expression expression;

    public AtomicWhere(ConjunctionEnum conjunction, Expression expression) {
        super(conjunction);
        if (expression == null) {
            throw new IllegalArgumentException("expression不能为空");
        }
        this.expression = expression;
    }

    @Override
    public String getAsString() {
        return ExpressionUtil.prepareExpression(expression);
    }

    @Override
    public void collectExpressions(List<Expression> results) {
        results.add(expression);
    }

}
