/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tutorial.restapidemo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 *
 * @author makis
 */
public class AffableBeanRESTCaller {

    boolean useMockResponses = false;

    public static void main(String... args) {
        AffableBeanRESTCaller obj = new AffableBeanRESTCaller();
        obj.getCategory(2);
    }

    public void getCategory(int categoryId) {
        Response response = null;
        try {

            CloseableHttpClient httpClient = HttpClients.createDefault();

//            String userCredentials = "system\\s-sip-drive-uat" + ":" + "u$Zwe4(@";
//            String encoding = new Base64().encode(userCredentials.getBytes());
            HttpPost httpPost = new HttpPost("http://localhost:8080/AffableBean/webresources/categories");
//            httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encoding);

            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json;charset=UTF-8");                // prepare POST body

            String body = "{\"name\": \"test\"}";                // set POST body

            HttpEntity entity = new StringEntity(body);

            httpPost.setEntity(entity);
            CloseableHttpResponse httpPostResponse = httpClient.execute(httpPost);
            if (httpPostResponse.getStatusLine().getStatusCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP Error code : "
                        + httpPostResponse.getStatusLine().getStatusCode());
            }
            httpClient.close();

            Response.ResponseBuilder responseBuilder = Response.status(Response.Status.OK);
            responseBuilder.header("Content-Type", MediaType.APPLICATION_JSON);
            InputStreamReader in = new InputStreamReader(httpPostResponse.getEntity().getContent());
            BufferedReader br = new BufferedReader(in);
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
            br.close();

            System.out.println("POST JSON response content: " + content);
            response = responseBuilder.entity(content.toString()).build();

//            rmToolServiceResponse = new ObjectMapper().readValue(response.getEntity().toString(), RMToolServiceResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
