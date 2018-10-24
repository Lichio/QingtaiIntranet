package qingtai.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.qingtai.interfaces.test.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import qingtai.service.interfaces.AdminService;

/**
 * qingtai.service.impl
 * Created on 2017/11/1
 *
 * @author Lichaojie
 */
@Service
public class AdminServiceImpl implements AdminService {

//	@Autowired
//	private DemoService demoService;

	public String dubboTest(String name){
		return name;
	}
}
