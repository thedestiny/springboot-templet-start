package com.destiny.squirrel.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;


import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

// spring容器启动完成之后，就会紧接着执行这个接口实现类的run方法。
// 被监听的数据库必须开启 binlog 的设置，程序模拟 slave 进行数据变更的监听
@Slf4j
@Component
public class MysqlBinLogListener implements ApplicationRunner {


    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 异步执行任务
        new Thread(this::connectMysqlBinLog).start();
    }

    public void connectMysqlBinLog() {

        //自己MySQL的信息。host，port，username，password
        BinaryLogClient client = new BinaryLogClient("localhost", 3306, "root", "123456");
        // 设置监听的server_id 必须是唯一的，该server_id 作为从节点来接受 mysql 数据
        client.setServerId(100);
        client.setKeepAlive(true); // 保持连接
        client.setKeepAliveInterval(10 *1000); // 心跳包发送频率
        client.setKeepAliveConnectTimeout(5 * 1000); // 心跳发送超时设置

        // https://blog.csdn.net/m0_69424697/article/details/124947861
        client.registerEventListener(event -> {
            // 数据事件 注册事件监听器，对不同类型的事件做出响应。
            EventData data = event.getData();
            if (data instanceof TableMapEventData) {
                TableMapEventData tableMapEventData = (TableMapEventData) data;
                long tableId = tableMapEventData.getTableId();
                // 监听到的数据库表和数据库
                String tableName = tableMapEventData.getTable();
                String database = tableMapEventData.getDatabase();
                log.info("database {}  tableId {} tableName {}", database, tableId, tableName);
            }
            // 对UpdateRowsEventData、WriteRowsEventData、DeleteRowsEventData类型的事件进行输出日志
            // 数据 保存 更新 删除
            if (data instanceof WriteRowsEventData) {
                long tableId = ((WriteRowsEventData) data).getTableId();
                log.info("insert tableId {} data {}", tableId, data.toString());
                List<Serializable[]> rows = ((WriteRowsEventData) data).getRows();
                for (Serializable[] row : rows) {
                    log.info("insert data row is {}", Arrays.toString(row));
                }

            }
            if (data instanceof UpdateRowsEventData) {
                long tableId = ((UpdateRowsEventData) data).getTableId();
                log.info("update tableId {} data {}", tableId, data.toString());
            }

            if (data instanceof DeleteRowsEventData) {
                long tableId = ((DeleteRowsEventData) data).getTableId();
                log.info("delete tableId {} data {}", tableId, data.toString());
            }
        });

        try {
            client.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

        MysqlBinLogListener listener = new MysqlBinLogListener();
        listener.connectMysqlBinLog();

    }

}
