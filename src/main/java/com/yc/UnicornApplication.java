package com.yc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yc.mapper")
public class UnicornApplication 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(UnicornApplication.class, args);
    }
}
