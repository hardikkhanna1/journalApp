package net.engineeringdigest.journalApp.Services;
import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.Entity.Journal;
import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.Repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalrepo;
    @Autowired
    private UserService userService;

    @Transactional
    public void saveJournalEntry(Journal entry, String username){

        try {
            User user = userService.findByUserName(username);
            if(user!=null) {
                entry.setTimestamp(LocalDateTime.now());
                Journal insertedEntry =journalrepo.save(entry);
                user.getJournalEntries().add(insertedEntry);
                userService.saveUser(user);
            }
        }
        catch (Error error){
            System.out.println(error);
            throw new Error("Error in adding journal entry");
        }
    }

    public void saveJournalEntry(Journal entry){
            journalrepo.save(entry);
        }

    public List<Journal> getJournalEntries(){
        return journalrepo.findAll();
    }

    public Optional<Journal> getJournalEntryByID(ObjectId journalID){
        return journalrepo.findById(journalID);
    }

    public void deleteEntry(ObjectId journalID, String username){
        User user = userService.findByUserName(username);
        journalrepo.deleteById(journalID);
        user.getJournalEntries().removeIf(x -> x.getId().equals(journalID));
        userService.saveUser(user);
    }
}
