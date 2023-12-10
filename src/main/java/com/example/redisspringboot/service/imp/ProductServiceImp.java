package com.example.redisspringboot.service.imp;


import com.example.redisspringboot.dto.ProductQueryParams;
import com.example.redisspringboot.dto.ProductRequest;
import com.example.redisspringboot.mapper.ProductMapper;
import com.example.redisspringboot.service.ProductService;
import com.example.redisspringboot.vo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImp  implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Override
    @Cacheable(value = "getProductById", keyGenerator = "keyGenerator")
    public Product getProductById(Integer id) {
        return productMapper.getById(id);
    }

    @Override
    public void addProduct(ProductRequest productRequest) {
        Date now = new Date();
        productRequest.setCreateDate(now);
        productRequest.setLastModifiedDate(now);
        productMapper.create(productRequest);
    }

    @Override
    @Cacheable(value = "getProducts", keyGenerator = "keyGenerator")
    public List<Product> getProducts(ProductQueryParams productQueryParams) {
        return productMapper.getProducts(productQueryParams);
    }

    @Override
    public int countProduct(ProductQueryParams productQueryParams) {
        return productMapper.countProduct(productQueryParams);
    }


}
