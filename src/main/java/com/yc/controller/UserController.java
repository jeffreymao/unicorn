package com.yc.controller;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yc.entity.UserEntity;
import com.yc.mapper.UserMapper;

@RestController
public class UserController {
	
	private static final Logger logger = LogManager.getLogger(UserController.class);
	
	@Autowired
	private UserMapper userMapper;
	
//	@Autowired
//	private RedisUtils redisUtils;
	
	@RequestMapping("/")
	public String index() {
		logger.info("enter index api :welcome ");
		return "welocome to unicorn system";
	}
	
	@RequestMapping("/getUsers")
	@Cacheable(value="users")
	public List<UserEntity> getUsers() {
		List<UserEntity> users=userMapper.getAll();
		logger.info("enter getUsers api | userList Size :"+users.size());
		return users;
	}
	
    @RequestMapping("/getUser")
    public UserEntity getUser(Long id) {
    	UserEntity user=userMapper.getOne(id);
        return user;
    }
    
    @RequestMapping("/add")
    public void save(@RequestParam(value="userJson",defaultValue="{}") String userJson) {
    	ObjectMapper mapper = new ObjectMapper();
//    	userJson = "{\"userName\":\"landy\",\"passWord\":\"456\",\"userSex\":\"woman\",\"nickName\":\"baby123\"}";
    	System.out.println("add user param :"+userJson);
    	UserEntity user;
		try {
			if (userJson !="{}"){
				user = mapper.readValue(userJson, UserEntity.class);
				userMapper.insert(user);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @RequestMapping(value="update")
    public void update(UserEntity user) {
    	userMapper.update(user);
    }
    
    @RequestMapping(value="/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
    	userMapper.delete(id);
    }
    
    
}