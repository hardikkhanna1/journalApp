package net.engineeringdigest.journalApp.scheduler;

import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.Services.EmailSenderService;
import net.engineeringdigest.journalApp.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PatchingNotificationScheduller {

    @Autowired
    private UserService userService ;

    @Autowired
    private EmailSenderService emailSenderService ;

    @Scheduled(cron = "0 0/1 * 1/1 * ?")
    public void sendGreetingsToEveryUser(){

        for (User user : userService.getAllUsers()) {
            emailSenderService.sendMail(user.getEmail() , "Journal App","Thought of the day is waiting for you");
        }
    }
}
