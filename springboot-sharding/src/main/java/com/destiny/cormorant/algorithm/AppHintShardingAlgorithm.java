package com.destiny.cormorant.algorithm;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.hint.HintManager;
import org.apache.shardingsphere.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.hint.HintShardingValue;

import java.util.Collection;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2021-08-09 2:09 PM
 */
@Slf4j
public class AppHintShardingAlgorithm implements HintShardingAlgorithm<Long> {


    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, HintShardingValue<Long> shardingValue) {


        // Hint分片策略必须要使用 HintManager工具类
        HintManager hintManager = HintManager.getInstance();
        //
        hintManager.addDatabaseShardingValue("t_order", 0);
        hintManager.addTableShardingValue("t_order", 1);

        // 直接指定对应具体的数据库
        // hintManager.setDatabaseShardingValue(1);
        //在读写分离数据库中，Hint 可以强制读主库（主从复制是存在一定延时，但在业务场景中，可能更需要保证数据的实时性）
        // hintManager.setMasterRouteOnly();

        return null;
    }
}
