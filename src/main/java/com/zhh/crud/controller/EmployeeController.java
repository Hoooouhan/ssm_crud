package com.zhh.crud.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhh.crud.bean.Employee;
import com.zhh.crud.service.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 处理员工增删改查请求
 */
@Controller
public class EmployeeController {

    @Autowired
    EmployeeServiceImpl employeeServiceImpl;

    /**
     * 查询所有员工信息（分页查询）
     * @return
     */
    @RequestMapping("/emps")
    public String getEmps(@RequestParam(value="pn", defaultValue="1")Integer pn, Model model){
        //Model类参数，待会要返回到前端页面，所以我们需要返回的结果，应当添加到Model类中

        //1.这里要实现分页查询，可以使用PageHelper插件（详细配置见笔记）
        //2.在查询之前，只需要按照如下方法调用,传入页码，页大小
        PageHelper.startPage(pn, 5);
        //3.startpage函数之后紧跟的查询语句，就会被自动识别成分页查询
        List<Employee> emps = employeeServiceImpl.getAll();

        //使用pagehelper的pageinfo类来包装最后查出来的结果
        //1.pageInfo类可以同时储存查询结果、总页面、当前查到了那一页、等额外信息，方便我们前端展示
        //2.pageinfo类里面封装了详细的分页信息；到时候，只要把pageinfo类交给前端页面，前端就可以获取到足够多的额外信息
        PageInfo pageInfo = new PageInfo(emps, 5);  //5代表你想要在前端的选择条例连续提供5页的选择按钮

        model.addAttribute("pageInfo", pageInfo);

        return "list";    //返回/WEB-INF/views/list.jsp页面
    }
}
