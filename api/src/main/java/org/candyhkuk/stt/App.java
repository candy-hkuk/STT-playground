package org.candyhkuk.stt;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.candyhkuk.stt.ioc.Root;
import org.candyhkuk.stt.utils.SttConfig;
import org.int4.dirk.api.Injector;

public final class App {
    private static final Logger LOG = LogManager.getLogger(App.class);

    public static void main(String... args) throws Exception {
        LOG.info("Starting App");
        setConfig(args);

        Injector injector = Root.setting();

        var bootstrap = injector.getInstance(Bootstrap.class);
        bootstrap.initialize();
        try {
            Thread.sleep(5000);
            bootstrap.destroy();
        } catch(Exception e){
            LOG.error("Failed", e);
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
