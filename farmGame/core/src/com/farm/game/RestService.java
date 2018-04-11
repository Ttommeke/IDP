package com.farm.game;

import com.badlogic.gdx.Gdx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RestService {
    private String $urlRoot = "https://bp-website.herokuapp.com/";

    public String getRequest(String route) {
        try {
            URL url = new URL($urlRoot + route);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output = br.readLine();

            conn.disconnect();

            return output;

        } catch (MalformedURLException e) {
            Gdx.app.log("restService", e.getMessage());
            return null;
        } catch (IOException e) {
            Gdx.app.log("restService", e.getMessage());
            return null;
        }

    }

    public String postRequest(String route, String input) {
        try {
            URL url = new URL($urlRoot + route);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != 200) {
                if(conn.getResponseCode() == 403) {
                    return "Invalid Credentials";
                }
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output = br.readLine();

            conn.disconnect();

            return output;

        } catch (MalformedURLException e) {
            Gdx.app.log("restService", e.getMessage());
            return null;
        } catch (IOException e) {
            Gdx.app.log("restService", e.getMessage());
            return null;
        }
    }
}
