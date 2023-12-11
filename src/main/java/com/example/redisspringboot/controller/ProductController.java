package com.example.redisspringboot.controller;

import com.example.redisspringboot.dto.Page;
import com.example.redisspringboot.dto.ProductQueryParams;
import com.example.redisspringboot.dto.ProductRequest;
import com.example.redisspringboot.service.ProductService;
import com.example.redisspringboot.vo.Product;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/product/{id}")
    public Product getProduct(@PathVariable Integer id){
        return productService.getProductById(id);
    }
    @PostMapping("/product")
    public  String addProduct(@RequestBody ProductRequest productRequest){
        productService.addProduct(productRequest);
        return  "success";
    }

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getProduct(
            //查詢條件 Filtering
            @RequestParam(required = false)String search,
            @RequestParam(required = false) Product.ProductCategory category,
            //排序 Sorting
            @RequestParam(defaultValue ="last_modified_date") ProductQueryParams.OrderBy orderBy,
            @RequestParam(defaultValue ="DESC") ProductQueryParams.Sort sort,
            //分頁 Pagination
            @RequestParam(defaultValue="5")   @Max(100) @Min(0)  Integer limit,//取得幾筆
            @RequestParam(defaultValue = "0") @Min(0)   Integer offset//跳過幾筆
    ){
        ProductQueryParams productQueryParams= new ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);
        productQueryParams.setLimit(limit);
        productQueryParams.setOffset(offset);
        List<Product> products;
        products= productService.getProducts(productQueryParams);
        //取得商品總筆 數
        Integer total =productService.countProduct(productQueryParams);
        //設定返回訊息
        Page<Product> page=new Page<>();
        page.setLimit(limit);
        page.setTotal(total);//當前查詢總共有多少筆數據
        page.setOffset(offset);
        page.setResults(products);
        System.out.println(page.getResults().get(0).getProductId());
        return ResponseEntity.ok().body(page);
    }

    @PutMapping ("/product/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable(name = "productId")Integer productId,
                                                 @RequestBody @Valid ProductRequest  request) {
        Product check=productService.getProductById(productId);
        if(check == null)//先檢查商品是否存在
            return ResponseEntity.badRequest().build();
        System.out.println("商品存在");
        productService.updateProduct(productId,request);
        Product rs=productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(rs);
    }
}
