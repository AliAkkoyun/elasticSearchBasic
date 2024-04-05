package com.aliakkoyun.elasticSearchBasic.controller;

import com.aliakkoyun.elasticSearchBasic.dto.SearchRequestDto;
import com.aliakkoyun.elasticSearchBasic.model.Item;
import com.aliakkoyun.elasticSearchBasic.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping()
    public Item createIndex(@RequestBody Item item){
         return itemService.createIndex(item);
    }

    @PostMapping("/init-index")
    public void additemsFromJson(){
        itemService.additemsFromJson();
    }

    @GetMapping("/getAllDataFromIndex/{indexName}")
    public List<Item> getAllDataFromIndex(@PathVariable String indexName){
        return itemService.getAllDataFromIndex(indexName);
    }
    @GetMapping("/search")
    public List<Item> searchItemsByFieldAndValue(@RequestBody SearchRequestDto dto){
        return itemService.searchItemsByFieldAndValue(dto);
    }
    @GetMapping("/boolQuery")
    public List<Item> boolQuery(@RequestBody SearchRequestDto dto){
        return itemService.boolQuery(dto);
    }
    @GetMapping("/autoSuggest/{name}")
    public Set<String> autoSuggestItemByName(@PathVariable String name){
        return itemService.findSugesstedItemNames(name);
    }
}
