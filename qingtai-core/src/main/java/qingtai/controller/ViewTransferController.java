package qingtai.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import qingtai.base.controller.BaseController;

/**
 * qingtai.controller
 * Created on 2017/10/24
 *
 * @author Lichaojie
 */

@Controller
@RequestMapping(value = "/view")
public class ViewTransferController extends BaseController {

    @RequestMapping(value = "/upload")
    public String upload(){
        return getPath("test/upload");
    }
}
