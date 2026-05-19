package com.myCompany.journalApp.entity;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "journal_entries")//to map as collections in mongodb
@Data //generates all the getter,setter,equals,toString,etc.
@NoArgsConstructor
public class JournalEntry {
    @Id//to map as primary key
    private ObjectId id; //predefined id my mongodb
    @NonNull
    private String title;
    private String content;
    private LocalDateTime date;
}
