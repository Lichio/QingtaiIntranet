package qingtai.config.db;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import qingtai.annotation.db.DataSource;

import java.lang.reflect.Method;

/**
 * qingtai.config.db
 * Created on 2018/2/1
 *
 * @author Lichaojie
 */
@Aspect
@Component
public class DynamicDataSourceAspect {

	Logger logger = Logger.getLogger(DynamicDataSourceAspect.class);

	@Pointcut("execution(* qingtai.mapper..*.*(..))")
	//@Pointcut("@annotation(DataSource)")
	public void pointCut(){}

	@Before(value = "pointCut()")
	public void before(JoinPoint point)
	{
		logger.info("pointcut");

		Object target = point.getTarget();
		String methodName = point.getSignature().getName();
		Class<?>[] clazz = target.getClass().getInterfaces();
		Class<?>[] parameterTypes = ((MethodSignature) point.getSignature()).getMethod().getParameterTypes();
		try {
			Method method = clazz[0].getMethod(methodName, parameterTypes);
			if (method != null && method.isAnnotationPresent(DataSource.class)) {
				DataSource data = method.getAnnotation(DataSource.class);
				DynamicDataSourceHolder.putDataSource(data.value());
				logger.info("data.value : " + data.value());
			}
		} catch (Exception e) {
			logger.info(String.format("Choose DataSource error, method:%s, msg:%s", methodName, e.getMessage()));
		}
	}

	@After("pointCut()")
	public void after(JoinPoint point) {
		DynamicDataSourceHolder.clearDataSource();
	}

}
