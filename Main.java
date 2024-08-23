package org.example;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Main {
    private static CloseableHttpClient clients= HttpClients.createDefault();
    public static void main(String[] args) {
       setTaskDone("Daniel Patachov","clean my room");
    }
    public static void register(String id){
try {
    URI uri = new URIBuilder("https://app.seker.live/fm1/register")
            .setParameter("id", id)
            .build();
    HttpPost request = new HttpPost(uri);
    CloseableHttpResponse response = clients.execute(request);
    System.out.println(EntityUtils.toString(response.getEntity()));
} catch (URISyntaxException e) {
    throw new RuntimeException(e);
} catch (IOException e) {
    throw new RuntimeException(e);
}

    }
    public static void getTasks(String id){
        try {
            URI uri = new URIBuilder("https://app.seker.live/fm1/get-tasks")
                    .setParameter("id", id)
                    .build();
            HttpGet request = new HttpGet(uri);
            CloseableHttpResponse response = clients.execute(request);
            System.out.println(EntityUtils.toString(response.getEntity()));
        }catch (URISyntaxException e) {
            throw new RuntimeException();
        }catch (IOException e){
            throw new RuntimeException();
        }

    }
    public static void addTasks(String id,String text){
        try {
            URI uri = new URIBuilder("https://app.seker.live/fm1/add-task")
                    .setParameter("id", id)
                    .setParameter("text", text)
                    .build();
            HttpPost request = new HttpPost(uri);
            CloseableHttpResponse response = clients.execute(request);
            System.out.println(EntityUtils.toString(response.getEntity()));
        }catch (URISyntaxException e) {
            throw new RuntimeException();
        }catch (IOException e){
            throw new RuntimeException();
        }
    }
    public static void setTaskDone(String id, String text){
        try {
            URI uri = new URIBuilder("https://app.seker.live/fm1/set-task-done")
                    .setParameter("id", id)
                    .setParameter("text", text)
                    .build();
            HttpPost request = new HttpPost(uri);
            CloseableHttpResponse response = clients.execute(request);
            System.out.println(EntityUtils.toString(response.getEntity()));
        }catch (URISyntaxException e) {
            throw new RuntimeException();
        }catch (IOException e){
            throw new RuntimeException();
        }
    }
    }
