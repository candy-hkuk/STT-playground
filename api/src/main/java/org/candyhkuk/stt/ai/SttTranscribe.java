package org.candyhkuk.stt.ai;

import com.assemblyai.api.AssemblyAI;
import com.assemblyai.api.resources.transcripts.types.Transcript;
import com.assemblyai.api.resources.transcripts.types.TranscriptStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.candyhkuk.stt.utils.SttConfig;

import java.io.IOException;
import java.io.InputStream;

public class SttTranscribe {
    private static final Logger LOG = LogManager.getLogger(SttTranscribe.class);

    private SttTranscribe(){}

    private static AssemblyAI getClient(){
        return AssemblyAI.builder()
                .apiKey(SttConfig.get(SttConfig.ASSEMBLY_AI_KEY))
                .build();
    }

    private static String handleTranscript(Transcript transcript){
        if (transcript.getStatus() == TranscriptStatus.ERROR) {
            String error = transcript.getError().isPresent() ? transcript.getError().get() : "error message cannot be retrieved";
            LOG.error("Transcript failed with error: {}", error);
            return error;
        }
        return transcript.getText().isPresent() ? transcript.getText().get() : null;
    }

    public static String getFromFile(String src){

        Transcript transcript = getClient().transcripts().transcribe(src);
        return handleTranscript(transcript);
    }

    public static String getFromInputStream(InputStream is) throws IOException {
        Transcript transcript = getClient().transcripts().transcribe(is);
        return handleTranscript(transcript);
    }
}
