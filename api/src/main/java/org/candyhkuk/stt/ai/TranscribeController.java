package org.candyhkuk.stt.ai;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.candyhkuk.stt.audio.AudioDAO;
import org.candyhkuk.stt.audio.AudioEntity;
import org.candyhkuk.stt.audio.CreateUpdateAudioDTO;

import java.io.IOException;
import java.time.Instant;

public class TranscribeController extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(TranscribeController.class);

    private final AudioDAO audioDAO;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JSR310Module());

    public TranscribeController(){
        super();
        this.audioDAO = new AudioDAO();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String src = req.getParameter("user");

            CreateUpdateAudioDTO audioDTO = new CreateUpdateAudioDTO(
                    src.isBlank() ? req.getRemoteUser() : src,
                    Instant.now(),
                    SttTranscribe.getFromInputStream(req.getPart("audio").getInputStream())
            );

            AudioEntity audio = audioDAO.createAudio(audioDTO);
            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write(objectMapper.writeValueAsString(audio));
        } catch(Exception e){
            LOG.error("Error creating audio record: {}", e.getMessage(), e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"Error creating audio record\"}");
        }
    }
}
