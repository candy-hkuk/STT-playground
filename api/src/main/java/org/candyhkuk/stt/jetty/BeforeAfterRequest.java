package org.candyhkuk.stt.jetty;

import jakarta.inject.Singleton;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Singleton
public class BeforeAfterRequest {
    private static final Logger LOG = LogManager.getLogger(BeforeAfterRequest.class);

    public void before(HttpServletRequest request){
        String requestUrl = request.getRequestURL().toString();
        LOG.info("Request received: {}", requestUrl);
    }

    public void after(HttpServletRequest request){
        String requestUrl = request.getRequestURL().toString();
        LOG.info("Response completed for: {}", requestUrl);
    }

}
