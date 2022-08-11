package cn.zefre.validation.hibernate.entity;


import javax.validation.constraints.NotNull;

/**
 * property-level constraints
 *
 * @author pujian
 * @date 2021/10/15 10:04
 */
public class CarByProperty {

    private String manufacturer;

    public CarByProperty(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @NotNull
    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
}
