package pers.under2hump.springboot.model;


import pers.under2hump.springboot.configure.annotation.SubAttribute;

/**
 * @description 二级子对象
 * @author AmVilCresx
 */
public class SecondLevel {

    private String address;

    private String fromCountry;

    @SubAttribute
    private ThirdLevel third;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFromCountry() {
        return fromCountry;
    }

    public void setFromCountry(String fromCountry) {
        this.fromCountry = fromCountry;
    }

    public ThirdLevel getThird() {
        return third;
    }

    public void setThird(ThirdLevel third) {
        this.third = third;
    }

    @Override
    public String toString() {
        return "SecondLevel{" +
                "address='" + address + '\'' +
                ", fromCountry='" + fromCountry + '\'' +
                ", third=" + third +
                '}';
    }
}
