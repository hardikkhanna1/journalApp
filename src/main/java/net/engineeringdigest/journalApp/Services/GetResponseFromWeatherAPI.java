package net.engineeringdigest.journalApp.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.api.response.WeatherEntity;
import net.engineeringdigest.journalApp.cache.AppCache;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class GetResponseFromWeatherAPI {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache = new AppCache();

    @Autowired
    private RedisService redisService ;

    @Value("${weather.api.key}")
    private String apiKey ;

    public WeatherEntity getResponse() {

        String city = "Mumbai";    // Currently for testing purpose only

        WeatherEntity value = redisService.getValue("Weather_for_" + city, WeatherEntity.class);
        if(value != null){
            return value;
        }
        else{
            String baseURL = appCache.getConfigurations().get("WEATHER_API_URL");
            String apiURL = baseURL.replace("<api_key>",apiKey).replace("<city",city);

            ResponseEntity<WeatherEntity> response = restTemplate.exchange(apiURL, HttpMethod.GET, null, WeatherEntity.class);
            WeatherEntity body = response.getBody();

            if(body!=null){
                redisService.setValue("Weather_for_"+city,body,300l);
                return body;
            }
        }
        return null;
    }
}
