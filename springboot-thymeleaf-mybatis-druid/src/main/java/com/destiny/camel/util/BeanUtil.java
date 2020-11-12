package com.destiny.camel.util;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.core.loader.api.BeanMappingBuilder;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.github.dozermapper.core.loader.api.TypeMappingOptions.mapEmptyString;
import static com.github.dozermapper.core.loader.api.TypeMappingOptions.mapNull;

/**
 * @Description: Dozer Bean复制工具
 */
public class BeanUtil {

    protected static Mapper mapper;

    static {
        mapper = DozerBeanMapperBuilder.buildDefault();
    }

    /**
     * @param s   数据对象
     * @param clz 复制目标类型
     * @param <T>
     * @param <S>
     * @return
     * @Description: 单个对象的深度复制及类型转换，vo/domain , po
     */
    public static <T, S> T convert(S s, Class<T> clz) {
        if (s == null) {
            return null;
        }
        return mapper.map(s, clz);
    }


    /**
     * @param s   数据对象
     * @param clz 复制目标类型
     * @param <T>
     * @param <S>
     * @return
     * @Description: list深度复制
     */
    public static <T, S> List<T> convert(List<S> s, Class<T> clz) {
        if (s == null) {
            return null;
        }
        List<T> list = new ArrayList<T>();
        for (S vs : s) {
            list.add(mapper.map(vs, clz));
        }
        return list;
    }

    /**
     * @param s   数据对象
     * @param clz 复制目标类型
     * @param <T>
     * @param <S>
     * @return
     * @Description: Set深度复制
     */
    public static <T, S> Set<T> convert(Set<S> s, Class<T> clz) {
        if (s == null) {
            return null;
        }
        Set<T> set = new HashSet<T>();
        for (S vs : s) {
            set.add(mapper.map(vs, clz));
        }
        return set;
    }

    /**
     * @param s   数据对象
     * @param clz 复制目标类型
     * @param <T>
     * @param <S>
     * @return
     * @Description: 数组深度复制
     */
    public static <T, S> T[] convert(S[] s, Class<T> clz) {
        if (s == null) {
            return null;
        }
        @SuppressWarnings("unchecked")
        T[] arr = (T[]) Array.newInstance(clz, s.length);
        for (int i = 0; i < s.length; i++) {
            arr[i] = mapper.map(s[i], clz);
        }
        return arr;
    }

    /**
     * 拷贝非空且非空串属性
     *
     * @param source 数据源
     * @param target 指向源
     */
    public static void convertIgnoreNullAndBlank(Object source, Object target) {
        DozerBeanMapperBuilder dozerBeanMapperBuilder = DozerBeanMapperBuilder.create();
        Mapper mapper = dozerBeanMapperBuilder.withMappingBuilders(new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(source.getClass(), target.getClass(), mapNull(false), mapEmptyString(false));
            }
        }).build();
        mapper.map(source, target);
    }

    /**
     * 拷贝非空属性
     *
     * @param source 数据源
     * @param target 指向源
     */
    public static void convertIgnoreNull(Object source, Object target) {
        DozerBeanMapperBuilder dozerBeanMapperBuilder = DozerBeanMapperBuilder.create();
        Mapper mapper = dozerBeanMapperBuilder.withMappingBuilders(new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(source.getClass(), target.getClass(), mapNull(false));
            }
        }).build();
        mapper.map(source, target);
    }
}
