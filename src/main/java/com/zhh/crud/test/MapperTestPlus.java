package com.zhh.crud.test;

import com.zhh.crud.bean.Department;
import com.zhh.crud.bean.Employee;
import com.zhh.crud.dao.DepartmentMapper;
import com.zhh.crud.dao.EmployeeMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

/**
 * 测试dao层和mapper ， 来确定mybatis是否配置完好
 *
 * 使用spring的单元测试，而不是普通的单元测试方法来测试：
 * 1.在pom中导入spring test模块
 * 2.使用注解@ContextConfiguration 指定spring配置文件的位置
 * 3.使用@Runwith注解，来指明单元测试运行时，使用什么类型的单元测试，这里我们指向spring的单元测试
 * 4.使用@autowired注解（依赖注入），指定我们需要使用的组件
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")  //指定spring的配置文件
public class MapperTestPlus {

    @Autowired   //此注解的作用：被此注解标记的类，创建对象的过程将会由 spring 容器接管，你只需要定义对象名称即可
    DepartmentMapper departmentMapper;

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    SqlSession sqlSession;  //用于批量插入数据，所以配置一个sqlsession

    /**
     * 测试 DepartmentMapper
     */
    @Test
    public void testCRUD(){
        //测试spring test配置后，类能否调用成功
        System.out.println(departmentMapper);

        //1.插入几个部门
//        departmentMapper.insertSelective(new Department(null,"开发部"));
//        departmentMapper.insertSelective(new Department(null,"测试部"));

        //2.插入几个员工
//        employeeMapper.insertSelective(new Employee(null, "Jerry", "M", "Jerry@atguigu.com", 2));

        //3.批量插入
        //3.1.旧的写法
        /*  for (){
            employeeMapper.insertSelective(new Employee(null, ,"M","jerry@pl.com",1));
        }*/

        // 3.2.新的写法：为了方便使用循环进行批量插入，我们在spring的配置文件applicationContext.xml中新增配置一个可以执行批量的sqlSession
        //目的：使用for循环的方法插入n次，需要数据库连接n次，我们使用sqlsession，可以连接一次，插入所有数据，减少了开销
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        for (int i = 0; i < 45; i++){
            String uid = UUID.randomUUID().toString().substring(0,5) + i;
            mapper.insertSelective(new Employee(null,uid, "M", uid+"@aiguigu.com", 2));
        }
        System.out.println("批量完成");





    }
}
