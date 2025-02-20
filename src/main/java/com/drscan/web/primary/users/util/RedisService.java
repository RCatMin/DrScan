package com.drscan.web.primary.users.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final StringRedisTemplate redisTemplate;

    public void saveToken(String email, String token, long expiration) {
        redisTemplate.opsForValue().set(email, token, expiration, TimeUnit.MINUTES);
    }

    public String getToken(String email) {
        return redisTemplate.opsForValue().get(email);
    }

    public void deleteToken(String email) {
        redisTemplate.delete(email);
    }
}

