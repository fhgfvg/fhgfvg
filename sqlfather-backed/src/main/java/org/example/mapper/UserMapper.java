package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author Mirrol
* @description 针对表【user(用户)】的数据库操作Mapper
* @createDate 2023-07-13 20:18:45
* @Entity org.example.entity.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




