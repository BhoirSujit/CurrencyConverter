package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.Gson;

class CCApi {

    public Map<String, String> getCurrencies() {
        Map<String, String> currencyMap = new HashMap<>();
        String url = "https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies.json";
        try {
            HttpURLConnection conn = (HttpsURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            reader.close();

            // Deserialize JSON directly to Map
            Gson gson = new Gson();
            currencyMap = gson.fromJson(jsonBuilder.toString(), Map.class);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return currencyMap;
    }

    public Map<String, Object> getCurrencyRate(String cur) {

        Map<String, Object> currencyMap = new HashMap<>();
        String url = "https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies/" + cur + ".json";

        try {
            HttpURLConnection conn = (HttpsURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            reader.close();

            // Deserialize JSON directly to Map
            Gson gson = new Gson();
            currencyMap = gson.fromJson(jsonBuilder.toString(), Map.class);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return currencyMap;
    }
}
