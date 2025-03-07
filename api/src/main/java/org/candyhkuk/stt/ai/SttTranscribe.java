package org.candyhkuk.stt.ai;

import com.assemblyai.api.AssemblyAI;
import com.assemblyai.api.resources.transcripts.types.Transcript;
import com.assemblyai.api.resources.transcripts.types.TranscriptStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.candyhkuk.stt.utils.SttConfig;

public class SttTranscribe {
    private static final Logger LOG = LogManager.getLogger(SttTranscribe.class);

    private SttTranscribe(){}

    public static String getFromFile(String src){
        AssemblyAI client = AssemblyAI.builder()
                .apiKey(SttConfig.get(SttConfig.ASSEMBLY_AI_KEY))
                .build();

        Transcript transcript = client.transcripts().transcribe(src);

        if (transcript.getStatus() == TranscriptStatus.ERROR) {
            String error = transcript.getError().isPresent() ? transcript.getError().get() : "error message cannot be retrieved";
            LOG.error("Transcript failed with error: {}", error);
        }

        return transcript.toString();
    }
}
