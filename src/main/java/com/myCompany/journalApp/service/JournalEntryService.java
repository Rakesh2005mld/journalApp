package com.myCompany.journalApp.service;

import com.myCompany.journalApp.entity.JournalEntry;
import com.myCompany.journalApp.entity.User;
import com.myCompany.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class JournalEntryService {
    @Autowired //dependency injection
    private JournalEntryRepository journalEntryRepository; //even spring inserts the implement for this interface
    @Autowired
    private UserService userService;

    //I want to save both the entries together or else none of them(user and journal),if anything goes wrong with user save entry,I don't want to save the corresponding journal entry else well
    @Transactional//for that we use Transactional(If anything goes wrong,the successful ones are also rolled back)
    public void saveEntry(JournalEntry journalEntry, String userName){
        try{
            User user=userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved=journalEntryRepository.save(journalEntry);//save is from mongodb spring
            user.getJournalEntries().add(saved);
            userService.saveUser(user);//If saveNewUser used,again the existing password will be encoded again
        }catch(Exception e){
            log.error("Exception",e);
        }
    }
    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll(); //predefined mongodb method
    }

    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);//optional means can have data or null
    }

    @Transactional
    public boolean deleteById(ObjectId id, String userName){
        boolean removed;
        try{
            User user=userService.findByUserName(userName);
            removed=user.getJournalEntries().removeIf(x->x.getId().equals(id));
            if(removed){
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);
            }
        }catch(Exception e){
            log.error("Error: ",e);
            throw new RuntimeException("An error occurred during runtime.",e);
        }
        return removed;
    }

}

//calling structure: controller --> service--> repository