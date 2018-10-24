package qingtai.config.db;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.HashMap;
import java.util.Map;

/**
 * qingtai.config.db
 * Created on 2018/2/1
 *
 * @author Lichaojie
 *
 *
 * ********************配置动态数据源********************
 * 1. 扩展AbstractRoutingDataSource类；
 * 2. 重写afterPropertiesSet方法，将数据源信息以Map的形式存储
 * 3. 重写determineCurrentLookupKey方法，返回运行过程中我们需要的的数据源对应的key值
 *
 * 缺点：不能动态增加数据源
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	Logger logger = Logger.getLogger(DynamicDataSource.class);

	private Object writeDataSource; //写数据源

	private Object readDataSource; //读数据源

	@Override
	public void afterPropertiesSet() {
		if (this.writeDataSource == null) {
			throw new IllegalArgumentException("Property 'writeDataSource' is required");
		}
		setDefaultTargetDataSource(writeDataSource);
		Map<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put(DynamicDataSourceGlobal.WRITE.name(), writeDataSource);
		if(readDataSource != null) {
			targetDataSources.put(DynamicDataSourceGlobal.READ.name(), readDataSource);
		}
		setTargetDataSources(targetDataSources);
		super.afterPropertiesSet();
	}

	/**
	 * AbstractRoutingDataSource的getConnection()的方法执行的时候，
	 * 先调用determineTargetDataSource()方法返回DataSource再执行getConnection()
	 * @return
	 */
	@Override
	protected Object determineCurrentLookupKey() {

		DynamicDataSourceGlobal dynamicDataSourceGlobal = DynamicDataSourceHolder.getDataSource();

		if(dynamicDataSourceGlobal == null
				|| dynamicDataSourceGlobal == DynamicDataSourceGlobal.WRITE) {
			logger.info("DynamicDataSourceGlobal.WRITE");
			return DynamicDataSourceGlobal.WRITE.name();
		}

		logger.info("DynamicDataSourceGlobal.READ");
		return DynamicDataSourceGlobal.READ.name();
	}

	public void setWriteDataSource(Object writeDataSource) {
		this.writeDataSource = writeDataSource;
	}

	public Object getWriteDataSource() {
		return writeDataSource;
	}

	public Object getReadDataSource() {
		return readDataSource;
	}

	public void setReadDataSource(Object readDataSource) {
		this.readDataSource = readDataSource;
	}
}
