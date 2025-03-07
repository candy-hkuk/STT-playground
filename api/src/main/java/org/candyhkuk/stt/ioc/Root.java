package org.candyhkuk.stt.ioc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.candyhkuk.stt.audio.AudioController;
import org.candyhkuk.stt.audio.AudioDAO;
import org.candyhkuk.stt.jetty.BeforeAfterRequest;
import org.candyhkuk.stt.jetty.HttpServer;
import org.int4.dirk.api.Injector;
import org.int4.dirk.jsr330.Injectors;

import java.util.Arrays;

public class Root {
    private static final Logger LOG = LogManager.getLogger(Root.class);
    private static Injector injector = null;

    public static Injector getInjector(){
        if(injector == null){
            LOG.error("Injector is not initialized");
            throw new IllegalStateException("Injector is not initialized");
        }
        return Root.injector;
    }

    public static Injector setting(){
        LOG.info("Setting dirk injector...");

        Injector injector = Injectors.autoDiscovering();
        injector.register(Arrays.asList(
                BeforeAfterRequest.class,
                HttpServer.class,
                AudioDAO.class,
                AudioController.class
        ));
        Root.injector = injector;
        return injector;
    }
}
