package org.candyhkuk.stt.audio;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Singleton
public class AudioDAO {
    private static final Logger LOG = LogManager.getLogger(AudioDAO.class);
    // TODO: Add DB Connectivity class

    @Inject
    public AudioDAO(){
        // TODO: Implement DB connection
    }

    public AudioEntity createAudio(CreateUpdateAudioDTO audio) {
        // TODO: insert into db implementation
        return null;
    }

    public AudioEntity getAudioById(int id) {
        // TODO: retrieve from db implementation
        return null;
    }

    public boolean updateAudio(CreateUpdateAudioDTO audio, int id) {
        // TODO: update in db implementation
        return true;
    }

    public boolean deleteAudio(int id){
        // TODO: delete in db implementation
        return true;
    }
}
