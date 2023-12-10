package com.example.redisspringboot.service;


import com.example.redisspringboot.dto.ProductQueryParams;
import com.example.redisspringboot.dto.ProductRequest;
import com.example.redisspringboot.vo.Product;
import  java.util.*;
public interface ProductService {
    Product getProductById(Integer id);
    void addProduct(ProductRequest productRequest);
    List<Product> getProducts(ProductQueryParams productQueryParams);

    int countProduct(ProductQueryParams productQueryParams);

}
