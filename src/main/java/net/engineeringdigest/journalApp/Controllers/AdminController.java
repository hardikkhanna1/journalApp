package net.engineeringdigest.journalApp.Controllers;

import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    public UserService userservice;

    @GetMapping("/get-all-users")
    public ResponseEntity<List<User>> getAllUsers(){

        List<User> allUsers = userservice.getAllUsers();

        if(allUsers!=null && !allUsers.isEmpty()){
            return new ResponseEntity<>(allUsers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin-user")
    public ResponseEntity<?> createAdminAccount(@RequestBody User user){
        userservice.saveAdminUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
