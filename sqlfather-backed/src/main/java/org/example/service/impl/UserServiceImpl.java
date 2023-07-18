package org.example.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.common.enums.ErrorCode;
import io.common.exceptions.CustomException;
import org.example.constant.Constants;
import org.example.dto.UserDto;
import org.example.entity.User;
import org.example.mapper.UserMapper;
import org.example.service.UserService;
import org.example.vo.UserVo;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Mirrol
 * @description 针对表【user(用户)】的数据库操作Service实现
 * @createDate 2023-07-13 20:18:45
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    @Resource
    private UserMapper userMapper;

    private final String SALT = "zx";

    /**
     * 注册
     *
     * @param userDto
     * @return
     */
    @Override
    public UserVo register(UserDto userDto) {
        String useraccount = userDto.getUseraccount();
        String userpassword = userDto.getUserpassword();
        if (StrUtil.hasEmpty(useraccount, userpassword, userDto.getUsername(), userDto.getCheckpassword()))
            throw new CustomException(ErrorCode.PARAMS_ERROR, "输入参数不能为空");
//        两次输入不一致
        if (!(userpassword.equals(userDto.getCheckpassword())))
            throw new CustomException(ErrorCode.PARAMS_ERROR, "两次密码输入不一致");
        Long count = userMapper.selectCount(new QueryWrapper<User>().eq("userAccount", useraccount));
        if (count > 0)
            throw new CustomException(ErrorCode.PARAMS_ERROR, "该用户已注册");

//        开始注册
        UserVo userVo;
        synchronized (useraccount.intern()) {
            User user = new User();
            user.setUsername(userDto.getUsername());
            user.setUseraccount(useraccount);
//        加密
            String password = DigestUtils.md5DigestAsHex((userpassword + SALT).getBytes());
            user.setUserpassword(password);
            boolean saved = this.save(user);
            if (!saved)
                throw new CustomException(ErrorCode.SYSTEM_ERROR, "数据库异常");
            userVo = new UserVo();
            BeanUtil.copyProperties(user, userVo);
        }
        return userVo;
    }

    /**
     * 登录
     *
     * @param userDto
     * @param request
     * @return
     */
    @Override
    public User login(UserDto userDto, HttpServletRequest request) {
        String useraccount = userDto.getUseraccount();
        String userpassword = userDto.getUserpassword();
        if (StrUtil.hasEmpty(useraccount, userpassword))
            throw new CustomException(ErrorCode.PARAMS_ERROR, "输入参数不能为空");
        User user = this.getOne(new QueryWrapper<User>()
                .eq("userAccount", useraccount)
                .eq("userPassword", DigestUtils.md5DigestAsHex((userpassword + SALT).getBytes()))
                .last("LIMIT 1"));
        if (user == null) {
            throw new CustomException(ErrorCode.SYSTEM_ERROR, "用户未注册");
        }
//        将用户保存在session中
        request.getSession().setAttribute(Constants.USER_LOGIN,user);
        return user;
    }
}




