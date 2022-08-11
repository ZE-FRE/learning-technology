package cn.zefre.validation.hibernate.entity.group;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

/**
 * @author pujian
 * @date 2021/10/18 14:51
 */
@Data
@AllArgsConstructor
public class Phone {

    @NotNull(message = "brand不能为null")
    private String brand;

    /**
     * 是否附带充电器
     */
    @AssertTrue(message = "手机必须附带充电器", groups = Domestic.class)
    private boolean withCharger;
}
