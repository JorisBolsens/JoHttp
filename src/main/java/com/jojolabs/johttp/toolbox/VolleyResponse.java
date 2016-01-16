package com.jojolabs.johttp.toolbox;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by joris on 1/16/16.
 */
public class VolleyResponse {
    private int statusCode;
    private InputStream content;
    private int contentLength;
    private String contentType;
    private String contentEncoding;
    private Map<String, List<String>> headers;

    public InputStream getContent() {
        return content;
    }

    public void setContent(InputStream content) {
        this.content = content;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentlength) {
        this.contentLength = contentlength;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentEncoding() {
        return contentEncoding;
    }

    public void setContentEncoding(String contentEngoding) {
        this.contentEncoding = contentEngoding;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    public void addHeader(String key, String value) {
        if (headers == null)
            headers = new HashMap<>();

        List<String> vals = new ArrayList<>(1);
        vals.add(value);
        headers.put(key, vals);
    }

    public void addHeader(String key, List<String> values){
        if (headers == null)
            headers = new HashMap<>();

        headers.put(key, values);
    }

    public void addHeader(Map.Entry<String, List<String>> header){
        if (headers == null)
            headers = new HashMap<>();

        headers.put(header.getKey(), header.getValue());
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
