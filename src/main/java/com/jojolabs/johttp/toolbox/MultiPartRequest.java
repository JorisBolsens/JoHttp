package com.jojolabs.johttp.toolbox;

import com.jojolabs.johttp.AuthFailureError;
import com.jojolabs.johttp.Request;
import com.jojolabs.johttp.Response;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by joris on 1/18/16.
 */
public abstract class MultiPartRequest<T> extends Request<T> {
    private final Response.Listener<T> mListener;
    private String boundary = UUID.randomUUID().toString();
    private String ContentType = "multipart/form-data";
    private List<Part> parts = new ArrayList<>();
    private Map<String, String> headers = new HashMap<>();

    public MultiPartRequest(int method, String url, Response.Listener<T> listener,
                            Response.ErrorListener errorListener){
        super(method, url, errorListener);
        mListener = listener;
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers;
    }

    @Override
    public void writeToStream(OutputStream out) throws IOException {
        String doubleDash = "--";
        String delim = boundary;
        String crlf = "\r\n";
        for(Part part : parts) {
            //Signal start of part by sending --boundary
            out.write(crlf.getBytes());
            out.write(doubleDash.getBytes());
            out.write(delim.getBytes());
            out.write(crlf.getBytes());
            //get headers, loop through and write them first
            Map<String, String> headers = part.getHeaders();
            if (headers.size() > 0){
                for(Map.Entry<String, String> entry : headers.entrySet()) {
                    out.write(entry.getKey().getBytes());
                    out.write(": ".getBytes());
                    out.write(entry.getValue().getBytes());
                }
            }
            //check if we are writing an InputStream or String
            if(part.isStream()){
                out.write("Content-Disposition: form-data; name=\"".getBytes());
                out.write(part.getKey().getBytes());
                out.write("\"".getBytes());
                out.write(crlf.getBytes());
                out.write("Content-Type: application/octet-stream".getBytes());
                out.write(crlf.getBytes());
                out.write(crlf.getBytes());
                //send in 4k pieces
                byte[] buf = new byte[4096];
                InputStream in = new FileInputStream(part.getFile());
                while(in.read(buf) > -1) {
                    out.write(buf);
                }
                out.flush();
                in.close();
            } else {
                out.write("Content-Disposition: form-data; name=\"".getBytes());
                out.write(part.getKey().getBytes());
                out.write("\"".getBytes());
                out.write(crlf.getBytes());
                out.write(("Content-Length: "+part.getValue().length()).getBytes());
                out.write(crlf.getBytes());
                out.write(crlf.getBytes());
                out.write(part.getValue().getBytes());
                out.flush();
            }
        }
        //signal end of multipart by sending "--boundary--"
        out.write(crlf.getBytes());
        out.write(doubleDash.getBytes());
        out.write(delim.getBytes());
        out.write(doubleDash.getBytes());
        out.write(crlf.getBytes());
    }

    @Override
    public boolean canStream() {
        return true;
    }

    @Override
    public String getBodyContentType() {
        return ContentType +
                "; boundary="+boundary;
    }

    public void addPart(Part part) {
        parts.add(part);
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public String getBoundry() {
        return boundary;
    }

    public void setBoundry(String boundry) {
        this.boundary = boundry;
    }

    public static class Part {
        private Map<String, String> headers = new HashMap<>();
        private boolean isStream;
        private String key;
        private String value;
        private File file;

        public Part(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public  Part(String name, File content) {
            key = name;
            file = content;
            isStream = true;
        }

        public Map<String, String> getHeaders() {
            return headers;
        }

        public void addHeader(String key, String value) {
            headers.put(key, value);
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        public File getFile() {
            return file;
        }

        protected boolean isStream() {
            return isStream;
        }
    }
}
