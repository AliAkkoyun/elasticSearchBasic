package com.aliakkoyun.elasticSearchBasic.util;

import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.util.ObjectBuilder;
import com.aliakkoyun.elasticSearchBasic.dto.SearchRequestDto;
import com.aliakkoyun.elasticSearchBasic.model.Item;
import lombok.experimental.UtilityClass;

import java.util.function.Function;
import java.util.function.Supplier;

@UtilityClass
public class EsUtil {
    public static Query createMatchAllQuery() {
        return Query.of(q -> q.matchAll(new MatchAllQuery.Builder().build()));
    }

    public static Supplier<Query> buildQueryForFieldAndValue(String fieldName, String searchValue) {
        return () -> Query.of(q -> q.match(buildMatchQueryForFieldAndValue(fieldName,searchValue)));
    }

    private static MatchQuery buildMatchQueryForFieldAndValue(String fieldName, String searchValue) {
        return new MatchQuery.Builder()
                .field(fieldName)
                .query(searchValue).build();
    }

    public static Supplier<Query> createBoolQuery(SearchRequestDto dto) {
        return () -> Query.of(q -> q.bool(boolQuery(dto.getFieldName().get(0), dto.getSearchValue().get(0),
                                                    dto.getFieldName().get(1), dto.getSearchValue().get(1))));
    }

    private static BoolQuery boolQuery(String key1, String val1, String key2, String val2) {
        return new BoolQuery.Builder()
                .filter(termQuery(key1, val1))
                .must(matchQuery(key2, val2))
                .build();
    }

    private static Query matchQuery(String key2, String val2) {
        return Query.of(q -> q.match(new MatchQuery.Builder()
                .field(key2)
                .query(val2)
                .build()));
    }

    private static Query termQuery(String key1, String val1) {
        return Query.of(q -> q.term(new TermQuery.Builder()
                .field(key1)
                .value(val1)
                .build()));
    }

    public static Query buildAutoSuggestedQuery(String name) {
        return Query.of(p -> p.match(new MatchQuery.Builder()
                .field("name")
                .query(name)
                .analyzer("custom-index")
                .build()));
    }
}
