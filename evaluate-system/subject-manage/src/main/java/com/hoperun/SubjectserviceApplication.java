package com.hoperun;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@MapperScan("com.hoperun.dao")
@EnableDiscoveryClient
@SpringBootApplication
public class SubjectserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SubjectserviceApplication.class, args);
	}
}
