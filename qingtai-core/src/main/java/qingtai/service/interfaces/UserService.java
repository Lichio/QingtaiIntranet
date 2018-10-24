package qingtai.service.interfaces;

import qingtai.pojo.main.User;
import qingtai.pojo.response.RetMsg;

/**
 * qingtai.service.interfaces
 * Created on 2017/10/20
 *
 * @author Lichaojie
 */
public interface UserService {

    /**
     * 根据用户名查找用户信息
     * @param username
     * @return
     */
    User getUserByUsername(String username);

    /**
     *
     * @param username
     * @return
     * @see qingtai.service.interfaces.UserService#getUserByUsername(String)
     */
    @Deprecated
    User getUserOld(String username);

    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     * @return
     */
    RetMsg login(String username,String password);


}
