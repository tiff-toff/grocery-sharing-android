/*
 * CREDIT: http://zetcode.com/java/getpostrequest/
 * Minor adjustments made
 *
 */

package com.example.grocerysharing.conveniences;

import android.annotation.TargetApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@TargetApi(19)
public class GetRequestHTTP {

    private static HttpURLConnection con;

    public static String sendGetRequest(String url, String urlParameters) throws IOException {

        try {

            String fullURL = url + "?" + urlParameters;

            URL myurl = new URL(fullURL);
            con = (HttpURLConnection) myurl.openConnection();

            con.setRequestMethod("GET");

            StringBuilder content;

            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {

                String line;
                content = new StringBuilder();

                while ((line = in.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }

            return content.toString();

        } finally {

            con.disconnect();
        }
    }
}