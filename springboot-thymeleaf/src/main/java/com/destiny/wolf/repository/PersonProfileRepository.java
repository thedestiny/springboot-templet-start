package com.destiny.wolf.repository;

import com.destiny.wolf.entity.PersonProfile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PersonProfileRepository extends ElasticsearchRepository<PersonProfile, String> {
}
