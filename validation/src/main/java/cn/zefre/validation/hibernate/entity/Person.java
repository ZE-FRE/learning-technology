package cn.zefre.validation.hibernate.entity;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author pujian
 * @date 2021/10/15 15:50
 */
@Data
public class Person {

    @NotNull
    private String name;

    @NotNull
    @Valid
    private Vehicle vehicle;

    public Person(String name) {
        this.name = name;
    }

}
