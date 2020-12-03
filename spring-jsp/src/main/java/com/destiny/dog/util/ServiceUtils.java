package com.destiny.dog.util;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 
 
 */
public class ServiceUtils {
	
	private ServiceUtils() {
	}
	
	/**
	 *  获取集合对象某个属性的 set 集合
	 *
	 * @param datas           集合
	 * @param mappingFunction 映射函数
	 * @param <ID>            id type
	 * @param <T>             data type
	 * @return a set of id
	 */
	@NonNull
	public static <ID, T> Set<ID> fetchProperty(final Collection<T> datas, Function<T, ID> mappingFunction) {
		return CollectionUtils.isEmpty(datas) ?
				Collections.emptySet() :
				datas.stream().map(mappingFunction).collect(Collectors.toSet());
	}
	
	/**
	 * 转变list 为 map ，按照某个属性进行分组
	 *
	 * @param ids             id collection
	 * @param list            data list
	 * @param mappingFunction calculate the id in data list
	 * @param <ID>            id type
	 * @param <D>             data type
	 * @return 最终返回的结果
	 */
	@NonNull
	public static <ID, D> Map<ID, List<D>> convertToListMap(Collection<ID> ids, Collection<D> list, Function<D, ID> mappingFunction) {
		Assert.notNull(mappingFunction, "mapping function must not be null");
		
		if (CollectionUtils.isEmpty(ids) || CollectionUtils.isEmpty(list)) {
			return Collections.emptyMap();
		}
		
		Map<ID, List<D>> resultMap = new HashMap<>();
		
		list.forEach(data -> resultMap.computeIfAbsent(mappingFunction.apply(data), id -> new LinkedList<>()).add(data));
		
		ids.forEach(id -> resultMap.putIfAbsent(id, Collections.emptyList()));
		
		return resultMap;
	}
	
	/**
	 * Converts to map (key from the list data)
	 *
	 * @param list            data list
	 * @param mappingFunction calclulate the id from list data
	 * @param <ID>            id type
	 * @param <D>             data type
	 * @return a map which key from list data and value is data
	 */
	@NonNull
	public static <ID, D> Map<ID, D> convertToMap(Collection<D> list, Function<D, ID> mappingFunction) {
		Assert.notNull(mappingFunction, "mapping function must not be null");
		
		if (CollectionUtils.isEmpty(list)) {
			return Collections.emptyMap();
		}
		
		Map<ID, D> resultMap = new HashMap<>();
		
		list.forEach(data -> resultMap.putIfAbsent(mappingFunction.apply(data), data));
		
		return resultMap;
	}
	
	/**
	 * Converts to map (key from the list data)
	 *
	 * @param list          data list
	 * @param keyFunction   key mapping function
	 * @param valueFunction value mapping function
	 * @param <ID>          id type
	 * @param <D>           data type
	 * @param <V>           value type
	 * @return a map which key from list data and value is data
	 */
	@NonNull
	public static <ID, D, V> Map<ID, V> convertToMap(@Nullable Collection<D> list, @NonNull Function<D, ID> keyFunction, @NonNull Function<D, V> valueFunction) {
		Assert.notNull(keyFunction, "Key function must not be null");
		Assert.notNull(valueFunction, "Value function must not be null");
		
		if (CollectionUtils.isEmpty(list)) {
			return Collections.emptyMap();
		}
		
		Map<ID, V> resultMap = new HashMap<>();
		
		list.forEach(data -> resultMap.putIfAbsent(keyFunction.apply(data), valueFunction.apply(data)));
		
		return resultMap;
	}
	
	/**
	 * Checks if the given number id is empty id.
	 *
	 * @param id the given number id
	 * @return true if the given number id is empty id; false otherwise
	 */
	public static boolean isEmptyId(@Nullable Number id) {
		return id == null || id.longValue() <= 0;
	}
	
	// /**
	//  * Builds latest page request.
	//  *
	//  * @param top top must not be less than 1
	//  * @return latest page request
	//  */
	// @NonNull
	// public static Pageable buildLatestPageable(int top) {
	//     return buildLatestPageable(top, "createTime");
	// }
//
	// /**
	//  * Build empty page result.
	//  *
	//  * @param page page info must not be null
	//  * @param <T>  target page result type
	//  * @param <S>  source page result type
	//  * @return empty page result
	//  */
	// @NonNull
	// public static <T, S> Page<T> buildEmptyPageImpl(@NonNull Page<S> page) {
	//     Assert.notNull(page, "Page result must not be null");
//
	//     return new PageImpl<>(Collections.emptyList(), page.getPageable(), page.getTotalElements());
	// }
//
	// /**
	//  * Builds latest page request.
	//  *
	//  * @param top          top must not be less than 1
	//  * @param sortProperty sort property must not be blank
	//  * @return latest page request
	//  */
	// @NonNull
	// public static Pageable buildLatestPageable(int top, @NonNull String sortProperty) {
	//     Assert.isTrue(top > 0, "Top number must not be less than 0");
	//     Assert.hasText(sortProperty, "Sort property must not be blank");
//
	//     return PageRequest.of(0, top, Sort.by(Sort.Direction.DESC, sortProperty));
	// }
}
