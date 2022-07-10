package com.nowcoder.community.controller;

import com.nowcoder.community.service.AlphaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
//用于标记在一个类上，使用它标记的类就是一个SpringMVC Controller对象。分发处理器将会扫描
//使用了该注解的类的方法。即：被Controller标记的类就是一个控制器，这个类中的方法就是相应的动作

@RequestMapping("/alpha")
//@RequestMapping是一个用来处理请求地址映射的注解，可用于类或方法上。
// 用于类上，表示类中的所有响应请求的方法都是以该地址作为父路径。
public class AlphaController {

    //controller依赖于service，将service注入
    @Autowired  //@Autowired可以对成员变量、方法和构造函数进行标注，来完成自动装配的工作
                //根据类型自动装配的
    private AlphaService alphaService;

    @RequestMapping("/hello")
    @ResponseBody
    public String sayHello() {
        return "Hello Spring Boot.";
    }

    //浏览器访问该方法的前提是要有注解声明路径
    @RequestMapping("/data")
    @ResponseBody
    //模拟查询请求的方法
    public String getData() {
        return alphaService.find(); //将find（）的结果返回到浏览器，
        //浏览器访问该方法的前提是要有注解声明路径
    }


    //在Spring MVC框架下，如何获得请求对象？
    @RequestMapping("/http")
    public void http(HttpServletRequest request, HttpServletResponse response) {
        // 获取请求数据
        System.out.println(request.getMethod()); //获取请求方式
        System.out.println(request.getServletPath()); //请求路径

        Enumeration<String> enumeration = request.getHeaderNames(); //获取迭代器对象 得到所有请求行的key
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            String value = request.getHeader(name);
            System.out.println(name + ":" + value);
        }
        System.out.println(request.getParameter("code"));

        //repose是对浏览器做出响应的对象
        //返回响应数据
        response.setContentType("text/html;charset=utf-8");
        try (
                PrintWriter writer = response.getWriter();
                ){
            writer.write("<h1>牛客网</h1>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //比上面更简单的方式：
    //处理浏览器请求分为两方面：一是基于request接收请求的数据，二是基于response进行返回
    //GET请求
    //假设要查询所有的学生（分页显示-->需要设置分页条件如当前页与每页最大显示条数），
    // 查询的路径是： /students?current=1&limit=20
    @RequestMapping(path = "/students", method = RequestMethod.GET)
    @ResponseBody
    public String getStudnets(
            @RequestParam(name = "current", required = false, defaultValue = "1") int current,
            @RequestParam(name = "limit", required = false, defaultValue = "10")int limit) {
                //上面两句注解的意思是：将名为“current”的参数传给current，也可以不传，那默认值就为1，
                // 这样对参数的注入做更详尽的解释
        System.out.println(current);
        System.out.println(limit);

        return "Some students";
    }

    //另一种获取方式，如通过学生id获取，且id直接在路径中，
    // 例如：/student/123
    @RequestMapping(path = "/student/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getStudent(@PathVariable("id") int id) {
        System.out.println(id);
        return "a student";
    }


    //浏览器向服务器提交数据
    //POST请求 （要先有网页，所以先创建一个静态网页），
    // 注意：templates里面放的是模板是动态的，静态资源要放在static下
    //在static下创建一个名为html的目录
    @RequestMapping(path = "/student", method = RequestMethod.POST)
    @ResponseBody
    public String saveStudent(String name, int age) {
            //saveStudent方法获取post中的参数：方法中参数的名字与表单中参数的名字一致就可以传过来
        System.out.println(name);
        System.out.println(age);
        return "success";
    }


    //向浏览器返回响应
    //响应html数据
    //假设要从浏览器查询一位老师，服务器查出之后就将其返回给浏览器
    @RequestMapping(path="/teacher", method = RequestMethod.GET)
    //不加@ResponseBody注解默认返回的是html文件
    public ModelAndView getTeacher() {
        ModelAndView mav = new ModelAndView();
        //模板里需要多少个变量，那就addObject多少个数据
        mav.addObject("name","张三"); //传入动态的值
        mav.addObject("age","30");

        //设置模板路径和名字， 模板需要放置在templates下（demo目录view。html文件）
        mav.setViewName("/demo/view"); //view实际上是指view.html文件
        return mav;
    }

    //比上面的查询老师要更简单一点,推荐开发的时候使用
    //假设查询学校
    @RequestMapping(path = "/school", method = RequestMethod.GET)
    public String getSchool(Model model) {
        //上一种是将model和view都装在一个对象里
        //而这一种是将model数据放进参数里，把view的视图直接返回
        model.addAttribute("name", "PekingU");
        model.addAttribute("age", 100);

        return "/demo/view"; //返回的是view的路径
    }




    //向浏览器响应JSON数据（通常在异步请求中）
        //异步请求：当前网页不刷新但是已经访问了服务器
    //Java对象 浏览器用JS解析Java对象得到JS对象，这两种对象不兼容，采用JSON字符串
    //Java对象 -> JSON字符串 -> JS对象

    @RequestMapping(path = "/emp", method = RequestMethod.GET)
    @ResponseBody //向浏览器返回JSON加上这句注解,否则认为返回的是html文件
    public Map<String, Object> getEmp() {
        Map<String, Object> emp = new HashMap<>();
        emp.put("name", "李四");
        emp.put("age", 23);
        emp.put("salary", 8000.00);
        return emp;
    }

    //上面修改一下：返回多个
    @RequestMapping(path = "/emps", method = RequestMethod.GET)
    @ResponseBody //向浏览器返回JSON加上这句注解,否则认为返回的是html文件
    public List<Map<String, Object>> getEmps() {
        List<Map<String, Object>> list = new ArrayList<>();

        Map<String, Object> emp = new HashMap<>();
        emp.put("name", "李四");
        emp.put("age", 23);
        emp.put("salary", 8000.00);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name", "王五");
        emp.put("age", 21);
        emp.put("salary", 5000.00);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name", "黄七");
        emp.put("age", 22);
        emp.put("salary", 9000.00);
        list.add(emp);

        return list;
    }






}

