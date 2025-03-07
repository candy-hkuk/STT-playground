package org.candyhkuk.stt.audio;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.candyhkuk.stt.ioc.Root;

import java.io.IOException;

public class AudioController extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(AudioController.class);

    private final AudioDAO audioDAO;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AudioController(){
        super();
        this.audioDAO = Root.getInjector().getInstance(AudioDAO.class);
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
        }
    }

}
