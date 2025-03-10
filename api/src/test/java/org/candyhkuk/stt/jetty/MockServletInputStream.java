package org.candyhkuk.stt.jetty;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MockServletInputStream extends ServletInputStream {

    private int lastIndexRetrieved = -1;
    private ReadListener readListener = null;
    private byte[] bais;

    public MockServletInputStream(ByteArrayInputStream bais){
        this.bais = bais.readAllBytes();
    }

    @Override
    public boolean isFinished() {
        return (lastIndexRetrieved == bais.length -1);
    }

    @Override
    public boolean isReady() {
        return isFinished();
    }

    @Override
    public void setReadListener(ReadListener readListener) {
        this.readListener = readListener;
        if(!isFinished()){
            try {
                readListener.onDataAvailable();
            } catch(IOException e){
                readListener.onError(e);
            }
        } else {
            try {
                readListener.onAllDataRead();
            } catch(IOException e){
                readListener.onError(e);
            }
        }
    }

    @Override
    public int read() throws IOException {
        int i;
        if(!isFinished()){
            i = bais[lastIndexRetrieved+1];
            lastIndexRetrieved++;
            if(isFinished() && readListener != null){
                try {
                    readListener.onAllDataRead();
                } catch(IOException e){
                    readListener.onError(e);
                    throw e;
                }
            }
            return i;
        } else {
            return -1;
        }
    }

    @Override
    public int available() throws IOException {
        return bais.length-lastIndexRetrieved-1;
    }

    @Override
    public void close() throws IOException {
        lastIndexRetrieved = bais.length -1;
    }
}
