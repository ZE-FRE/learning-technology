package cn.zefre.mybatisplus.crud.where;

import cn.zefre.mybatisplus.crud.ConjunctionEnum;
import cn.zefre.mybatisplus.crud.Expression;

import java.util.List;

/**
 * WHERE子句条件树
 *
 * @author pujian
 * @date 2022/6/22 14:51
 */
public abstract class Where {

    /**
     * SQL WHERE条件连接词
     * 在{@link Where#getAsString}方法中由父节点，即{@link CompositeWhere}负责拼接
     */
    protected ConjunctionEnum conjunction;

    protected Where(ConjunctionEnum conjunction) {
        this.conjunction = conjunction;
    }

    /**
     * 得到字符串形式的WHERE条件表达式
     *
     * @return java.lang.String
     * @author pujian
     * @date 2022/6/22 17:38
     */
    public abstract String getAsString();

    /**
     * 获取{@link Expression}结果集
     *
     * @param results {@link Expression}结果集
     * @author pujian
     * @date 2022/6/22 17:38
     */
    public abstract void collectExpressions(List<Expression> results);

    /**
     * 添加子节点
     *
     * @param child 子节点
     * @author pujian
     * @date 2022/6/22 17:37
     */
    public void addChild(Where child) {
        throw new UnsupportedOperationException();
    }

}
