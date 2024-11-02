package net.engineeringdigest.journalApp.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "journal")
@Data
@NoArgsConstructor
public class Journal {

    @Id
    private ObjectId id ;

    private String content;

    @NonNull
    private String title;
    private LocalDateTime timestamp;

}
