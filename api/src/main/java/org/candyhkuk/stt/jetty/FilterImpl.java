package org.candyhkuk.stt.jetty;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Random;

@WebFilter("/*")
public class FilterImpl implements Filter {
    private final BeforeAfterRequest beforeAfterRequest;
    private final int instanceId;
    private final Logger LOG = LogManager.getLogger(FilterImpl.class);

    public FilterImpl(){
        this.beforeAfterRequest = new BeforeAfterRequest();
        instanceId = new Random().nextInt();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOG.debug("Initializing filter configurations for ID({}): {}", instanceId, filterConfig);
    }

    @Override
    public void destroy(){
        LOG.debug("Destroying filter ID({})", instanceId);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        if(request instanceof HttpServletRequest httpRequest){
            this.beforeAfterRequest.before(httpRequest);
            chain.doFilter(request,response);
            this.beforeAfterRequest.after(httpRequest);
        }
        chain.doFilter(request, response);
    }

}
