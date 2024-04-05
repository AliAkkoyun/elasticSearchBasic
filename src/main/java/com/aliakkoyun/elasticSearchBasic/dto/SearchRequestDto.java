package com.aliakkoyun.elasticSearchBasic.dto;

import com.aliakkoyun.elasticSearchBasic.model.Item;
import lombok.Getter;
import lombok.Setter;
import java.util.*;

@Getter
@Setter
public class SearchRequestDto {
    private List<String> fieldName;
    private List<String> searchValue;

}
