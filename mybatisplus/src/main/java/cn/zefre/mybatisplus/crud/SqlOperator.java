package cn.zefre.mybatisplus.crud;

import cn.zefre.mybatisplus.crud.mapper.GenericMapper;

import java.util.Collections;
import java.util.List;

/**
 * sql条件符
 *
 * @author pujian
 * @date 2022/6/21 13:34
 */
public enum SqlOperator {

    EQ("等于") {
        @Override
        public String prepareExpression(String field, Object value) {
            return field + " = " + concatAsPlaceholder(field);
        }
    },
    LIKE("包含") {
        @Override
        public String prepareExpression(String field, Object value) {
            return field + " LIKE CONCAT('%'," + concatAsPlaceholder(field) + ",'%')";
        }
    },
    STARTS_WITH("以...开始") {
        @Override
        public String prepareExpression(String field, Object value) {
            return field + " LIKE CONCAT('%'," + concatAsPlaceholder(field) + ")";
        }
    },
    ENDS_WITH("以...结尾") {
        @Override
        public String prepareExpression(String field, Object value) {
            return field + " LIKE CONCAT(" + concatAsPlaceholder(field) + ",'%')";
        }
    },
    IN("在...中") {
        @Override
        public String prepareExpression(String field, Object value) {
            return field + " IN (" + concatAsPlaceholderForIn(field, value) + ")";
        }
    },
    NOT_EQ("不等于") {
        @Override
        public String prepareExpression(String field, Object value) {
            return field + " <> " + concatAsPlaceholder(field);
        }
    },
    GT("大于") {
        @Override
        public String prepareExpression(String field, Object value) {
            return field + " > " + concatAsPlaceholder(field);
        }
    },
    GE("大于等于") {
        @Override
        public String prepareExpression(String field, Object value) {
            return field + " >= " + concatAsPlaceholder(field);
        }
    },
    LT("小于") {
        @Override
        public String prepareExpression(String field, Object value) {
            return field + " < " + concatAsPlaceholder(field);
        }
    },
    LE("小于等于") {
        @Override
        public String prepareExpression(String field, Object value) {
            return field + " <= " + concatAsPlaceholder(field);
        }
    };

    private final String description;

    SqlOperator(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    /**
     * 输出预处理表达式
     *
     * @param field 字段
     * @param value 字段值
     * @return java.lang.String
     * @author pujian
     * @date 2022/6/21 13:56
     */
    public abstract String prepareExpression(String field, Object value);

    private static final String PLACEHOLDER_PREFIX = "#{" + GenericMapper.WHERE_PARAM_MAP + ".";
    private static final String PLACEHOLDER_SUFFIX = "}";

    /**
     * 拼接sql参数预处理占位符
     *
     * @param param 字段
     * @return java.lang.String
     * @author pujian
     * @date 2022/6/22 10:01
     */
    private static String concatAsPlaceholder(String param) {
        return PLACEHOLDER_PREFIX + param + PLACEHOLDER_SUFFIX;
    }

    /**
     * 拼接IN查询参数预处理占位符
     *
     * @param param 字段
     * @param value 字段值
     * @return java.lang.String
     * @author pujian
     * @date 2022/6/23 16:53
     */
    private static String concatAsPlaceholderForIn(String param, Object value) {
        param = PLACEHOLDER_PREFIX + param;
        List<?> list = value instanceof List ? (List<?>) value : Collections.singletonList(value);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                result.append(",");
            }
            result.append(param).append("[").append(i).append("]").append(PLACEHOLDER_SUFFIX);
        }
        return result.toString();
    }

}
