package com.example.redisspringboot.subscript.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisMessagePublisher  {
    @Autowired
    @Qualifier("myMessageListener")
    private MessageListener messageListener;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void publish(String channel,String message) {
        redisTemplate.convertAndSend(channel, message);
    }

    public void addSubscription(String channel) {
        // 動態添加訂閱
        redisTemplate.getConnectionFactory().getConnection().subscribe(messageListener, channel.getBytes());
    }

//    public void removeSubscription(String channel) {
//        // 动态取消订阅
//        redisTemplate.getConnectionFactory().getConnection().remo(channel.getBytes());
//    }
}
