package com.llxk.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.llxk.reggie.common.R;
import com.llxk.reggie.entity.User;
import com.llxk.reggie.service.UserService;
import com.llxk.reggie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * ClassName: UserController
 * Package: com.llxk.reggie.controller
 *
 * @author 庐陵小康
 * @version 1.0
 * @Desc
 * @Date 2023/4/21 16:07
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 发送手机短信验证码
     * @param user
     * @param session
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
        //获取手机号
        String phone = user.getPhone();

        if(StringUtils.isNotEmpty(phone)){
            //生成随机验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code={}", code);

            //调用阿里云提供的短信服务API完成发送短信
            //SMSUtils.sendMessage("瑞吉外卖", "", phone, code);

            //将生成的验证码保存到Session
            session.setAttribute(phone, code);

            return R.success("手机短信验证码发送成功");
        }

        return R.error("短信发送失败");

    }

    /**
     * 移动端用户登陆
     * @param map
     * @param session
     * @return
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session){
        //获取手机号
        String phone = (String) map.get("phone");

        //获取验证码
        String code = (String) map.get("code");

        //从Session中获取保存的验证码
        String codeInSession = (String) session.getAttribute(phone);

        //比对验证码
        if(codeInSession != null && codeInSession.equals(code)){
            //比对成功，登陆成功

            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone, phone);
            User user = userService.getOne(queryWrapper);

            //判断当前手机号对应的用户是否为新用户，如果是新用户就自动完成注册
            if(user == null){
                user = new User();
                user.setPhone(phone);
                userService.save(user);
            }
            session.setAttribute("user", user.getId());
            return R.success(user);
        }

        return R.error("登陆失败");
    }






}
