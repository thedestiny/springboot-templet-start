package com.destiny.wolf.repository;

import com.destiny.wolf.entity.BookIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface BookIndexRepository extends ElasticsearchRepository<BookIndex, String> {
}
