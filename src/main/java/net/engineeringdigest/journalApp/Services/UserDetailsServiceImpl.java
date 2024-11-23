package net.engineeringdigest.journalApp.Services;

import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByusername(username);
        if(user!=null){
            UserDetails userdetails = org.springframework.security.core.userdetails.User.builder().
                    username(user.getUsername()).
                    password(user.getPassword()).
                    roles(user.getRoles().toArray(new String[0])).build();
            return userdetails;
        }
        throw new UsernameNotFoundException("User Not Found With this Username "+ username);
    }
}
