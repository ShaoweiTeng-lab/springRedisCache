package com.example.redisspringboot.dto;

import com.example.redisspringboot.vo.Product;
import lombok.Data;

@Data
public class ProductQueryParams {
    Product.ProductCategory category;
    String search;
    OrderBy orderBy;
    Sort sort;
    public enum  OrderBy{
        last_modified_date,
        price,
        stock
    }
    public   enum  Sort{
        ASC,
        DESC
    }
    private  Integer limit;
    private  Integer offset;
}
