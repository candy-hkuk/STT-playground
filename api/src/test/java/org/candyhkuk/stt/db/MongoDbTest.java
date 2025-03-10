package org.candyhkuk.stt.db;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.candyhkuk.stt.utils.SttConfig;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Tag("integration")
public class MongoDbTest {

    @BeforeEach
    void init(){
        SttConfig.setPropsFromFile("src/test/resources/db.properties");
    }

    @Test
    void testGetSettings(){
        assertDoesNotThrow(() -> {
            MongoClientSettings settings = MongoDb.getSettings(SttConfig.get(MongoDb.CONNECTION_STR));
            assertNotNull(settings);
        });
    }

    @Test
    void testCreateClient(){
        assertDoesNotThrow(() -> {
            MongoClientSettings settings = MongoDb.getSettings(SttConfig.get(MongoDb.CONNECTION_STR));
            MongoDatabase db = MongoDb.createClient(settings, "test");
            assertNotNull(db);
        });
    }

    @Test
    void testCreateClientDefault(){
        assertDoesNotThrow(() -> {
            MongoDatabase db = MongoDb.createClient( "test");
            assertNotNull(db);
        });
    }

    @Test
    void testInteraction(){
        assertDoesNotThrow(() -> {
            MongoDatabase db = MongoDb.createClient("admin");
            Bson command = new BsonDocument("ping", new BsonInt64(1));
            Document commandResult = db.runCommand(command);
            System.out.println(commandResult.toJson());
        });
    }

    @AfterEach
    void terminate(){
        MongoDb.close();
    }
}
