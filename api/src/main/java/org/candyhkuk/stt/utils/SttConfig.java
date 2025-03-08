package org.candyhkuk.stt.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Properties;

public class SttConfig {
    private static final Logger LOG = LogManager.getLogger(SttConfig.class);
    public static final String ASSEMBLY_AI_KEY = "assemblyai.key";
    public static final String HTTP_SERVER_PORT = "http.server.port";

    private SttConfig(){}

    private static final Properties props = new Properties();

    static {
        if(SttConfig.class.getResource("stt.properties") != null) {
            try (InputStream is = SttConfig.class.getResourceAsStream("stt.properties")) {
                props.load(is);
            } catch (IOException e) {
                LOG.warn("No default configurations found");
            }
        }
    }

    public static void setPropsFromFile(String filePath){
        setPropsFromFile(new File(filePath));
    }

    public static void setPropsFromFile(File propsFile){
        try(InputStream is = new FileInputStream(propsFile)){
            props.load(is);
        } catch(FileNotFoundException e){
            LOG.error("File does not exists", e);
        } catch(SecurityException e){
            LOG.error("Application does not have permission to read file", e);
        } catch(IOException e){
            LOG.error(e);
        }
    }

    public static Properties getProps(){
        return props;
    }

    public static String get(String key){
        return props.getProperty(key, null);
    }

    public static String getOrDefault(String key, String value){
        return props.getProperty(key, value);
    }

    public static boolean has(String key){
        return props.containsKey(key);
    }
}
