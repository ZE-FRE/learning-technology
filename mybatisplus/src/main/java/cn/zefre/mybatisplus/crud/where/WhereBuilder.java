package cn.zefre.mybatisplus.crud.where;

import cn.zefre.mybatisplus.crud.Condition;
import lombok.experimental.UtilityClass;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author pujian
 * @date 2022/6/23 10:18
 */
@UtilityClass
public class WhereBuilder {

    private static boolean conditionIsInvalid(Condition condition) {
        return condition == null || (condition.getExpression() == null && CollectionUtils.isEmpty(condition.getChildren()));
    }

    /**
     * 根据{@link Condition}构造{@link Where}
     *
     * @param condition where条件
     * @return Where
     * @author pujian
     * @date 2022/6/24 14:46
     */
    public static Where build(Condition condition) {
        // 过滤前端传无效对象过来
        if (conditionIsInvalid(condition)) {
            return null;
        }
        if (condition.isAtomic()) {
            return new AtomicWhere(condition.getConjunction(), condition.getExpression());
        } else {
            Where root = new RootWhere();
            build(root, condition.getChildren());
            return root;
        }
    }

    /**
     * 递归构造{@link Where}
     *
     * @param parent   parent
     * @param children children
     * @author pujian
     * @date 2022/6/24 14:49
     */
    private static void build(Where parent, List<Condition> children) {
        if (CollectionUtils.isEmpty(children)) {
            return;
        }
        for (Condition condition : children) {
            // 过滤前端传无效对象过来
            if (conditionIsInvalid(condition)) {
                continue;
            }
            if (condition.isAtomic()) {
                parent.addChild(new AtomicWhere(condition.getConjunction(), condition.getExpression()));
            } else {
                CompositeWhere compositeWhere = new CompositeWhere(condition.getConjunction());
                parent.addChild(compositeWhere);
                build(compositeWhere, condition.getChildren());
            }
        }
    }

}
