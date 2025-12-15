package com.sh.mygallery.domain.refreshtoken.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@AllArgsConstructor
@RedisHash(value = "refreshToken", timeToLive = 60 * 60 * 24 * 7) // 7 days
public class RefreshToken {
    @Id
    private String userId;
    @Indexed
    private String token;
}
