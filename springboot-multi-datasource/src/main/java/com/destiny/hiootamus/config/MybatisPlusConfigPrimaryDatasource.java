package com.destiny.hiootamus.config;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @Description 新数据库连接配置
 * @Author destiny
 * @Date 2021-05-12 4:39 PM
 */

@Slf4j
@Configuration
@MapperScan(basePackages = "com.destiny.hiootamus.mapper.primary", sqlSessionFactoryRef = "priSqlSessionFactory")
public class MybatisPlusConfigPrimaryDatasource {

    @Autowired
    private MybatisPlusInterceptor plusInterceptor;


    @Bean(name = "priDatasource")//注入到这个容器
    @ConfigurationProperties(prefix = "spring.datasource.primary")//表示取application.properties配置文件中的前缀
    @Primary
    // primary是设置优先，因为有多个数据源，在没有明确指定用哪个的情况下，会用带有primary的，这个注解必须有一个数据源要添加
    public DataSource priDataSource() {
        DataSource build = DataSourceBuilder.create().build();
        log.info(" start datasource !" );
        return build;
    }

    // pre
    @Bean("priSqlSessionFactory")
    public SqlSessionFactory priSqlSessionFactory(@Qualifier("priDatasource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCacheEnabled(false);
        configuration.setDefaultExecutorType(ExecutorType.BATCH);

        sqlSessionFactory.setConfiguration(configuration);
        sqlSessionFactory.setTypeAliasesPackage("com.destiny.hiootamus.entity.primary");
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().
                getResources("classpath*:mapper/primary/*.xml"));
        sqlSessionFactory.setPlugins(plusInterceptor);
        sqlSessionFactory.setGlobalConfig(new GlobalConfig().setBanner(true));
        return sqlSessionFactory.getObject();
    }

    @Bean(name = "priTransactionManager")
    public DataSourceTransactionManager priTransactionManager(@Qualifier("priDatasource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "priSqlSessionTemplate")
    public SqlSessionTemplate priSqlSessionTemplate(@Qualifier("priSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "priTransactionTemplate")
    public TransactionTemplate priTransactionTemplate(@Qualifier("priTransactionManager") DataSourceTransactionManager manager) {
        return new TransactionTemplate(manager);
    }

//    @Bean(name = "priTransactionInterceptor")
//    public TransactionInterceptor transactionInterceptor(@Qualifier("priTransactionManager") DataSourceTransactionManager manager) {
//
//        TransactionInterceptor interceptor = new TransactionInterceptor();
//        interceptor.setTransactionManager(manager);
//
//        Properties properties = new Properties();
//        properties.setProperty("delete*", "PROPAGATION_REQUIRED");
//        properties.setProperty("remove*", "PROPAGATION_REQUIRED");
//        properties.setProperty("update*", "PROPAGATION_REQUIRED");
//        properties.setProperty("get*", "PROPAGATION_REQUIRED,-Exception,readOnly");
//        properties.setProperty("query*", "PROPAGATION_REQUIRED,-Exception,readOnly");
//        properties.setProperty("select*", "PROPAGATION_REQUIRED,-Exception,readOnly");
//        interceptor.setTransactionAttributes(properties);
//
//        return interceptor;
//    }


}
