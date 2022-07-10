package com.nowcoder.community.controller;

import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.Page;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.DiscussPostService;
import com.nowcoder.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller //此处省略了访问路径
public class HomeController {

    @Autowired
    private DiscussPostService discussPostService;
    @Autowired
    private UserService userService; //通过userId查到user详细的信息

    //增加处理请求的方法
    @RequestMapping(path = "/index", method = RequestMethod.GET) //定义方法的访问路径和请求方式
    //这个方法响应的是网页，所以不用写@ResponseBody

    //此处返回的String是视图的名字，方法名是getIndexPage;通过model携带数据给模板
    public String getIndexPage(Model model, Page page) {
        //服务器设置一些值
        //Spring MVC中，会自动将page装入到model中，所以在当前的方法里，可以直接调用page，也可以通过model进行部署
        //即：方法调用之前，SpringMVC会自动实例化Model和Page，并将Page注入到Model中。
        //所以在Thymeleaf中可以直接访问Page对象中的数据（即不需model.addAttribute)
        page.setRows(discussPostService.findDiscussPostRows(0));//总行数,首页userId为0
        page.setPath("/index"); //当前的访问路径,页面上就可复用该路径

        List<DiscussPost> list = discussPostService.findDiscussPosts(0, page.getOffset(), page.getLimit()); //查找首页的前十条
        //在主页查询所有用户的，userId=0；第一页从0开始：offset=0；先写死之后再变为动态的
        //但是这样查到的数据是不完整的，具体就是对象DiscussPost的userId只是id而不是用户名，但我们需要用户名在页面上展现

        //方式：遍历集合，针对每一个DiscussPost，根据userId查到User，将这些数据放到一个新的集合中，然后返回给页面
        //新建一个集合，装的是能够封装DiscussPost和User对象的对象，可以写一个类/数组/Map
        List<Map<String, Object>> discussPosts = new ArrayList<>(); //实例化。key是String，value是Object

        //discussPosts的值需要遍历list得到
        if (list != null) { //list不为空才遍历。每次遍历，都需把结果装进Map里面
            for (DiscussPost post : list) {
                Map<String, Object> map = new HashMap<>();
                /*
                往map里面装东西
                 */
                map.put("post", post); //先装帖子,其次装User（调用userService将其查出来，userId从post中找到）
                User user = userService.findUserById(post.getUserId()); //找到以后查询得到对应的user的完整数据
                map.put("user", user); //将user装进map中

                discussPosts.add(map); //每一次都要将map装进List
            }
        }
        //将最终要给页面展现的结果装进model（页面才能看懂得到）
        model.addAttribute("discussPosts", discussPosts); //给数据取名为discussPosts,值是集合discussPosts
        return "/index"; //最终返回的是模板的路径（templates下的index.html
    }
}
