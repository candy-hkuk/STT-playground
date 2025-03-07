package org.candyhkuk.stt.audio;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class AudioEntity {
    private int id;

    @JsonProperty("src")
    private String src;

    @JsonProperty("submitTime")
    private Instant submitTime;

    @JsonProperty("transcription")
    private String transcription;

    public AudioEntity(int id, String src, Instant submitTime, String transcription){
        this.id = id;
        this.src = src;
        this.submitTime = submitTime;
        this.transcription = transcription;
    }

    public AudioEntity(String src, Instant submitTime, String transcription){
        this(-1, src, submitTime, transcription);
    }

    public int getId(){
        return id;
    }

    public String getSrc(){
        return src;
    }

    public Instant getSubmitTime(){
        return submitTime;
    }

    public String getTranscription(){
        return transcription;
    }

    @Override
    public String toString(){
        return "AudioEntity{"
                + "id=" + id
                + ", src=" + src
                + ", submitTime=" + submitTime
                + ", transcript=" + transcription
                + "}";
    }

}
