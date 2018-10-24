package qingtai.service.impl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qingtai.mapper.UserMapper;
import qingtai.pojo.main.User;
import qingtai.pojo.response.RetMsg;
import qingtai.service.interfaces.UserService;

/**
 * qingtai.service.impl
 * Created on 2017/10/19
 *
 * @author Lichaojie
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByUsername(String username){
        return userMapper.selectByUsername(username);
    }

    @Override
    public User getUserOld(String username){
        return new User();
    }

    @Override
    public RetMsg login(String username,String password){

        RetMsg msg = new RetMsg();

        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        try {
            subject.login(token);
            msg.setStatus(true)
                    .setCode(0)
                    .setMsg("success");
        } catch (AuthenticationException exception) {
            exception.printStackTrace();
            msg.setStatus(false)
                    .setMsg("登陆失败," + exception.getMessage())
                    .setCode(-1);
        }

        return msg;
    }
}
