package org.candyhkuk.stt.audio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class AudioController extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(AudioController.class);

    private final AudioDAO audioDAO;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JSR310Module());

    public AudioController(){
        super();
        this.audioDAO = new AudioDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();

        if(pathInfo == null){

            try {
                String jsonResponse = "some response";
                resp.getWriter().write(jsonResponse);
            } catch(Exception e){
                LOG.error("some error", e);
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write("{\"error\":\"General error\"}");
            }
        } else {
            String[] pathParts = pathInfo.split("/");
            if(pathParts.length > 1){
                try {
                    String audioId = pathParts[1];
                    AudioEntity audio = audioDAO.getAudioById(audioId);
                    if(audio != null){
                        String jsonResponse = objectMapper.writeValueAsString(audio);
                        resp.setContentType("application/json");
                        resp.getWriter().write(jsonResponse);
                    } else {
                        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        resp.getWriter().write("{\"error\":\"Audio Record Not Found\"}");
                    }
                } catch(Exception e){
                    LOG.error("Error fetching audio by ID: {}", pathParts[1], e);
                    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    resp.getWriter().write("{\"error\":\"Error fetching audio record\"}");
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            CreateUpdateAudioDTO audioDTO = objectMapper.readValue(req.getReader(), CreateUpdateAudioDTO.class);
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

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        if(pathInfo != null && pathInfo.length() > 1){
            String[] pathParts = pathInfo.split("/");
            if(pathParts.length > 1){
                try {
                    String audioId = pathParts[1];
                    CreateUpdateAudioDTO audioDTO = objectMapper.readValue(req.getReader(), CreateUpdateAudioDTO.class);
                    boolean success = audioDAO.updateAudio(audioDTO, audioId);
                    if(success){
                        resp.setStatus(HttpServletResponse.SC_OK);
                        resp.getWriter().write("{\"message\":\"Audio record updated successfully\"}");
                    } else {
                        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        resp.getWriter().write("{\"error\":\"Product not found\"}");
                    }
                } catch(Exception e){
                    LOG.error("Error updating audio record with ID: {}", pathParts[1], e);
                    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    resp.getWriter().write("{\"error\":\"Error updating audio record\"}");
                }
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("{\"error\":\"Audio record not found\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        if(pathInfo != null && pathInfo.length() > 1){
            String[] pathParts = pathInfo.split("/");
            if(pathParts.length > 1){
                try {
                    String audioId = pathParts[1];
                    boolean success = audioDAO.deleteAudio(audioId);
                    if(success){
                        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                    } else {
                        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        resp.getWriter().write("{\"error\":\"Audio record not found\"}");
                    }
                } catch(Exception e){
                    LOG.error("Error deleting audio record with ID: {}", pathParts[1], e);
                    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    resp.getWriter().write("{\"error\":\"Error delete audio record\"}");
                }
            }
        }
    }
}
