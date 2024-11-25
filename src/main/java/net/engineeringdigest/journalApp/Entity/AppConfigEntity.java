package net.engineeringdigest.journalApp.Entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "configurations")
@Data
@NoArgsConstructor
public class AppConfigEntity {

    @Id
    private ObjectId id;

    @NonNull
    private String key;

    private String value;

}
