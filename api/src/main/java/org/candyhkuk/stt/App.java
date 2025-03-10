package org.candyhkuk.stt;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.candyhkuk.stt.jetty.HttpServer;
import org.candyhkuk.stt.utils.SttConfig;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

public final class App {
    private static final Logger LOG = LogManager.getLogger(App.class);

    public static void main(String... args) throws Exception {
        LOG.info("Starting App");
        setConfig(args);

        HttpServer server = new HttpServer();

        server.start(8080);

        if(Arrays.asList(args).contains("--test")){
            Thread.sleep(Duration.of(1, ChronoUnit.HOURS));
            server.stop();
        }
    }

    static void setConfig(String[] args){
        for(String arg : args){
            if(arg.startsWith("--creds")){
                SttConfig.setPropsFromFile(arg.replace("--creds=",""));
            }
        }
    }
}
