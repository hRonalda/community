package com.nowcoder.community.dao;

import com.nowcoder.community.entity.User;
import org.apache.ibatis.annotations.Mapper;

//还要写个注解才能让spring容器装配这个bean
//按理说，dao层可以使用@Repository，这样可以，
//但是MyBatis有另一个注解，@Mapper
@Mapper
public interface UserMapper {
    //声明增删查改的方法,再写上对应的sql的配置文件

    //查询
    User selectById(int id); //根据id查询，参数是id
    User selectByName(String username);
    User selectByEmail(String email);

    int insertUser(User user); //返回插入的行数

    int updateStatus(int id, int status); //返回修改的条数,以id为条件，改的是状态

    int updateHeader(int id, String headerUrl); //更新图像的路径，条件是id，参数是URL

    int updatePassword(int id, String password);
}






















