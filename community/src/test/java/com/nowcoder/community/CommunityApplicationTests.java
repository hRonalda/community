package com.nowcoder.community;

import com.nowcoder.community.dao.AlphaDao;
import com.nowcoder.community.service.AlphaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
//一会运行的测试代码就是以CommunityApplication为配置类

//IOC的核心是容器，而容器是被自动访问的。哪个类想得到Spring容器，那就去得到接口
	//如下：
public class CommunityApplicationTests implements ApplicationContextAware {

	private ApplicationContext applicationContext; //创建一个成员变量用于记录容器

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		//传入了一个参数ApplicationContext，它就是Spring容器
		this.applicationContext = applicationContext;
	}

	@Test
	public void testApplicationContext() {
		System.out.println(applicationContext);

		AlphaDao alphaDao = applicationContext.getBean(AlphaDao.class); //如果有多个选择进行运行
		//例如，
		System.out.println(alphaDao.select());

		alphaDao = applicationContext.getBean("alphaHibernate", AlphaDao.class);
													//将得到的object转换成AlphaDao
		System.out.println(alphaDao.select());
	}

	@Test
	public void testBeanManagement() {
		AlphaService alphaService = applicationContext.getBean(AlphaService.class); //按类型来获取
		System.out.println(alphaService); //查看这个bean是否存在（是否能够实例化这个bean），结果显示为：com.nowcoder.community.service.AlphaService@XXXXXXX

		//被Spring容器管理的bean默认是单个使用单例的
		alphaService = applicationContext.getBean(AlphaService.class); //按类型来获取
		System.out.println(alphaService);
	}

	@Test //还是较为笨拙
	public void testBeanConfig() {
		SimpleDateFormat simpleDateFormat =
				applicationContext.getBean(SimpleDateFormat.class);
		System.out.println(simpleDateFormat.format(new Date())); //结果是当前日期

	}

	@Autowired //注解也可以通过类的构造器来注入，但通常都像这样注入在属性之前
	//@Qualifier("alphaHibernate")
	private AlphaDao alphaDao;//设置成员变量，希望将AlphaDao注入一个属性

	//同理，获取AlphaService和SimpleDateFormat也可如此做
	@Autowired
	private AlphaService alphaService;

	@Autowired
	private SimpleDateFormat simpleDateFormat;

	@Test
	public void testDI() { //DI(Dependency Injection)依赖注入
		System.out.println(alphaDao);

		System.out.println(alphaService);
		System.out.println(simpleDateFormat);
	}
}


/*
由controller来处理浏览器的请求，它会调用业务组件service，业务组件会调用dao去访问数据库，
controller与service与dao互相依赖，它们的依赖就可以使用依赖注入来实现

@Controller控制器（注入服务）
@Service服务（注入dao)
@Repository dao(实现dao访问)
@component （把普通pojo实例化到spring容器中
，相当于配置文件中的<bean id="" class=""/>）

* */


















