package net.engineeringdigest.journalApp.Service;
import net.engineeringdigest.journalApp.Entity.Journal;
import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.Services.JournalEntryService;
import net.engineeringdigest.journalApp.Services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.*;
import org.mockito.Mock;
import org.springframework.util.Assert;

import java.util.ArrayList;
import static org.mockito.Mockito.when;

@Disabled
public class JournalEntryServiceTest {

    @InjectMocks
    private JournalEntryService journalEntryService;

    @Mock
    private UserService userService;

    @BeforeEach
    void Setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveJournalEntryTest(){

        Journal journal = new Journal();
        journal.setTitle("Testing");
        journal.setContent("Test Content");

        User user = new User("test_user","yeh_kuch_bhi");
        user.setUsername("test_user");
        user.setPassword("yeh_kuch_bhi");
        user.setRoles(new ArrayList<>());
        user.setJournalEntries(new ArrayList<>());

        when(userService.findByUserName(ArgumentMatchers.anyString())).thenReturn(user);

        Assert.notNull(userService.findByUserName("test"));

    }

}
