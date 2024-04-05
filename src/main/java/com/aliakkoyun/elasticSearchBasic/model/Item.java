package com.aliakkoyun.elasticSearchBasic.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@Getter
@Setter
@Document(indexName = "items-index")
@Setting(settingPath = "static/es-setting.json")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item  {


    @Id
    private String id;

    @Field(name = "name", type = FieldType.Text, analyzer = "custom-index", searchAnalyzer = "custom-search")
    private String name;
    @Field(name = "price", type = FieldType.Double)
    private double price;
    @Field(name = "brand", type = FieldType.Text, analyzer = "custom-index", searchAnalyzer = "custom-search")
    private String brand;
    @Field(name = "category", type = FieldType.Keyword )
    private String category;
}
