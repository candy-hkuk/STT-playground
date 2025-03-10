package org.candyhkuk.stt;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.candyhkuk.stt.jetty.HttpServer;
import org.candyhkuk.stt.utils.SttConfig;

@Singleton
public class Bootstrap {
    private static final Logger LOG = LogManager.getLogger(Bootstrap.class);
    private final HttpServer server;

    @Inject
    public Bootstrap(HttpServer server) {
        this.server = server;
    }

    public void initialize() {
        LOG.info("Initializing all services...");

        int port = Integer.parseInt(SttConfig.getOrDefault(SttConfig.HTTP_SERVER_PORT, "8080"));

        try {
            server.start(port);
        } catch(Exception e){
            LOG.error("Failed to start HTTP server at port {}", port, e);
        }
    }

    public void destroy(){
        LOG.info("Destroying all services...");
        try {
            server.stop();
            LOG.info("HTTP server stopped successfully");
        } catch(Exception e){
            LOG.error("Failed to stop http server", e);
        }
    }
}
