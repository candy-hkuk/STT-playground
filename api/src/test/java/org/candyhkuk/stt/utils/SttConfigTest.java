package org.candyhkuk.stt.utils;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class SttConfigTest {
    final String propsFile = "src/test/resources/stt-test.properties";

    @Test
    void testSttConfig(){
        assertTrue(SttConfig.getProps().isEmpty());
    }

    @Test
    void testSetPropertiesFromFile(){
        assertDoesNotThrow(() -> {
            assertTrue(Files.exists(Path.of(propsFile)));
            SttConfig.setPropsFromFile(propsFile);
        });
    }

    @Test
    void testGetPropertiesAfterSettingPropertiesFromFile(){
        assertDoesNotThrow(() -> {
            SttConfig.setPropsFromFile(propsFile);
            assertFalse(SttConfig.getProps().isEmpty());
        });
    }

    @Test
    void testGetPropertyAfterSettingPropertiesFromFile(){
        assertDoesNotThrow(() -> {
            SttConfig.setPropsFromFile(propsFile);
            assertNotNull(SttConfig.get("some.param"));
            assertEquals("thisissomeparam", SttConfig.get("some.param"));
        });
    }
}
