package cn.zefre.validation.hibernate.custom;

/**
 * @author pujian
 * @date 2021/10/19 11:09
 */
public class Computer {

    /**
     * 厂商大写
     */
    @CheckCase(value = CaseMode.UPPER, message = "厂商必须大写")
    private String manufacturer;

    public Computer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
}
