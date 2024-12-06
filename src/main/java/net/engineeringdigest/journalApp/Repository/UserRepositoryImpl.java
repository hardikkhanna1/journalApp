package net.engineeringdigest.journalApp.Repository;

import net.engineeringdigest.journalApp.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserRepositoryImpl {

    @Autowired
    public MongoTemplate mongoTemplate;

    public List<User> userWithNotificationTrue(){
        Query query = new Query();
        query.addCriteria(Criteria.where("optForNotification").is(true));
        return mongoTemplate.find(query,User.class);
    }
}
