package com.hoperun;

import java.util.Properties;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.github.pagehelper.PageHelper;

/**
 * 
 * @author mWX576942
 * @date 2018/07/25
 */
@MapperScan("com.hoperun.dao")
@EnableDiscoveryClient
@SpringBootApplication
public class SubjectserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SubjectserviceApplication.class, args);
	}
	
	//配置mybatis的分页插件pageHelper
    @Bean
    public PageHelper pageHelper(){
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("offsetAsPageNum","true");
        properties.setProperty("rowBoundsWithCount","true");
        properties.setProperty("reasonable","true");
        properties.setProperty("dialect","mysql");    //配置mysql数据库的方言
        pageHelper.setProperties(properties);
        return pageHelper;
    }
}
