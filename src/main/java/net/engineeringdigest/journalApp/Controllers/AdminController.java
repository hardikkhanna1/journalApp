package net.engineeringdigest.journalApp.Controllers;
import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.Repository.UserRepositoryImpl;
import net.engineeringdigest.journalApp.Services.EmailSenderService;
import net.engineeringdigest.journalApp.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    public UserService userservice;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private UserRepositoryImpl userRepositoryimpl;

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

    @PostMapping("/send-patching-notification")
    public ResponseEntity<List<User>> sendReminderForWeakPassword(){
        try {
            List<User> users = userRepositoryimpl.userWithNotificationTrue();

            for (User user : users) {
                emailSenderService.sendMail(user.getEmail(),
                        "Journal App - Patch Upgrade",
                        "Patching activity currently going on!");
            }

            return new ResponseEntity<>(users , HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error in sending notification");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
