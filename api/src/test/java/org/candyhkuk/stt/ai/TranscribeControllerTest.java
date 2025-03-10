package org.candyhkuk.stt.ai;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.candyhkuk.stt.jetty.MockPart;
import org.candyhkuk.stt.utils.SttConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.*;

class TranscribeControllerTest extends Mockito {

    @BeforeEach
    void init(){
        SttTranscribeTest.initWithToken();
        SttConfig.setPropsFromFile("src/test/resources/db.properties");
    }

    @Test
    void testTranscribeAndSave() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getPathInfo()).thenReturn(null);
        when(request.getPart("audio"))
                .thenReturn(new MockPart("audio", "src/test/resources/harvard.wav"));

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        when(response.getWriter()).thenReturn(pw);

        TranscribeController controller = new TranscribeController();
        controller.doPost(request, response);

        String output = sw.toString();
        System.out.println(output);

        pw.close();
        sw.close();
    }

}
