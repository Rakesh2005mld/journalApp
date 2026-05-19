package com.myCompany.journalApp.service;

import com.myCompany.journalApp.entity.User;
import com.myCompany.journalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    @Autowired //dependency injection
    private UserRepository userRepository; //even spring inserts the implement for this interface

    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

//    private static final Logger log= LoggerFactory.getLogger(UserService.class); //use @Slf4j annotation instead

    public boolean saveNewUser(User user){
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(List.of("User"));
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            log.error("An error occurred for {}: ",user.getUserName(),e);
            return false;
        }
    }

    public void saveAdmin(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("User","ADMIN"));
        userRepository.save(user);
    }

    public void saveUser(User user){
        try{
            userRepository.save(user);//save is from mongodb spring
        }catch(Exception e){
            log.error("Exception",e);
        }
    }

    public List<User> getAll(){
        return userRepository.findAll(); //predefined mongodb method
    }

    public Optional<User> findById(ObjectId id){
        return userRepository.findById(id);//optional means can have data or null
    }

    public void deleteById(ObjectId id){
        userRepository.deleteById(id);
    }

    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }
}

//calling structure: controller --> service--> repository