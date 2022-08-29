package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        //HTTP Request to covid API
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://covid-193.p.rapidapi.com/statistics?country=spain"))
                .header("X-RapidAPI-Key", "2485024da0mshac7e7509945a67ep1735eajsna56b007928ad")
                .header("X-RapidAPI-Host", "covid-193.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        String resultados = response.body();
        System.out.println(response.body());

        //Parse JSON into object
        try{
            Gson gson = new Gson();

            Response response1 = gson.fromJson(response.body(), Response.class);
            System.out.println(response1);

            System.out.println(response1.getCases());
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}