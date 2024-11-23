package net.engineeringdigest.journalApp.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.api.response.WeatherEntity;
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

    @Value("${weather.api.key}")
    private String apiKey ;

    private final String baseURL = "http://api.weatherstack.com/current";

    public WeatherEntity getResponse() {
        String apiURL = baseURL + "?access_key=" + apiKey + "&query=Mumbai";

        ResponseEntity<WeatherEntity> response = restTemplate.exchange(apiURL, HttpMethod.GET, null, WeatherEntity.class);
        WeatherEntity body = response.getBody();

        if(body!=null){
            return body;
        }
        return null;
    }
}
