package org.candyhkuk.stt.audio;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.bson.types.ObjectId;
import org.candyhkuk.stt.jetty.MockServletInputStream;
import org.candyhkuk.stt.utils.SttConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("integration")
public class AudioControllerTest extends Mockito {

    @BeforeEach
    void init(){
        SttConfig.setPropsFromFile("src/test/resources/db.properties");
    }

    @Test
    void testGetRecords() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getPathInfo())
                .thenReturn(null);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        when(response.getWriter()).thenReturn(pw);

        AudioController controller = new AudioController();
        controller.doGet(request, response);

        String output = sw.toString();
        assertEquals("some response", output);
    }

    @Test
    void testPostRecords() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Instant testTime = Instant.now();

        String json = "{\"src\":\"source\",\"submitTime\":\"" + testTime + "\",\"transcript\":\"this is a transcript\"}";
        when(request.getPathInfo()).thenReturn("/");
        when(request.getInputStream())
                .thenReturn(new MockServletInputStream((new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8)))));
        when(request.getReader())
                .thenReturn(new BufferedReader(new StringReader(json)));
        when(request.getContentType()).thenReturn("application/json");
        when(request.getCharacterEncoding()).thenReturn("UTF-8");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        when(response.getWriter()).thenReturn(pw);

        AudioController controller = new AudioController();
        controller.doPost(request, response);

        String output = sw.toString();
        assertTrue(output.contains("this is a transcript"));

        pw.close();
        sw.close();
    }

    @Test
    void testPutRecords() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Instant testTime = Instant.now();

        String json = "{\"src\":\"source\",\"submitTime\":\"" + testTime + "\",\"transcript\":\"this is a transcript\"}";
        when(request.getPathInfo()).thenReturn("/" + ObjectId.get());
        when(request.getInputStream())
                .thenReturn(new MockServletInputStream((new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8)))));
        when(request.getReader())
                .thenReturn(new BufferedReader(new StringReader(json)));
        when(request.getContentType()).thenReturn("application/json");
        when(request.getCharacterEncoding()).thenReturn("UTF-8");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        when(response.getWriter()).thenReturn(pw);

        AudioController controller = new AudioController();
        controller.doPut(request, response);

        String output = sw.toString();
        assertTrue(output.contains("Audio record updated successfully"));

        pw.close();
        sw.close();
    }

}
