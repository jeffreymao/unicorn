package com.yc.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.yc.entity.UserEntity;

public interface UserMapper {
	
	@Select("SELECT * FROM users")
	@Results({
		@Result(property = "userName",  column = "user_name"),
		@Result(property = "phoneNum",  column = "phone_num"),
	})
	List<UserEntity> getAll();
	
	@Select("SELECT * FROM users WHERE id = #{id}")
	@Results({
		@Result(property = "userName",  column = "user_name"),
		@Result(property = "phoneNum",  column = "phone_num"),
	})
	UserEntity getOne(Long id);

	@Insert("INSERT INTO users(user_name,pass_word,phone_num,ctime,utime) VALUES(#{userName}, #{passWord}, #{phoneNum},#{ctime},#{utime)")
	void insert(UserEntity user);

	@Update("UPDATE users SET userName=#{userName} WHERE id =#{id}")
	void update(UserEntity user);

	@Delete("DELETE FROM users WHERE id =#{id}")
	void delete(Long id);

}