package com.example.managementapp.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Server {

    private static final String SERVER_URL = "http://172.20.10.2:5000/getUserCredentials?username=%s&password=%s";

    public static void getUserCredentials(String username, String password) {
        try {
            // Create the URL with the username and password as query parameters
            URL url = new URL(String.format(SERVER_URL, username, password));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Set the request method to GET
            conn.setRequestMethod("GET");

            // Send the request and read the response
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Handle the retrieved data (which is in the response string)
            String retrievedData = response.toString();
            // Do something with the retrieved data...

        } catch (IOException e) {
            // Handle any exceptions...
        }
    }
}