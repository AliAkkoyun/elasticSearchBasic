package com.aliakkoyun.elasticSearchBasic.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.aliakkoyun.elasticSearchBasic.dto.SearchRequestDto;
import com.aliakkoyun.elasticSearchBasic.model.Item;
import com.aliakkoyun.elasticSearchBasic.repository.ItemRepository;
import com.aliakkoyun.elasticSearchBasic.util.EsUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final JsonDataService jsonDataService;
    private final ElasticsearchClient elasticsearchClient;

    public Item createIndex(Item item){
        return this.itemRepository.save(item);
    }

    public void additemsFromJson() {
        log.info("Adding all items");
        List<Item> list = jsonDataService.readItemsFromJson();
        itemRepository.saveAll(list);
    }

    public List<Item> getAllDataFromIndex(String indexName) {
        var query = EsUtil.createMatchAllQuery();
        log.info("query of ElasticSearch : {}", query.toString());
        SearchResponse<Item> response = null;
        try{
            response = elasticsearchClient.search(
                    q->q.index(indexName).query(query), Item.class
            );
        } catch (IOException e){
            throw new RuntimeException();
        }
        log.info("response of ElasticSearch : {}", response);
        return extractSearchItemResponse(response);
      }

      public List<Item> extractSearchItemResponse(SearchResponse<Item> response){
        return response.hits()
                .hits().stream().map(Hit::source).collect(Collectors.toList());
      }

    public List<Item> searchItemsByFieldAndValue(SearchRequestDto dto) {
        Supplier<Query> query = EsUtil.buildQueryForFieldAndValue(
                dto.getFieldName().get(0),
                dto.getSearchValue().get(0)
        );
        log.info("query of ElasticSearch : {}", query);
        SearchResponse<Item> response = null;

        try{
            response = elasticsearchClient.search(q->q.index("items-index").query(query.get()), Item.class);

        } catch (IOException e){
            throw new RuntimeException();
        }

        log.info("response of ElasticSearch : {}", response);
        return extractSearchItemResponse(response);
    }

    public List<Item> boolQuery(SearchRequestDto dto) {
        var query = EsUtil.createBoolQuery(dto);
        log.info("query of ElasticSearch : {}", query);
        SearchResponse<Item> response = null;

        try{
            response = elasticsearchClient.search(q->q.index("items-index").query(query.get()), Item.class);

        } catch (IOException e){
            throw new RuntimeException();
        }
        log.info("response of ElasticSearch : {}", response);
        return extractSearchItemResponse(response);
    }

    public Set<String> findSugesstedItemNames(String name) {
        Query query = EsUtil.buildAutoSuggestedQuery(name);
        log.info("query of ElasticSearch : {}", query);

        try{
            return elasticsearchClient.search(p -> p.index("items-index").query(query),Item.class)
                    .hits().hits().stream().map(Hit::source).map(Item::getName).collect(Collectors.toSet());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
