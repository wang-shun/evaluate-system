package com.hoperun;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import com.github.pagehelper.PageHelper;

@SpringBootApplication
@EnableTransactionManagement 
@EnableDiscoveryClient
@ServletComponentScan
public class ExamServiceApplication {
    
    private static final Logger log = LoggerFactory.getLogger(ExamServiceApplication.class);
    
    /**
     * 使用fastJson进行json解析
     * @return
     */
    /*@Bean
	public HttpMessageConverters fastJsonHttpMessageConverters() {
		//1.定义HttpMessageConverter
		FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
		//2.定义JsonConfig
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		//3.配置JsonConfig
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
		//4.将JsonConfig注册到HttpMessageConverter
		fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
		HttpMessageConverter<?> converter = fastJsonHttpMessageConverter;
		//5.返回HttpMessageConverter
		return new HttpMessageConverters(converter);
	}*/
    
    public static void main(String[] args) {
        log.info("exam-manage service loader!");
        SpringApplication.run(ExamServiceApplication.class, args);
    }
    
    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
        
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
