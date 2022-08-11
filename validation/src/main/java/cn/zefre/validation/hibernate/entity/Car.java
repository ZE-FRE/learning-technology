package cn.zefre.validation.hibernate.entity;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author pujian
 * @date 2021/10/14 16:39
 */
@Data
public class Car {

    /**
     * 厂商
     */
    @NotNull
    private String manufacturer;

    /**
     * 牌照
     */
    @NotNull
    @Size(min = 2, max = 14)
    private String licensePlate;

    /**
     * 座位数
     */
    @Min(2)
    private int seatCount;

    public Car(String manufacturer, String licencePlate, int seatCount) {
        this.manufacturer = manufacturer;
        this.licensePlate = licencePlate;
        this.seatCount = seatCount;
    }

}
