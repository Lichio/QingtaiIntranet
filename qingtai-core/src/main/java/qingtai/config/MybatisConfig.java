package qingtai.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.base.Preconditions;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import qingtai.config.db.DynamicDataSource;

/**
 * qingtai.config
 * Created on 2017/10/18
 *
 * @author Lichaojie
 */

/**
 * 在Spring中，处理外部值的最简单方式就是声明属性源（如@PropertySource(value = "classpath:/jdbc.properties")）
 * 并通过Spring的Environment来检索属性
 */
@Configuration
@PropertySource(value = "classpath:jdbc.properties")
@MapperScan("qingtai.mapper")
@EnableTransactionManagement
public class MybatisConfig implements ApplicationContextAware{

    @Autowired
    private Environment env;

    /**
     * 实现了ApplicationContextAware接口的对象会拥有一个ApplicationContext的引用，
     * 这样我们就可以以编程的方式操作ApplicationContext
     */
    private ApplicationContext context;

    /**
     * 数据库配置信息
     */
    private static final String MYBATIS_DB1_URL = "mybatis.db1.url";
    private static final String MYBATIS_DB1_USERNAME = "mybatis.db1.username";
    private static final String MYBATIS_DB1_PASSWORD = "mybatis.db1.password";
    private static final String MYBATIS_DB1_INITIAL_SIZE = "mybatis.db1.initialSize";
    private static final String MYBATIS_DB1_MAX_ACTIVE = "mybatis.db1.maxActive";

    private static final String MYBATIS_DB2_URL = "mybatis.db2.url";
    private static final String MYBATIS_DB2_USERNAME = "mybatis.db2.username";
    private static final String MYBATIS_DB2_PASSWORD = "mybatis.db2.password";
    private static final String MYBATIS_DB2_INITIAL_SIZE = "mybatis.db2.initialSize";
    private static final String MYBATIS_DB2_MAX_ACTIVE = "mybatis.db2.maxActive";
    /**
     * Mappers.mybatis 数据库配置
     */
    // Mappers.mybatis data source
    @Bean(name = "writeDataSource")
    @Qualifier("writeDataSource")
    @Primary
    public DruidDataSource writeDataSource() {

        return createDataSource(MYBATIS_DB1_URL,
                MYBATIS_DB1_USERNAME,
                MYBATIS_DB1_PASSWORD,
                MYBATIS_DB1_MAX_ACTIVE,
                MYBATIS_DB1_INITIAL_SIZE);
    }


    @Bean(name = "readDataSource")
    @Qualifier("readDataSource")
    @Primary
    public DruidDataSource readDataSource() {

        return createDataSource(MYBATIS_DB2_URL,
                MYBATIS_DB2_USERNAME,
                MYBATIS_DB2_PASSWORD,
                MYBATIS_DB2_MAX_ACTIVE,
                MYBATIS_DB2_INITIAL_SIZE);
    }


    @Bean(name = "myDataSource")
    @Qualifier("myDataSource")
    public DynamicDataSource myDataSource(){
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setReadDataSource(readDataSource());
        dynamicDataSource.setWriteDataSource(writeDataSource());

        return dynamicDataSource;
    }


    // Mappers.mybatis session factory
    @Bean(name = "mybatisFactory")
    @Qualifier("mybatisFactory")
    public SqlSessionFactory sqlSessionFactorySso() throws Exception {

        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(myDataSource());//使用之前的datasource
        factoryBean.setMapperLocations(context.getResources("classpath*:mapping/**/main/*.xml"));//配置了mapper的地址
        factoryBean.setConfigLocation(context.getResource("classpath:mybatis-config.xml"));
        return factoryBean.getObject();//一个sqlsessionfactory就可以操作mapper里面定义的操作了
    }

    // Mappers.mybatis transaction manager
    @Bean(name = "mybatisManager")
    @Qualifier("mybatisManager")
    public DataSourceTransactionManager transactionManagerPrimary() {

        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(myDataSource());
        return transactionManager;//配置事物管理器
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }




    public DruidDataSource createDataSource(String MYBATIS_DB_URL,
                                            String MYBATIS_DB_USERNAME,
                                            String MYBATIS_DB_PASSWORD,
                                            String MYBATIS_DB_MAX_ACTIVE,
                                            String MYBATIS_DB_INITIAL_SIZE) {

        final String url = Preconditions.checkNotNull(env.getProperty(MYBATIS_DB_URL));
        final String username = Preconditions.checkNotNull(env.getProperty(MYBATIS_DB_USERNAME));
        final String password = env.getProperty(MYBATIS_DB_PASSWORD);
        final int maxActive = Integer.parseInt(env.getProperty(MYBATIS_DB_MAX_ACTIVE, "200"));
        final int initialSIze = Integer.parseInt(env.getProperty(MYBATIS_DB_INITIAL_SIZE));

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setInitialSize(initialSIze);
        dataSource.setMaxActive(maxActive);

        return dataSource;
    }
}






/**
 * @MapperScan 示例
 * Use this annotation to register MyBatis mapper interfaces when using Java Config.
 * It performs when same work as MapperScannerConfigurer
 * MapperScannerConfigurer 通过自动扫描 将Mapper接口生成代理注入到Spring
 */
//@Configuration
//@MapperScan("org.mybatis.spring.sample.mapper")
//public class AppConfig {
//    @Bean
//    public DataSource dataSource() {
//        return new EmbeddedDatabaseBuilder().addScript("schema.sql").build();
//    }
//
//    @Bean
//    public DataSourceTransactionManager transactionManager() {
//        return new DataSourceTransactionManager(dataSource());
//    }
//
//    @Bean
//    public SqlSessionFactory sqlSessionFactory() throws Exception {
//        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
//        sessionFactory.setDataSource(dataSource());
//        return sessionFactory.getObject();
//    }
//}