package org.example.service;

import org.example.dto.UserDto;
import org.example.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.vo.UserVo;

import javax.servlet.http.HttpServletRequest;

/**
* @author Mirrol
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2023-07-13 20:18:45
*/
public interface UserService extends IService<User> {

    UserVo register(UserDto userDto);

    User login(UserDto userDto, HttpServletRequest request);
}
