package org.candyhkuk.stt.db;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.candyhkuk.stt.utils.SttConfig;

public class MongoDb {
    private static final Logger LOG = LogManager.getLogger(MongoDb.class);
    public static final String CONNECTION_STR = "db.mongo.uri";
    private static MongoClient client;

    private MongoDb(){}

    // Function for step-wise testing purposes
    static MongoClientSettings getSettings(String connectionStr){
        ServerApi api = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        return MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionStr))
                .serverApi(api)
                .build();
    }

    // Function for testing purposes
    static MongoDatabase createClient(MongoClientSettings settings, String database) throws MongoException {
        if(client == null) {
            client = MongoClients.create(settings);
            LOG.info("Initializing global MongoDB client");
        } else {
            LOG.info("Accessing an active client");
        }
        return client.getDatabase(database);
    }

    public static MongoDatabase createClient(String database) throws MongoException {
        return createClient(getSettings(SttConfig.get(CONNECTION_STR)), database);
    }

    public static void close(){
        if(client != null){
            client.close();
            client = null;
        }
    }
}
