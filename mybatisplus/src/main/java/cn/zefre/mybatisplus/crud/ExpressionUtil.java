package cn.zefre.mybatisplus.crud;

import cn.zefre.mybatisplus.crud.where.Where;
import lombok.experimental.UtilityClass;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author pujian
 * @date 2022/6/22 16:54
 */
@UtilityClass
public class ExpressionUtil {

    /**
     * 输出预处理表达式
     *
     * @param expression 条件表达式
     * @return java.lang.String
     * @author pujian
     * @date 2022/6/23 17:33
     */
    public static String prepareExpression(Expression expression) {
        if (expression != null) {
            SqlOperator operator = expression.getOperator();
            return operator.prepareExpression(expression.getField(), expression.getValue());
        }
        return null;
    }

    public static Map<String, Object> getWhereMap(Where where) {
        if (null != where) {
            List<Expression> expressions = new ArrayList<>();
            where.collectExpressions(expressions);
            return toMap(expressions);
        }
        return null;
    }

    public static Map<String, Object> toMap(List<Expression> expressions) {
        if (CollectionUtils.isEmpty(expressions)) {
            return null;
        }
        return expressions.stream().collect(
                Collectors.toMap(Expression::getField, ExpressionUtil::getExpressionValue,
                        (existing, duplicate) -> {
                            throw new IllegalArgumentException("重复的参数");
                        }));
    }

    public static Object getExpressionValue(Expression expression) {
        if (expression.getOperator() == SqlOperator.IN) {
            Object value = expression.getValue();
            List<?> list = value instanceof List ? (List<?>) value : Collections.singletonList(value);
            if (list.isEmpty()) {
                throw new IllegalArgumentException("IN查询参数不能为空");
            }
            return list;
        }
        return expression.getValue();
    }

}
