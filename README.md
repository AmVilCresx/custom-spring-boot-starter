# custom-spring-boot-starter

#### 介绍
基于Spring boot框架，自定义Starter

#### 模块介绍

##### 1. underline2Hump-spring-boot-web-starter

######  1.1 特性

* 支持基本类型或String类型参数下划线风格自动转驼峰
* 支持自定义参数对象的属性下划线风格自动转驼峰
* 支持Body参数下划线风格自动转驼峰
* 支持多级对象嵌套参数下划线风格自动转驼峰

* 兼容`@RequestParam`注解

###### 1.2 使用说明

* 根据源码编译打包，或者下载这里提供的jar包, [点击下载](https://pan.baidu.com/s/1YK9kmQRYUMBNKdj15TjkkA)，提取码: a3xu 。然后把jar包放入maven仓库

  ```shell
  mvn install:install-file -Dfile=underline2hump-spring-boot-starter-1.0.0.RELEASE.jar -DgroupId=pers.avc.springboot -DartifactId=underline2hump-spring-boot-starter -Dversion=1.0.0.RELEASE -Dpackaging=jar
  ```

* 添加maven依赖

  ```xml
  <dependency>
              <groupId>pers.avc.springboot</groupId>
              <artifactId>underline2hump-spring-boot-starter</artifactId>
      		<!-- 根据实际情况，选择性排除 -->
              <exclusions>
                  <exclusion>
                      <groupId>org.springframework.boot</groupId>
                      <artifactId>spring-boot-starter-web</artifactId>
                  </exclusion>
              </exclusions>
              <version>1.0.0.RELEASE</version>
          </dependency>
  ```

  

* 配置包扫描路径，如：`@SpringBootApplication(scanBasePackages = {Underline2HumpConstant.SCAN_COMPONENT_PATH, "person.springboot.ordinary"})`。

* 在启动类上标注`@EnableUnderLineToHump` 注解即可实现自动激活。

* 如果在参数对象中某个属性是自定义对象，则需要配合`@SubAttribute` 注解才可实现二级解析。

  ![](https://i.loli.net/2020/12/28/t5urnVAphZQmHJs.png)

* 倘若某方法不想使用该功能，则只需在方法上标注`@IgnoreToHump`注解即可。

  > 对于方法的某个参数，下划线和驼峰参数都是可以映射到实体类字段的。比如说，参数的名是user_name, 那么实体类中userName或user_name均可以映射，无需再选择性忽略。

​	

