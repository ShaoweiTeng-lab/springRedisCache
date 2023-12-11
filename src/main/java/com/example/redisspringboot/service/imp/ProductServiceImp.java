package com.example.redisspringboot.service.imp;


import com.example.redisspringboot.dto.ProductQueryParams;
import com.example.redisspringboot.dto.ProductRequest;
import com.example.redisspringboot.mapper.ProductMapper;
import com.example.redisspringboot.service.ProductService;
import com.example.redisspringboot.vo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImp  implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    /**
     * 添加緩存  id作為key
     * */
    @Override
    @Cacheable(value = "getProductById", key = "#id")
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
    /**
     * 添加緩存  使用自訂keyGenerator
     * */
    @Override
    @Cacheable(value = "getProducts", keyGenerator = "keyGenerator")
    public List<Product> getProducts(ProductQueryParams productQueryParams) {
        return productMapper.getProducts(productQueryParams);
    }

    @Override
    public int countProduct(ProductQueryParams productQueryParams) {
        return productMapper.countProduct(productQueryParams);
    }

    /**
     * 更新緩存
     * */
    @CachePut(value = "getProductById", key = "#productId")
    @Override
    public Product updateProduct(Integer productId, ProductRequest productRequest) {
        Date now = new Date();
        productRequest.setLastModifiedDate(now);
        productMapper.updateById(productId,productRequest);
        return  getProductById(productId);
    }

    @Override
    @CacheEvict(value = "getProductById", key = "#productId")
    public String removeById(Integer productId) {
        productMapper.deleteById(productId);
        return "刪除成功";
    }


}
