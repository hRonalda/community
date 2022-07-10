package com.nowcoder.community.service;

import com.nowcoder.community.dao.AlphaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
//@Scope("prototype") //默认参数是单例，prototype参数表示多个实例（每次访问bean都会产生新的实例）
//但很少这样使用

//@Service("userService")注解是告诉spring，当Spring要创建UserServiceImpl的的实例时，
//bean的名字必须叫做"userService"，这样当Action需要使用UserServiceImpl的的实例时,
// 就可以由Spring创建好的"userService"，然后注入给Action。

public class AlphaService {

    @Autowired   //service依赖于dao
    private AlphaDao alphaDao; //注入了依赖，在进行查询业务时就可以调用
    //

    //查看PostConstruct是否在构造器之后进行使用
    public AlphaService() {
        System.out.println("实例化AlphaService");
    }

    @PostConstruct //让init初始哈方法在构造器之后调用，让容器自动调用
    //增加初始化方法
    public void init() {
        System.out.println("初始化AlphaService");
    }

    @PreDestroy //在销毁对象之前调用它
    public void destroy() {
        System.out.println("销毁AlphaService");
    }

    //模拟查询业务
    public String find() {
        return alphaDao.select(); //返回查询结果
    }
}

