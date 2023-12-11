package com.example.redisspringboot.subscript.listiner;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@Component
public class MyMessageListener implements org.springframework.data.redis.connection.MessageListener {
    @Override
    public void onMessage(Message message, byte[] pattern) {
        //若已經設定序列化器而非初始的 StringRedisSerializer， 需再此設定 否則返回亂碼
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        String channel = stringRedisSerializer.deserialize(message.getChannel());
        Object msg = stringRedisSerializer.deserialize(message.getBody());
        System.out.println("頻道:" + channel +"\n消息內容:"+msg);
    }
}
