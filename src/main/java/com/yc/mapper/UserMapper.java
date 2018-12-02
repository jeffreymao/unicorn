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
		@Result(property = "userName",  column = "userName"),
		@Result(property = "userSex",  column = "user_sex"),
		@Result(property = "nickName", column = "nick_name")
	})
	List<UserEntity> getAll();
	
	@Select("SELECT * FROM users WHERE id = #{id}")
	@Results({
		@Result(property = "userName",  column = "userName"),
		@Result(property = "userSex",  column = "user_sex"),
		@Result(property = "nickName", column = "nick_name")
	})
	UserEntity getOne(Long id);

	@Insert("INSERT INTO users(userName,passWord,user_sex) VALUES(#{userName}, #{passWord}, #{userSex})")
	void insert(UserEntity user);

	@Update("UPDATE users SET userName=#{userName},nick_name=#{nickName} WHERE id =#{id}")
	void update(UserEntity user);

	@Delete("DELETE FROM users WHERE id =#{id}")
	void delete(Long id);
	
	@Select("SELECT gpdm,zqjc FROM usrzqzb1 limit 100")
	@Results({
		@Result(property="gpdm",column="gpdm"),
		@Result(property="zqjc",column="zqjc")
	})
	List<Map<String,String>> getAllCompanys();
	
	

}