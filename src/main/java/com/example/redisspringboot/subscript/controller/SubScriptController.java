package com.example.redisspringboot.subscript.controller;

import com.example.redisspringboot.subscript.service.RedisMessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SubScriptController {
    @Autowired
    private RedisMessagePublisher redisMessagePublisher;

    @PostMapping("/{channel}/publish")
    public void publishMessage(@PathVariable String channel, @RequestBody String message) {
        redisMessagePublisher.publish(channel,message);
    }

    @GetMapping("/subscript/{channel}")
    public String subscriptChannel(@PathVariable String channel) {
        redisMessagePublisher.addSubscription(channel);
        return  "訂閱成功";
    }
}

