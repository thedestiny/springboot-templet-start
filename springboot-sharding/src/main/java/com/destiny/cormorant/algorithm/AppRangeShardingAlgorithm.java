package com.destiny.cormorant.algorithm;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @Description
 * @Author destiny
 * @Date 2021-08-07 5:00 PM
 */

@Slf4j
public class AppRangeShardingAlgorithm implements RangeShardingAlgorithm<Long> {

    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Long> sharding) {

        Set<String> result = new LinkedHashSet<>();
        long lower = sharding.getValueRange().lowerEndpoint();
        long upper = sharding.getValueRange().upperEndpoint();
        for (long i = lower; i <= upper; i++) {
            long l = i % collection.size() + 1;
            for (String each : collection) { //ds0,ds1
                if (each.endsWith( l + "")) {
                    result.add(each);
                }
            }
        }

        return result;
    }


}
