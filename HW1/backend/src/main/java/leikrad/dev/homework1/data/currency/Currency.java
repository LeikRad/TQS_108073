package leikrad.dev.homework1.data.currency;

import java.io.Serializable;

import org.springframework.data.redis.core.RedisHash;

import lombok.*;

@RedisHash(timeToLive = 3600L, value = "Currency")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Currency implements Serializable {
    private String id;
    private Double eurRate;
}
