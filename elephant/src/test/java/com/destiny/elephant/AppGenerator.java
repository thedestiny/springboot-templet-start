package com.destiny.elephant;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import net.bytebuddy.implementation.auxiliary.AuxiliaryType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppGenerator {

    private static Logger logger = LoggerFactory.getLogger(AppGenerator.class);

    public static void main(String[] args) {


        String packageName = "cn.saytime";
        boolean serviceNameStartWithI = false;
        //auth -> UserService, 设置成true: auth -> IUserService
        generateByTables(serviceNameStartWithI, packageName, "saytime", "test", "user");


    }

    private static void generateByTables(boolean serviceNameStartWithI, String packageName, String author, String database, String... tableNames) {
        GlobalConfig config = new GlobalConfig();
        String dbUrl = "jdbc:mysql://127.0.0.1:3306/" + database + "?useUnicode=true&characterEncoding=utf8&autoReconnect=true&useSSL=false";
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL).setUrl(dbUrl).setUsername("root").setPassword("root").setDriverName("com.mysql.jdbc.Driver");
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setCapitalMode(true)
                .setEntityLombokModel(false)
                .setDbColumnUnderline(true)
                .setNaming(AuxiliaryType.NamingStrategy.underline_to_camel).setSuperMapperClass("cn.saytime.mapper.BaseMapper")
                .setInclude(tableNames);
        //修改替换成你需要的表名，多个表名传数组
        config.setActiveRecord(false) .setAuthor(author)
                .setOutputDir("e:\\codeGen")
                .setFileOverride(true) .setEnableCache(false);
        if (!serviceNameStartWithI) { config.setServiceName("%sService"); }
        new AutoGenerator().setGlobalConfig(config) .setDataSource(dataSourceConfig) .setStrategy(strategyConfig) .setPackageInfo( new PackageConfig() .setParent(packageName) .setController("web") .setEntity("model") .setMapper("mapper") .setService("service") .setServiceImpl("service.impl") .setXml("mybatis.mappers") ).execute(); }


    }
