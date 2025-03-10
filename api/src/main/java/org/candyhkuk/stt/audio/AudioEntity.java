package org.candyhkuk.stt.audio;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class AudioEntity {

    public static final String SRC = "src";
    public static final String SUBMIT_TIME = "submitTime";
    public static final String TRANSCRIPT = "transcript";


    private String id;

    @JsonProperty(SRC)
    private String src;

    @JsonProperty(SUBMIT_TIME)
    private Instant submitTime;

    @JsonProperty(TRANSCRIPT)
    private String transcript;

    public AudioEntity(String id, String src, Instant submitTime, String transcript){
        this.id = id;
        this.src = src;
        this.submitTime = submitTime;
        this.transcript = transcript;
    }

    public AudioEntity(String src, Instant submitTime, String transcript){
        this("", src, submitTime, transcript);
    }

    public String getId(){
        return id;
    }

    public String getSrc(){
        return src;
    }

    public Instant getSubmitTime(){
        return submitTime;
    }

    public String getTranscript(){
        return transcript;
    }

    @Override
    public String toString(){
        return "AudioEntity{"
                + "id=" + id
                + ", src=" + src
                + ", submitTime=" + submitTime
                + ", transcript=" + transcript
                + "}";
    }

}
