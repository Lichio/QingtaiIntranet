package qingtai.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import qingtai.base.controller.BaseController;
import qingtai.mapper.UserMapper;
import qingtai.pojo.main.User;
import qingtai.pojo.response.RetMsg;
import qingtai.service.interfaces.UserService;
import qingtai.support.utils.RedisShardingUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * qingtai.controller
 * Created on 2017/10/19
 *
 * @author Lichaojie
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController extends BaseController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisShardingUtil redisShardingUtil;
//    @Autowired
//    private DemoService demoService;
//    @Autowired
//    private IMService imService;

    //produces = "application/json;charset=utf-8"
    @RequestMapping(value = "/login.json",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String doLogin(@RequestParam("username") String username,
                          @RequestParam("password") String password){

        RetMsg msg = new RetMsg();

        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            msg.setMsg("用户名或密码不能为空！")
                    .setCode(-1)
                    .setStatus(false);
        }else {
            msg = userService.login(username,password);
        }

        return JSON.toJSONString(msg);
    }

    @RequestMapping(value = "/home")
    //@ResponseBody
    public String home(HttpServletRequest request){
        long time = System.currentTimeMillis();
        User user = new User();
        user.setUsername("kristy" + (time % 1000))
                .setPassword("kristy" + (time % 1000))
                .setGender((byte)(Math.random()*100%2))
                .setAge((int)(Math.random()*100%15 + 10));
        userMapper.insert(user);

        request.getSession().setAttribute(user.getUsername(),user);
        redisShardingUtil.set("key1","value1");
        redisShardingUtil.set("key2","value2");

        return getPath("admin/home");

//        String msg = FileReaderUtil.read("E:\\Java\\Code\\IdeaProjects\\QingtaiIntranet\\qingtai-file\\src\\main\\resources\\template\\email\\simpleTemplate");
//        StringBuffer htmlMsgBuf = new StringBuffer(msg.length() + "<html><body><pre>".length() + "</pre></body></html>".length());
//        htmlMsgBuf.append("<html><body><pre>").append(msg).append("</pre></body></html>");
//        return htmlMsgBuf.toString();
    }

    @RequestMapping(value = "dbTest.json")
    @ResponseBody
    public String dbTest(){
        User user = userMapper.selectByPrimaryKey(7l);
        return JSON.toJSONString(user);
    }

    /* 此部分用作dubbo服务测试
    @RequestMapping(value = "/dubbo")
    @ResponseBody
    public String dubboTest(@RequestParam("name") String name){
        return demoService.sayHello(name);
    }

    @RequestMapping(value = "/message")
    @ResponseBody
    public String messageTest(@RequestParam("from") int from,
                            @RequestParam("to") int to,
                            @RequestParam("content") String content){
        boolean isOk = imService.sendMessage(37L,45L,content);
        if(isOk){
            return "SUCCESS!";
        } else {
            return "ERROR!";
        }
    }
    */


}
