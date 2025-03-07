package org.candyhkuk.stt.jetty;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.candyhkuk.stt.ioc.Root;

import java.io.IOException;

@WebFilter("/*")
public class FilterImpl implements Filter {
    BeforeAfterRequest beforeAfterRequest;
    private final Logger LOG = LogManager.getLogger(FilterImpl.class);

    public FilterImpl(){
        this.beforeAfterRequest = Root.getInjector().getInstance(BeforeAfterRequest.class);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy(){

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
