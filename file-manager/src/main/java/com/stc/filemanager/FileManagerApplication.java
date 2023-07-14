package com.stc.filemanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.handler.MappedInterceptor;

import com.stc.filemanager.interceptor.CustomHandlerInterceptor;

@SpringBootApplication
public class FileManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileManagerApplication.class, args);
	}

	@Bean
	@Autowired
	public MappedInterceptor getMappedInterceptor(CustomHandlerInterceptor interceptor) {
		return new MappedInterceptor(new String[] { "/item/create-folder", "/file/create","/file/download/**","/graphql" }, interceptor);
	}
}
