package net.engineeringdigest.journalApp.Controllers;
import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.Services.EmailSenderService;
import net.engineeringdigest.journalApp.Services.UserDetailsServiceImpl;
import net.engineeringdigest.journalApp.Services.UserService;
import net.engineeringdigest.journalApp.Utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {

    @Autowired
    private UserService userservice;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping("/signup")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            userservice.saveUserEncoded(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            String token = jwtUtils.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(token,HttpStatus.OK);

        } catch (Exception e) {
            log.info("Issue in login and generating token");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
