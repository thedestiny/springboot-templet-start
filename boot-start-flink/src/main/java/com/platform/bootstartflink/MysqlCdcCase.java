package com.platform.bootstartflink;

import com.ververica.cdc.connectors.mysql.MySqlSource;
import com.ververica.cdc.connectors.mysql.table.StartupOptions;
import com.ververica.cdc.debezium.DebeziumSourceFunction;
import com.ververica.cdc.debezium.StringDebeziumDeserializationSchema;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
public class MysqlCdcCase {

    public static void main(String[] args) throws Exception {

        //1.创建执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        // 1.1 开启ck
//        env.enableCheckpointing(5000);
//        env.getCheckpointConfig().setAlignmentTimeout(1000);
//        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
//        env.getCheckpointConfig().setMaxConcurrentCheckpoints(1);
//        env.setStateBackend(new FsStateBackend("hafs://hadoop102:8020/cdctest/ck"));

        // 2 通过flinkcdc构建
        DebeziumSourceFunction<String> sourceFunction = MySqlSource.<String>builder()
                .hostname("localhost")
                .port(3306)
                .username("test")
                .password("123456")
                .databaseList("test_01")
                .serverTimeZone("Asia/Shanghai")
                // .tableList("gamll.student")
                .deserializer(new StringDebeziumDeserializationSchema())
                .startupOptions(StartupOptions.initial())
                .build();
        // 3 应用
        DataStreamSource<String> DataStreamSource = env.addSource(sourceFunction);
        // 打印
        DataStreamSource.print();
        System.out.println("执行了打印");
        //4.执行任务
        env.execute("flink_cdc_mysql");

        System.out.println("执行了任务");



    }


}
