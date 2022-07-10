package com.nowcoder.community.dao;

import org.springframework.stereotype.Repository;

//@Repository
@Repository("alphaHibernate")
//bean的默认名为类名首字母小写，但是也可以自定义，如括号里面的alphaHibernate
//之后可以通过名字强制返回该bean？？
//@Repository(value="userDao")注解是告诉Spring，让Spring创建一个名字叫“userDao”的UserDaoImpl实例。
//当Service需要使用Spring创建的名字叫“userDao”的UserDaoImpl实例时，就可以使用

public class AlphaDaoHibernateImpl implements AlphaDao{
    @Override
    public String select() {
        return "Hibernate";
    }
}
