package qingtai.annotation.db;

import org.springframework.stereotype.Component;
import qingtai.config.db.DynamicDataSourceGlobal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * qingtai.config.db
 * Created on 2018/2/1
 *
 * @author Lichaojie
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Component
public @interface DataSource {
	public DynamicDataSourceGlobal value() default DynamicDataSourceGlobal.READ;
}
