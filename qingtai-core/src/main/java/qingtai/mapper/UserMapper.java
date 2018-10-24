package qingtai.mapper;

import org.springframework.stereotype.Repository;
import qingtai.annotation.db.DataSource;
import qingtai.base.mapper.BaseMapper;
import qingtai.config.db.DynamicDataSourceGlobal;
import qingtai.pojo.main.User;


/**
 * qingtai.mapper
 * Created on 2017/10/19
 *
 * @author Lichaojie
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    @DataSource(value = DynamicDataSourceGlobal.READ)
    User selectByUsername(String username);

    /**
     * 根据用户名&密码查找用户
     * @param username
     * @param password
     * @return
     */
    User selectByParam(String username,String password);


}
