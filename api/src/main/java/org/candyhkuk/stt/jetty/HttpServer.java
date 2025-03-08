package org.candyhkuk.stt.jetty;

import jakarta.servlet.DispatcherType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.candyhkuk.stt.audio.AudioController;
import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.ee10.servlet.ServletHolder;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.CrossOriginHandler;

import java.util.EnumSet;
import java.util.Set;

public class HttpServer {
    private static final Logger LOG = LogManager.getLogger(HttpServer.class);

    private Server server;
    private ServerConnector connector;

    public HttpServer(){
        this.server = new Server();
        this.connector = new ServerConnector(server);
    }

    public void start(int port) throws Exception {
        connector.setPort(port);
        server.addConnector(connector);

        CrossOriginHandler xHandler = new CrossOriginHandler();
        xHandler.setAllowedOriginPatterns(Set.of("http://localhost"));
        xHandler.setAllowCredentials(true);
        server.setHandler(xHandler);

        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/api");

        context.addFilter(FilterImpl.class, "/*", EnumSet.of(DispatcherType.REQUEST));

        xHandler.setHandler(context);

        ServletHolder servletHolder = context.addServlet(AudioController.class, "/audio/*");
        servletHolder.setInitParameter("maxItems", "128");

        server.start();
        LOG.info("Server started on port {}", port);
    }

    public void stop() throws Exception {
        LOG.info("Stopping server");
        server.stop();
        LOG.info("Server stopped");
    }

}
