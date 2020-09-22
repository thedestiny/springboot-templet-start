package com.destiny.wolf.service;


import com.destiny.wolf.entity.BookIndex;

import java.util.List;

public interface BookIndexService extends IEsService {
	
	
	/**
	 * 查询数据
	 */
	List<BookIndex> searchList();
	
	/**
	 * 批量插入
	 */
	void insertBach(List<BookIndex> list);
}
