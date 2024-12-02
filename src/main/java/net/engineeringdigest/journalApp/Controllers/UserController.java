package net.engineeringdigest.journalApp.Controllers;


import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.Repository.UserRepositoryImpl;
import net.engineeringdigest.journalApp.Services.GetResponseFromWeatherAPI;
import net.engineeringdigest.journalApp.Services.UserService;
import net.engineeringdigest.journalApp.api.response.WeatherEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userservice;

    @Autowired
    private GetResponseFromWeatherAPI getResponseFromWeatherAPI ;

    @Autowired
    private UserRepositoryImpl userRepositoryimpl;

    @DeleteMapping
    public ResponseEntity<User> deleteUserbyusername() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        try {
            userservice.deletebyUserName(username);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Error err) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User newUser) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            User dbUser = userservice.findByUserName(username);
            if (dbUser != null) {
                dbUser.setUsername(newUser.getUsername() != null && !newUser.getUsername().equals("") ? newUser.getUsername() : dbUser.getUsername());
                dbUser.setPassword(newUser.getPassword() != null && !newUser.getPassword().equals("") ? newUser.getPassword() : dbUser.getPassword());
                userservice.saveUser(dbUser);
            }

            return new ResponseEntity<>(dbUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


    }

    @GetMapping
    public ResponseEntity<String> showweather(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        WeatherEntity weather = getResponseFromWeatherAPI.getResponse();

        if(weather!=null){
            return new ResponseEntity<>("Hi "+ username + " the current temp in your area is "+ weather.getCurrent().getTemperature()+" but it feels like "+weather.getCurrent().getFeelslike(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }
}
