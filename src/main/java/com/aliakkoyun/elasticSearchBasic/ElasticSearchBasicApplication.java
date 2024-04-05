package com.aliakkoyun.elasticSearchBasic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
public class ElasticSearchBasicApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElasticSearchBasicApplication.class, args);
	}

}
