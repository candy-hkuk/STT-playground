package org.candyhkuk.stt.jetty;

import jakarta.servlet.http.Part;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Collection;
import java.util.List;

public class MockPart implements Part {
    private final String partName;
    private final String path;

    public MockPart(String partName, String path){
        this.partName = partName;
        this.path = path;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(path);
    }

    @Override
    public String getContentType() {
        return URLConnection.guessContentTypeFromName(path);
    }

    @Override
    public String getName() {
        return partName;
    }

    @Override
    public String getSubmittedFileName() {
        return new File(path).getName();
    }

    @Override
    public long getSize() {
        return new File(path).getTotalSpace();
    }

    @Override
    public void write(String s) throws IOException {
        // Not currently used
    }

    @Override
    public void delete() throws IOException {
        // Not currently used
    }

    @Override
    public String getHeader(String s) {
        return "";
    }

    @Override
    public Collection<String> getHeaders(String s) {
        return List.of();
    }

    @Override
    public Collection<String> getHeaderNames() {
        return List.of();
    }
}
