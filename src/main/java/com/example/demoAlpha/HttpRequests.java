package com.example.demoAlpha;

import org.apache.commons.codec.Charsets;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpRequests {
    private static final HttpClient HTTP_CLIENT = HttpClients.createDefault();

    public static HttpResponse runGetRequest(String url, Map<String, Object> header) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        header.forEach((k, v) -> httpGet.addHeader(k, (String) v));
        HttpResponse httpResponse = HTTP_CLIENT.execute(httpGet);
        return httpResponse;
    }

    public static String getResponseBody(HttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        Header encodingHeader = entity.getContentEncoding();
        Charset encoding = encodingHeader == null ? StandardCharsets.UTF_8 :
                Charsets.toCharset(encodingHeader.getValue());

        return EntityUtils.toString(entity, encoding);
    }
}
