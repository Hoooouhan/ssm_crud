package com.zhh.crud.test;

import com.zhh.crud.dao.DepartmentMapper;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 测试dao层和mapper ， 来确定mybatis是否配置完好
 */
public class MapperTest {

    /**
     * 测试 DepartmentMapper
     */
    @Test
    public void testCRUD(){
        /**
         * 一般情况下使用的测试方法：创建容器，然后。。。。
         *
         * 但是实际上，我们有更加好的方法，  *** 可以直接使用spring提供的单元测试来进行我们的测试，可以自动注入我们需要的组件 ***
         * 所以spring方法的单元测试，详见MapperTestPlus.java
         */
        //1.创建springioc容器(由配置文件传入)
        ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
        //2.从容器中获取mapper
        ioc.getBean(DepartmentMapper.class);

    }
}
