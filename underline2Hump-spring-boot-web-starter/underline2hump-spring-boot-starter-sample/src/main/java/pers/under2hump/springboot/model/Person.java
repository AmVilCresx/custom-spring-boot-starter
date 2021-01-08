package pers.under2hump.springboot.model;


import pers.under2hump.springboot.configure.annotation.SubAttribute;

import java.util.Arrays;
import java.util.List;

/**
 * @description Person实体类
 * @author AmVilCresx
 */
public class Person {

    private String userName;

    private String userAge;

    @SubAttribute
    private SecondLevel secondLevel;

    private List<String> favourHobbies;

    private Integer[] numbers;

    public Person() { }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAge() {
        return userAge;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

    public SecondLevel getSecondLevel() {
        return secondLevel;
    }

    public void setSecondLevel(SecondLevel secondLevel) {
        this.secondLevel = secondLevel;
    }

    public List<String> getFavourHobbies() {
        return favourHobbies;
    }

    public void setFavourHobbies(List<String> favourHobbies) {
        this.favourHobbies = favourHobbies;
    }

    public Integer[] getNumbers() {
        return numbers;
    }

    public void setNumbers(Integer[] numbers) {
        this.numbers = numbers;
    }

    @Override
    public String toString() {
        return "Person{" +
                "userName='" + userName + '\'' +
                ", userAge='" + userAge + '\'' +
                ", secondLevel=" + secondLevel +
                ", favourHobbies=" + favourHobbies +
                ", numbers=" + Arrays.toString(numbers) +
                '}';
    }
}
