/*
 * 文 件 名:  PaperManageApplication.java
 * 版    权:  Copyright 2000-2009 Huawei Tech. Co. Ltd. All Rights Reserved.
 * 描    述:  <描述>
 * 修 改 人:  mWX576942
 * 修改时间:  2018年7月16日
 * 修改版本:  <跟踪单号>
 */
package com.hoperun;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * <功能详细描述>
 * 
 * @author  mWX576942
 * @version  [版本号, 2018年7月16日]
 * @since  [云服务/模块版本]
 */
@SpringBootApplication
@MapperScan("com.hoperun.dao")
@EnableDiscoveryClient
public class PaperManageApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(PaperManageApplication.class, args);
    }
    @Bean
    @LoadBalanced
     RestTemplate restTemplate() {
        return new RestTemplate();
        
    }
}
