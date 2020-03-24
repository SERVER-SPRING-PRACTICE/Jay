package com.cadi.boardapi.mapper;

import com.cadi.boardapi.dto.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    //이름과 비밀번호로 조회
    @Select("SELECT userIdx, id, password FROM user WHERE id = #{id} AND password = #{password}")
    User findByNameAndPassword(@Param("id") final String id, @Param("password") final String password);

    //회원 고유 번호로 조회
    @Select("SELECT userIdx, id, password FROM user WHERE userIdx = #{userIdx}")
    User findByUserIdx(@Param("userIdx") final int userIdx);

    // 회원가입
    @Insert("INSERT INTO user(id, password, name, email, phone) VALUES (#{id},#{pw},#{name},#{email},#{phone})")
    void signUp(@Param("id") final String id, @Param("pw") final String pw, @Param("name") final String name, @Param("email") final String email, @Param("phone") final String phone);
}
