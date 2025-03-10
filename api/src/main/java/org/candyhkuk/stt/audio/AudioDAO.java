package org.candyhkuk.stt.audio;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.BsonObjectId;
import org.bson.BsonString;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.candyhkuk.stt.db.MongoDb;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import static org.candyhkuk.stt.audio.AudioEntity.*;

@Singleton
public class AudioDAO implements Serializable {

    private static final Logger LOG = LogManager.getLogger(AudioDAO.class);

    private final MongoCollection<Document> collection;

    @Inject
    public AudioDAO(){
        MongoDatabase db = MongoDb.createClient("stt");
        collection = db.getCollection("audio");
    }

    public AudioEntity createAudio(CreateUpdateAudioDTO audio) {
        Document doc = new Document()
                .append(SRC, audio.getSrc())
                .append(SUBMIT_TIME, audio.getSubmitTime())
                .append(TRANSCRIPT, audio.getTranscript());

        InsertOneResult result = collection.insertOne(doc);
        if(result.wasAcknowledged()){
            BsonObjectId id = Objects.requireNonNull(result.getInsertedId()).asObjectId();

            return new AudioEntity(
                    id != null ? id.getValue().toString() : "",
                    audio.getSrc(),
                    audio.getSubmitTime(),
                    audio.getTranscript()
            );
        } else {
            LOG.error("Failed to insert record: {}", audio);
        }
        return null;
    }

    public AudioEntity getAudioById(String id) {
        Bson filter = Filters.eq("_id", id);
        Document doc = collection.find(filter).first();
        if(doc != null) {
            return new AudioEntity(
                    doc.get("_id", String.class),
                    doc.get(SRC, String.class),
                    doc.get(SUBMIT_TIME, Instant.class),
                    doc.get(TRANSCRIPT, String.class)
            );
        } else {
            LOG.warn("Failed to retrieve Record");
            return null;
        }
    }

    public boolean updateAudio(CreateUpdateAudioDTO audio, String id) {
        Bson filter = Filters.eq("_id", new ObjectId(id));
        Document doc = new Document()
                .append(SRC, audio.getSrc())
                .append(SUBMIT_TIME, audio.getSubmitTime())
                .append(TRANSCRIPT, audio.getTranscript());

        ReplaceOptions options = new ReplaceOptions().upsert(true);
        UpdateResult result = collection.replaceOne(filter, doc, options);

        if(result.wasAcknowledged()){
            BsonObjectId upsertId = Objects.requireNonNull(result.getUpsertedId()).asObjectId();
            LOG.debug("Matched document count for ID({}): {}", upsertId.getValue(), result.getMatchedCount());
            LOG.debug("Modified document count for ID({}): {}", upsertId, result.getModifiedCount());
            return true;
        }
        return false;
    }

    public boolean deleteAudio(String id){
        Bson filter = Filters.eq("_id", id);

        Document deleted = Objects.requireNonNull(collection.findOneAndDelete(filter));
        return deleted.get("_id").equals(id);
    }
}
