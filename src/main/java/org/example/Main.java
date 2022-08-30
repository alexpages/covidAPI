package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        //String Builder for user input
        StringBuilder uriHttp = new StringBuilder();
        Scanner console = new Scanner(System.in);
        System.out.println("Please enter a valid country name: ");
        String country = console.nextLine();
        uriHttp.append("https://covid-193.p.rapidapi.com/statistics?country=");
        uriHttp.append(country.toLowerCase());

        //Calling function
        System.out.println(newCasesCountry(uriHttp.toString()));
    }

    public static String newCasesCountry(String uriHttp) throws IOException, InterruptedException {

        //HTTP Request to covid API
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uriHttp))
                .header("X-RapidAPI-Key", "2485024da0mshac7e7509945a67ep1735eajsna56b007928ad")
                .header("X-RapidAPI-Host", "covid-193.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

        //Parse JSON into object
        Root root = null;
        try {
            ObjectMapper om = new ObjectMapper();
            root = om.readValue(response.body(), Root.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return root.response.get(0).cases.mynew;
    }

}