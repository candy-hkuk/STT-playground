package org.candyhkuk.stt.audio;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class CreateUpdateAudioDTO {
    private String src;
    private String transcript;
    private Instant submitTime;

    @JsonCreator
    public CreateUpdateAudioDTO(
            @JsonProperty("src") String src,
            @JsonProperty("submitTime") String submitTime,
            @JsonProperty("transcript") String transcript
    ){
        this.src = src;
        this.submitTime = Instant.parse(submitTime);
        this.transcript = transcript;
    }

    public CreateUpdateAudioDTO(
            String src,
            Instant submitTime,
            String transcript
    ){
        this.src = src;
        this.submitTime = submitTime;
        this.transcript = transcript;
    }

    public String getSrc(){
        return src;
    }

    public void setSrc(String src){
        this.src = src;
    }

    public String getTranscript(){
        return transcript;
    }

    public void setTranscript(String transcript){
        this.transcript = transcript;
    }

    public Instant getSubmitTime(){
        return submitTime;
    }

    public void setSubmitTime(String submitTime){
        this.submitTime = Instant.parse(submitTime);
    }

    public void setSubmitTime(Instant submitTime){
        this.submitTime = submitTime;
    }

    @Override
    public String toString(){
        return "CreateAudioDTO{"
                + "src=" + src
                + ", submitTime=" + submitTime
                + ", transcript=" + transcript
                + "}";
    }

}
