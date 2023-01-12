package com.destiny.hiootamus.config;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;


/**
 * @Description 旧数据库连接配置
 * @Author destiny
 * @Date 2021-05-12 4:39 PM
 */

@Slf4j
@Configuration
@MapperScan(basePackages = "com.destiny.hiootamus.mapper.second", sqlSessionFactoryRef = "secSqlSessionFactory")
public class MybatisPlusConfigSecondDatasource {


    @Autowired
    private MybatisPlusInterceptor plusInterceptor;

    @Bean(name="secDatasource")//注入到这个容器
    @ConfigurationProperties(prefix="spring.datasource.second")//表示取application.properties配置文件中的前缀
    // primary是设置优先，因为有多个数据源，在没有明确指定用哪个的情况下，会用带有primary的，这个注解必须有一个数据源要添加
    public DataSource secDataSource() {
        return DataSourceBuilder.create().build();
    }

    // sec
    @Bean("secSqlSessionFactory")
    public SqlSessionFactory secSqlSessionFactory(@Qualifier("secDatasource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCacheEnabled(false);
        sqlSessionFactory.setConfiguration(configuration);

        sqlSessionFactory.setTypeAliasesPackage("com.destiny.hiootamus.entity.second");
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().
                getResources("classpath*:mapper/second/*.xml"));
        sqlSessionFactory.setPlugins(plusInterceptor);
        sqlSessionFactory.setGlobalConfig(new GlobalConfig().setBanner(false));
        return sqlSessionFactory.getObject();
    }

    @Bean(name = "secTransactionManager")
    public DataSourceTransactionManager secTransactionManager(@Qualifier("secDatasource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "secSqlSessionTemplate")
    public SqlSessionTemplate secSqlSessionTemplate(@Qualifier("secSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "secTransactionTemplate")
    public TransactionTemplate secTransactionTemplate(@Qualifier("secTransactionManager") DataSourceTransactionManager manager){
        return new TransactionTemplate(manager);
    }



}
