package com.myCompany.journalApp.entity;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "users")//to map as collections in mongodb
@Data //generates all the getter,setter,equals,toString,etc.
@Builder
public class User {
    @Id
    private ObjectId id;
    @Indexed(unique = true)//indexed and unique username from now
    @NonNull
    private String userName;
    @NonNull
    private String password;
    @DBRef//to create the link
    private List<JournalEntry> journalEntries=new ArrayList<>();
    private List<String> roles;//what the user can do(user entity to represent the user data model)

}
