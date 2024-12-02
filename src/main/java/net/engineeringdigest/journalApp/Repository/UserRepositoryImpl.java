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

    public List<User> userWithWeakPassword(){
        Query query = new Query();
        query.addCriteria(Criteria.where("password").not().regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$\n"));

        return mongoTemplate.find(query,User.class);
    }
}
