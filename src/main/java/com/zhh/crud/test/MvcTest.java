package com.zhh.crud.test;

import com.github.pagehelper.PageInfo;
import com.zhh.crud.bean.Employee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

/**
 * 使用Spring测试模块提供的测试请求功能
 *
 * 继续使用spring单元测试（可参考MapperTestPlus）
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration  //此注解是为了能让一个容器能在另一个容器里面自动装配autowired
@ContextConfiguration(locations = { "classpath:applicationContext.xml",
        "file:src/main/webapp/WEB-INF/dispatcherServlet-servlet.xml" })   //指定spring的配置文件，和spring mvc的配置文件
public class MvcTest {
    //把spring mvc的 ioc容器 传入进来（传入到test的容器中来）
    @Autowired  //和@WebAppConfiguration 注解配合
    WebApplicationContext context;

    //使用虚拟mvc的请求，获取到处理结果
    MockMvc mockMvc;

    @Before   //此注解作用：每次运行时，调用一下此函数，完成mockMvc的初始化
    public void initMockMvc(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testPage() throws Exception {
        //模拟发送请求方法,请求emps页面，请求参数为pn=1 （第一页）,然后接受返回值
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/emps").param("pn","5"))
                .andReturn();

        //请求成功以后，请求域中会有pageinfo，我们可以取出pageinfo 来验证数据是否准确
        MockHttpServletRequest request = result.getRequest();
        PageInfo pi = (PageInfo) request.getAttribute("pageInfo");

        System.out.println("当前页码： " + pi.getPageNum() );
        System.out.println("总页码： " + pi.getPages() );
        System.out.println("总记录数： " + pi.getTotal() );
        System.out.println("连续显示的页码： ");
        int[] nums = pi.getNavigatepageNums();
        for (int i : nums){
            System.out.println(" " + i);
        }

        //获取员工数据
        //获取员工数据
        List<Employee> list = pi.getList();
        for (Employee employee : list) {
            System.out.println("ID："+employee.getEmpId()+"==>Name:"+employee.getEmpName());
        }

    }

}
