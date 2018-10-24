package qingtai.config.db;

/**
 * qingtai.config.db
 * Created on 2018/2/1
 *
 * @author Lichaojie
 */
public class DynamicDataSourceHolder {
	private static final ThreadLocal<DynamicDataSourceGlobal> holder = new ThreadLocal<DynamicDataSourceGlobal>();

	public static void putDataSource(DynamicDataSourceGlobal dataSource){
		holder.set(dataSource);
	}

	public static DynamicDataSourceGlobal getDataSource(){
		return holder.get();
	}

	public static void clearDataSource() {
		holder.remove();
	}

}
