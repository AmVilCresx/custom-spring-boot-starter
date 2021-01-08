package pers.under2hump.springboot.model;

import java.util.Date;

/**
 * @description 三级子对象
 * @author AmVilCresx
 */
public class ThirdLevel {

    private String thirdName;

    private Integer thirdLen;

    private Date accessTime;

    private String region_name;

    public String getThirdName() {
        return thirdName;
    }

    public void setThirdName(String thirdName) {
        this.thirdName = thirdName;
    }

    public Integer getThirdLen() {
        return thirdLen;
    }

    public void setThirdLen(Integer thirdLen) {
        this.thirdLen = thirdLen;
    }

    public Date getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(Date accessTime) {
        this.accessTime = accessTime;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }
}
