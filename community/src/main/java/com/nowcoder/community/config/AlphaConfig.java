package com.nowcoder.community.config;
//自己写配置类，在配置类中进行bean注解声明，装配第三方的bean

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

@Configuration //表示这是一个配置类而非普通类
public class AlphaConfig {

    @Bean
    //返回的对象将被装配到容器里，bean的名字就是simpleDateFormat
    public SimpleDateFormat simpleDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
}
