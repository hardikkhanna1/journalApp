package net.engineeringdigest.journalApp.cache;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.Entity.AppConfigEntity;
import net.engineeringdigest.journalApp.Repository.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Getter
@Slf4j
public class AppCache {

    @Autowired
    private ConfigRepository configRepository;

    private Map<String,String> configurations = new HashMap<>();

    @PostConstruct
    public void init(){
        List<AppConfigEntity> all = configRepository.findAll();

        for (AppConfigEntity appConfigEntity : all){
            configurations.put(appConfigEntity.getKey().toString(),appConfigEntity.getValue().toString());
        }
    }

}
