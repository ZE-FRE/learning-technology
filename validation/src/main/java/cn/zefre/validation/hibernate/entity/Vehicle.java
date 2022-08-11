package cn.zefre.validation.hibernate.entity;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author pujian
 * @date 2021/10/15 15:50
 */
@Data
public class Vehicle {

    /**
     * 牌照
     */
    @NotNull
    private String licensePlate;

    @Valid
    private Person driver;

    public Vehicle(String licensePlate, Person driver) {
        this.licensePlate = licensePlate;
        this.driver = driver;
    }

}
