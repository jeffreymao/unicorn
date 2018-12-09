package com.yc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan("com.yc.mapper")
@EnableCaching
public class UnicornApplication 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(UnicornApplication.class, args);
    }
}
