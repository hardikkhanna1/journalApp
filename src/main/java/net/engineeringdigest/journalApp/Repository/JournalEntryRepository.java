package net.engineeringdigest.journalApp.Repository;

import net.engineeringdigest.journalApp.Entity.Journal;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository extends MongoRepository<Journal, ObjectId> {
}
