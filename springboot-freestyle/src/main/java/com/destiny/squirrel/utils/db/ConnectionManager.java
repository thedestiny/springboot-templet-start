package com.destiny.squirrel.utils.db;

import com.alibaba.druid.pool.DruidDataSource;
import com.destiny.squirrel.exception.DataAccessException;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Created by xieyue on 2016/6/15.
 */
public class ConnectionManager {

    private static DruidDataSource dataSource = new DruidDataSource();

    private static String driver = "com.mysql.jdbc.Driver";
    /* admin 老库 */
    private static String url = "jdbc:mysql://127.0.0.1:3306/springboot_example?useUnicode=true&characterEncoding=utf-8&useSSL=true";
    /* admin 新库 */
    // private static String url = "jdbc:mysql://123.59.53.140:3506/aceadmin?useUnicode=true&characterEncoding=utf-8&useSSL=true";
    private static String username = "root";
    private static String password = "Myroot123!";
    private static Integer initsize = 1;
    private static Integer maxsize = 1;
    private static Integer maxwait = 5000;
    private static Integer minidle = 0;


    static {

        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        dataSource.setInitialSize(initsize);
        dataSource.setMaxActive(maxsize);
        dataSource.setMaxWait(maxwait);
        dataSource.setMinIdle(minidle);
    }

    // import javax.sql package
    public static DataSource getDataSource() {
        return dataSource;
    }


    public static Connection getConnection() throws DataAccessException {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new DataAccessException("数据库连接异常", e);
        }
    }

    public static void closeConnection(Connection connection) throws DataAccessException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DataAccessException("数据库关闭异常", e);
        }
    }


    public static void main(String[] args) throws Exception {
	
	    Connection connection = getConnection();
	    
	    Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	
	
    }

}
