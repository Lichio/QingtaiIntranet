package qingtai.service.interfaces;

import org.qingtai.interfaces.test.DemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * qingtai.service.interfaces
 * Created on 2017/10/31
 *
 * @author Lichaojie
 */
public class DubboTest {

	/**
	 * 测试dubbo服务是否生效
	 */
	public static void main(String[] args){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:/dubbo-consumer.xml");
		context.start();
		DemoService demoService = (DemoService) context.getBean("demoService"); // 获取远程服务代理
		String hello = demoService.sayHello("world"); // 执行远程方法
		System.out.println(hello);
	}

}
