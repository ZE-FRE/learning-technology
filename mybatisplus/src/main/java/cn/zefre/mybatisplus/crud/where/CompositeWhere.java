package cn.zefre.mybatisplus.crud.where;

import cn.zefre.mybatisplus.crud.ConjunctionEnum;
import cn.zefre.mybatisplus.crud.Expression;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 嵌套的条件
 * Where where = new CompositeWhere(ConjunctionEnum.OR);
 * where.addChild(new AtomicWhere(null, new Expression("en_name", SqlOperator.EQ, "vip_price")));
 * where.addChild(new AtomicWhere(ConjunctionEnum.AND, new Expression("field_length", SqlOperator.LT, 60)));
 * 表示：OR (en_name = 'vip_price' AND field_length < 60)
 *
 * @author pujian
 * @date 2022/6/22 14:48
 */
public class CompositeWhere extends Where {

    private List<Where> children;

    public CompositeWhere(ConjunctionEnum conjunction) {
        super(conjunction);
        this.children = new LinkedList<>();
    }

    @Override
    public String getAsString() {
        Assert.state(CollectionUtils.isNotEmpty(children), "嵌套条件还未构造完毕");
        StringBuilder result = new StringBuilder();
        result.append("(");
        Where firstChild = children.get(0);
        result.append(firstChild.getAsString());
        // 继续拼接后续条件
        for (int i = 1; i < children.size(); i++) {
            Where child = children.get(i);
            Assert.notNull(child.conjunction, "条件错误，多个条件之间缺少" + Arrays.toString(ConjunctionEnum.values()) + "连接");
            result.append(" ").append(child.conjunction).append(" ").append(child.getAsString());
        }
        result.append(")");
        return result.toString();
    }

    @Override
    public void collectExpressions(List<Expression> results) {
        children.forEach(expression -> expression.collectExpressions(results));
    }

    @Override
    public void addChild(Where child) {
        children.add(child);
    }

}
