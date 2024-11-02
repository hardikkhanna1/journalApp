package net.engineeringdigest.journalApp.Controllers;
import net.engineeringdigest.journalApp.Entity.Journal;
import net.engineeringdigest.journalApp.Entity.User;
import net.engineeringdigest.journalApp.Services.JournalEntryService;
import net.engineeringdigest.journalApp.Services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalentryservice;
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Journal> addEntry(@RequestBody Journal newEntry){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            journalentryservice.saveJournalEntry(newEntry,username);
            return new ResponseEntity<>(newEntry , HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllEntriesofuser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUserName(username);
        List<Journal> allEntries = user.getJournalEntries();
        if(!allEntries.isEmpty()  && allEntries!=null){
            return new ResponseEntity<>(allEntries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<Journal> getEntrybyId(@PathVariable ObjectId myId){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userService.findByUserName(username);

        List<Journal> JournalList = user.getJournalEntries().stream().filter(j -> j.getId().equals(myId)).collect(Collectors.toList());

        if(!JournalList.isEmpty()){
            Optional<Journal> journalEntry = journalentryservice.getJournalEntryByID(myId);
            if(journalEntry.isPresent()) {
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/id/{myId}")
    public ResponseEntity<Journal> UpdateEntrybyId(@PathVariable ObjectId myId,@RequestBody Journal newEntry){

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userService.findByUserName(username);

        List<Journal> JournalList = user.getJournalEntries().stream().filter(j -> j.getId().equals(myId)).collect(Collectors.toList());

        if(!JournalList.isEmpty()) {
            Journal oldEntry = journalentryservice.getJournalEntryByID(myId).orElse(null);

            if(oldEntry!=null){
                oldEntry.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals("")?newEntry.getTitle():oldEntry.getTitle());
                oldEntry.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("")?newEntry.getContent():oldEntry.getContent());
                journalentryservice.saveJournalEntry(oldEntry);
                return new ResponseEntity<>(oldEntry, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // controller for deleting a journal entry
    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteEntrybyId(@PathVariable ObjectId myId){

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userService.findByUserName(username);

        List<Journal> JournalList = user.getJournalEntries().stream().filter(j -> j.getId().equals(myId)).collect(Collectors.toList());

        if(!JournalList.isEmpty()) {
            journalentryservice.deleteEntry(myId,username);
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
