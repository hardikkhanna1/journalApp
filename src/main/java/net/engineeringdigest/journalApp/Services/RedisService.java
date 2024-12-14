package net.engineeringdigest.journalApp.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate ;

    public <T> T getValue(String Key,Class<T> Entity){

        try{
            Object o = redisTemplate.opsForValue().get(Key);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(o.toString(),Entity);
        }
        catch (Exception e){
            log.error("Error - "+ e);
            return null;
        }

    }

    public void setValue(String Key,Object o ,long expiryTime){

        try{
            ObjectMapper objectMapper = new ObjectMapper();
            String s = objectMapper.writeValueAsString(o);
            redisTemplate.opsForValue().set(Key,s,expiryTime, TimeUnit.SECONDS);
        }
        catch (Exception e){
            log.error("Error - "+ e);
        }

    }

}
