package org.candyhkuk.stt;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AppTest {
    static String credsPath;

    @BeforeAll
    static void init(){
        URL creds = AppTest.class.getClassLoader().getResource("creds.properties");
        assertNotNull(creds);
        if(System.getProperty("os.name").startsWith("Windows")){
            credsPath = creds.getPath().substring(1);
        } else {
            credsPath = creds.getPath();
        }

    }

    @Test
    void testMain(){
        assertDoesNotThrow(() -> App.main("--creds=" + credsPath));
    }

    @AfterAll
    static void terminate(){

    }
}
