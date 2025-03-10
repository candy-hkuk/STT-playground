package org.candyhkuk.stt.ai;

import org.candyhkuk.stt.utils.SttConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

public class SttTranscribeTest {

    @BeforeAll
    static void initWithToken(){
        URL creds = SttTranscribeTest.class.getClassLoader().getResource("creds.properties");
        assertNotNull(creds);
        String path;
        if(System.getProperty("os.name").startsWith("Windows")){
            path = creds.getPath().substring(1);
        } else {
            path = creds.getPath();
        }
        SttConfig.setPropsFromFile(path);
    }

    @Test
    void testInit(){
        assertTrue(SttConfig.has(SttConfig.ASSEMBLY_AI_KEY));
    }

    @Tag("integration")
    @Test
    void testTranscribeDefault(){
        String src = "https://assembly.ai/news.mp4";
        assertDoesNotThrow(() -> {
            String msg = SttTranscribe.getFromFile(src);
            System.out.println(msg);
        });
    }

    @Tag("integration")
    @Test
    void testTranscribeFromInputStream(){
        String audio = "src/test/resources/harvard.wav";
        assertDoesNotThrow(() -> {
            String msg = SttTranscribe.getFromInputStream(new FileInputStream(audio));
            System.out.println(msg);
        });
    }
}
