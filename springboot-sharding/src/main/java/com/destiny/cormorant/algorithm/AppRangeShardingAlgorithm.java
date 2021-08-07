package com.destiny.cormorant.algorithm;

import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.Collection;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2021-08-07 5:00 PM
 */
public class AppRangeShardingAlgorithm implements RangeShardingAlgorithm {

    @Override
    public Collection<String> doSharding(Collection availableTargetNames, RangeShardingValue shardingValue) {
        return null;
    }


}
