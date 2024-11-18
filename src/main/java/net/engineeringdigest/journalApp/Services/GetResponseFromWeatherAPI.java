package net.engineeringdigest.journalApp.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.engineeringdigest.journalApp.api.response.WeatherEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GetResponseFromWeatherAPI {

    @Autowired
    private RestTemplate restTemplate;

    private static final String apiKey = "981d35cd7dabda19b8cbde0d9c912218";
    private static final String apiURL = "http://api.weatherstack.com/current?access_key="+apiKey + "&query=Mumbai"; // Currently hardcoding for testing purpose


    public WeatherEntity getResponse(){

        ResponseEntity<WeatherEntity> response = restTemplate.exchange(apiURL, HttpMethod.GET, null, WeatherEntity.class);
        WeatherEntity body = response.getBody();

        if(body!=null){
            return body;
        }
        return null;
    }
}
