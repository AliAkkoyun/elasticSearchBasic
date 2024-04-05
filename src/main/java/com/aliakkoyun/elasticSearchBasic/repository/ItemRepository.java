package com.aliakkoyun.elasticSearchBasic.repository;

import com.aliakkoyun.elasticSearchBasic.model.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends ElasticsearchRepository<Item, String>  {
}
