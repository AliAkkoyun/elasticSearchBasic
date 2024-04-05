package com.aliakkoyun.elasticSearchBasic.service;


import com.aliakkoyun.elasticSearchBasic.model.Item;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.util.*;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class JsonDataService {
    private final ObjectMapper objectMapper;

    public List<Item> readItemsFromJson(){
        try{
            ClassPathResource resource = new ClassPathResource("data/Items.json");
            InputStream inputStream = resource.getInputStream();
            return objectMapper.readValue(inputStream, new TypeReference<List<Item>>() {
            });
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
