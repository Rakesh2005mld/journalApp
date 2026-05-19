package com.myCompany.journalApp.repository;

import com.myCompany.journalApp.entity.JournalEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.bson.types.ObjectId;

public interface JournalEntryRepository extends MongoRepository<JournalEntry,ObjectId> { //standard repo given by mongo for CRUD
//<> contains the variable where you are operating, dtype of id
}
