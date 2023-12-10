package com.example.redisspringboot.controller;

import com.example.redisspringboot.dto.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
public class RedisController {
    @Autowired
    private RedisTemplate redisTemplate;
    @GetMapping("/{key}/{value}")
    private Object setValue(@PathVariable String key,@PathVariable String value){
        redisTemplate.opsForValue().set(key,value);
        return  "Success";
    }

    @PostMapping("/{key}")
    private String setValue(@PathVariable String key, @RequestBody UserInfo userInfo){
        redisTemplate.opsForValue().set(key,userInfo);
        return  "Success";
    }
    @GetMapping("/{key}")
    private Object getValue(@PathVariable String key){
        return redisTemplate.opsForValue().get(key);
    }
}
