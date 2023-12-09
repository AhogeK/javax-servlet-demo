package com.ahogek.servlet;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author AhogeK ahogek@gmail.com
 * @since 2023-12-09 16:35:04
 */
class FormServletLiveTest {

    @Test
    void whenPostRequestUsingHttpClient_thenCorrect() throws Exception {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost method = new HttpPost("http://localhost:8080/calculateServlet");
            List<BasicNameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("height", String.valueOf(2)));
            nvps.add(new BasicNameValuePair("weight", String.valueOf(80)));

            method.setEntity(new UrlEncodedFormEntity(nvps));
            CloseableHttpResponse response = client.execute(method);

            assertEquals("Success", response.getFirstHeader("Test").getValue());
            assertEquals("20.0", response.getFirstHeader("BMI").getValue());
        }
    }
}
