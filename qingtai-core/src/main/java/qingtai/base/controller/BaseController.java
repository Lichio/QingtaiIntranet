package qingtai.base.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;

/**
 * qingtai.base.controller
 * Created on 2017/10/19
 *
 * @author Lichaojie
 */
@Controller("BaseController")
public abstract class BaseController {
    Logger logger = Logger.getLogger(this.getClass());

    @PostConstruct
    public void init() {
        logger.info("Controller::" + this.getClass().getName() + "初始化成功!");
    }

    public String getPath(String path) {
        return "view/" + path;
    }
}
