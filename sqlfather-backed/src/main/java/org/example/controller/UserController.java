package org.example.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import io.common.UserHolder;
import io.common.annotation.AuthCheck;
import io.common.enums.ErrorCode;
import io.common.exceptions.CustomException;
import io.common.utils.Result;
import io.swagger.annotations.ApiOperation;
import org.example.constant.Constants;
import org.example.dto.UserDto;
import org.example.entity.User;
import org.example.service.UserService;
import org.example.vo.UserVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: ZhaoXing

 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;


    //region 登录相关
    /**
     * 注册
     *
     * @param userDto
     * @return
     */
    @ApiOperation(tags = "注册", value = "接口信息")
    @PostMapping("/register")
    public Result register(@RequestBody UserDto userDto) {
        if (userDto == null)
            throw new CustomException(ErrorCode.PARAMS_ERROR);
        UserVo userVo = userService.register(userDto);
        return new Result().ok(userVo);
    }

    /**
     * 登录
     * @param userDto
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(tags = "登录", value = "登录接口")
    public Result login(@RequestBody UserDto userDto, HttpServletRequest request) {
        if (userDto == null)
            throw new CustomException(ErrorCode.PARAMS_ERROR);
        User user = userService.login(userDto, request);
        return new Result().ok(user);
    }

    /**
     * 查询用户是否登录
     * @return
     */
    @ApiOperation(tags = "查询用户状态", value = "查询用户状态")
    @GetMapping("/search")
    public Result isExisted() {
        User user = UserHolder.getUser();
        if (user != null)
            return new Result<>().ok(user);
        return new Result().error(404);
    }

    /**
     * 注销登录
     * @param request
     * @return
     */
    @ApiOperation(tags = "退出",value = "登出")
    @GetMapping("/layout")
    public Result layout(HttpServletRequest request){
        User user = UserHolder.getUser();
        request.getSession().removeAttribute(Constants.USER_LOGIN);
        UserHolder.removeUser();
        return new Result<>().ok("退出登录",user);
    }
    //endregion

//    region CRUD

    /**
     * 删除
     * @param id
     * @return
     */
    @ApiOperation(tags = "CRUD",value = "删除接口")
    @PostMapping("/delete")
    @AuthCheck(mustRole = Constants.USER_ROLE_ADMIN)
    public Result deleteUser(@RequestParam("userId") Long id){
        boolean removed = userService.removeById(id);
        if(removed)
            return new Result<>().ok("已删除用户");
        return new Result<>().error(ErrorCode.SYSTEM_ERROR,"数据库异常");
    }

    /**
     * 查询用户列表
     * @param user
     * @return
     */
    @ApiOperation(tags = "CRUD",value = "查询用户列表")
    @GetMapping("/list")
    @AuthCheck(mustRole = Constants.USER_ROLE_ADMIN)
    public Result list(User user){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);
        List<User> list = userService.list(queryWrapper);
        List<UserVo> userVoList = list.stream().map((user1) -> {
            UserVo userVo = new UserVo();
            BeanUtil.copyProperties(user1, userVo);
            return userVo;
        }).collect(Collectors.toList());
        return new Result<>().ok(userVoList);
    }

    /**
     * 分页查询用户列表
     * @param user
     * @return
     */
    @ApiOperation(tags = "CRUD",value = "分页查询用户列表")
    @GetMapping("/list/page")
    public Result listByPage(User user){
        int current = Constants.CURRENT;
        int size = Constants.SIZE;
        if(user.getCurrent() > 0)
            current = user.getCurrent();
        if(user.getCurrent() > 0)
            size = user.getSize();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);
//        存放分页参数
        Page<User> page = new Page<>(current, size);
//        进行分页
        Page<User> userPage = userService.page(page, queryWrapper);
//        分页返回dto
        PageDTO<UserVo> userVoPageDTO = new PageDTO<>(userPage.getCurrent(), userPage.getSize(),userPage.getTotal());
//        分页数据
        List<UserVo> userVoList = userPage.getRecords().stream().map(user1 -> {
            UserVo userVo = new UserVo();
            BeanUtil.copyProperties(user1, userVo);
            return userVo;
        }).collect(Collectors.toList());
//        设置分页数据
        userVoPageDTO.setRecords(userVoList);
        return new Result().ok(userVoPageDTO);
    }
//    endregion


}
