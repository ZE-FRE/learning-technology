package cn.zefre.validation.hibernate.entity.group;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author pujian
 * @date 2021/10/18 15:02
 */
@Data
@AllArgsConstructor
public class Child {

    @NotBlank(message = "name不能为null")
    private String name;

    @Min(value = 18, message = "美国未成年人禁止玩儿手机", groups = America.class)
    private int age;

    @NotNull
    @Valid
    private Phone phone;

}
