package pers.under2hump.springboot.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.under2hump.springboot.configure.annotation.IgnoreToHump;
import pers.under2hump.springboot.model.Person;

/**
 * @description 入参下划线转驼峰测试类
 *
 * @author AmVilCresx
 */
@RestController
public class TestController {

    @RequestMapping("/parameterInRequest")
    public Person parameterInRequest(String userName, int userAge, Integer testB, long c, Long d, short e){
        Person person = new Person();
        person.setUserName(userName);
        person.setUserAge(String.valueOf(userAge));
        return person;
    }

    @RequestMapping("/parameterInRequestObj")
  //  @IgnoreToHump
    public Person parameterInRequestObj(Person person){
        return person;
    }

    @PostMapping("/parameterInBody")
    @IgnoreToHump
    public Person parameterInBody( @RequestBody Person person){
        return person;
    }
}
