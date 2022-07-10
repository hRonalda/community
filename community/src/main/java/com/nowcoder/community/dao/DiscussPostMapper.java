package com.nowcoder.community.dao;

import com.nowcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper //打上注解，才能被容器扫描接口，才能去自动实现装配它
public interface DiscussPostMapper {
    //做查询的功能，在这里声明查询的方法，在配置文件里写出与之有关的SQL
    //这里做的是分页查询帖子的功能，查到的是多条数据，所以返回的是一个集合，集合里装的是帖子的对象
    //参数介绍：userId（帖子表discuss_post里的字段），但是首页上查询是不需要userId的，
    //将所有人的帖子都能查到；用户个人主页会有一个"我发布的帖子"的功能会用到。可以将userId设置为0或者非0
    //offset:每页起始行的行号   limit：每一页最多显示多少条数据
    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);

    //查找帖子的行数
    //@Parama注解用于给参数取别名
    //如果只有一个参数，并且在<if>中使用，则必须加上别名
    int selectDiscussPostRows(@Param("userId") int userId);
}

