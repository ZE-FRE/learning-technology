package cn.zefre.mybatisplus.crud;

/**
 * 连词
 *
 * @author pujian
 * @date 2022/6/21 13:15
 */
public enum ConjunctionEnum {

    AND("AND(所有条件都要求匹配)"),
    OR("OR(条件中任意一个匹配)");

    private final String description;

    ConjunctionEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
