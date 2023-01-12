package com.destiny.cormorant.algorithm;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.Collection;

/**
 * @Description 精确分片算法
 * @Author destiny
 * @Date 2021-08-07 5:00 PM
 */
@Slf4j
public class AppPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

    /**
     * availableTargetNames 数据库名或者数据库表名称集合
     * */
    @Override
    public String doSharding(Collection<String> tables, PreciseShardingValue<Long> sharding) {

        log.info("数据库列名 {}, 逻辑表名 {} 数据库分片值 {}",sharding.getColumnName(),sharding.getLogicTableName(),sharding.getValue());
        long l = sharding.getValue() % tables.size() + 1;
        for(String node : tables){
            if(node.endsWith(l+"")){
               return node;
            }
        }
        throw  new UnsupportedOperationException("未找到合适的数据库表");
    }
}
