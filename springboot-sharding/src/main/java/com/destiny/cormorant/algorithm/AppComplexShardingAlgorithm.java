package com.destiny.cormorant.algorithm;

import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2021-08-07 4:49 PM
 *
 * ComplexKeysShardingAlgorithm 复杂分片
 * HintShardingAlgorithm 强制分片
 * PreciseShardingAlgorithm 精确分片
 * RangeShardingAlgorithm 范围分片
 */

public class AppComplexShardingAlgorithm implements ComplexKeysShardingAlgorithm<Long> {

    /**
     * availableTargetNames 目标数据源 或者 表 的值。
     * shardingValue logicTableName逻辑表名 columnNameAndShardingValuesMap 分片列的精确值集合。 columnNameAndRangeValuesMap 分片列的范围值集合
     */
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ComplexKeysShardingValue<Long> shardingValue) {

        //实现按照 Between 进行范围分片。
        //例如 select * from course where cid in (1,3,5) and userid Between 200 and 300;

        Collection<Long> cidCol = shardingValue.getColumnNameAndShardingValuesMap().get("id");
        Range<Long> uageRange = shardingValue.getColumnNameAndRangeValuesMap().get("user_id");

        List<String> result = new ArrayList<>();

        Long lowerEndpoint = uageRange.lowerEndpoint();//200
        Long upperEndpoint = uageRange.upperEndpoint();//300
        //实现自定义分片逻辑 例如可以自己实现 course_$->{cid%2+1 + (30-20)+1} 这样的复杂分片逻辑
        for (Long cid : cidCol) {
            BigInteger cidI = BigInteger.valueOf(cid);
            BigInteger target = (cidI.mod(BigInteger.valueOf(2L))).add(new BigInteger("1"));
            result.add("t_goods_" + target);
        }

        return result;
    }
}
