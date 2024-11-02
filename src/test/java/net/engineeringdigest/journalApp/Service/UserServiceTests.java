package net.engineeringdigest.journalApp.Service;

import lombok.NonNull;
import net.engineeringdigest.journalApp.Repository.UserRepository;
import net.engineeringdigest.journalApp.Services.UserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;


    @Test
    public void deleteUserTests(){
        assertNotNull(userRepository.findByusername("hardik"));
    }

    @ParameterizedTest
    @CsvFileSource(resources ="/testparam.csv")
    public void testingparam(String username){
        assertNotNull(userRepository.findByusername(username));
    }


}
