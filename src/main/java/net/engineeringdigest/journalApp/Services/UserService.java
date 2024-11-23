package net.engineeringdigest.journalApp.Services;


import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.Entity.Journal;
import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.Repository.JournalEntryRepository;
import net.engineeringdigest.journalApp.Repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveUser(User user){
        userRepository.save(user);
    }

    public void saveAdminUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("end_user","Admin"));
        userRepository.save(user);
    }

    public void saveUserEncoded(User user) throws Exception{
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("end_user"));
            userRepository.save(user);
        }
        catch(Exception e) {
            log.debug("User Already exist for user: {}",user.getUsername());
            throw  new Exception("User Duplicate Record");
        }
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getUserByID(ObjectId UserID){
        return userRepository.findById(UserID);
    }

    public void deleteUser(ObjectId userID){
        userRepository.deleteById(userID);
    }

    public User findByUserName(String userName){
        return userRepository.findByusername(userName);
    }

    public void deletebyUserName(String username){
        userRepository.deleteByusername(username);
    }



}
