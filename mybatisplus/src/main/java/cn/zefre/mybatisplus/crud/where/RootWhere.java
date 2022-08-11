package cn.zefre.mybatisplus.crud.where;

/**
 * {@link Where}树根节点
 * 此类可有可无，{@link CompositeWhere}本身也可以代表根节点
 * 创建此类目的：
 * 1、突出根节点
 * 2、使拼接出来的sql最外层少一层括号
 *
 * @author pujian
 * @date 2022/6/22 16:36
 */
public final class RootWhere extends CompositeWhere {

    public RootWhere() {
        super(null);
    }

    @Override
    public String getAsString() {
        String result = super.getAsString();
        return result.substring(1, result.length() - 1);
    }

}
