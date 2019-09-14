/*
* CREDIT: http://zetcode.com/java/getpostrequest/
* Minor adjustments made
*
*/

package com.example.grocerysharing.conveniences;

import android.annotation.TargetApi;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@TargetApi(19)
public class PostRequestHTTP {

    private static HttpURLConnection con;

    public static String sendPostRequest(String url, String urlParameters) throws IOException {

        try {

            URL myurl = new URL(url);
            byte[] parameterBytes = urlParameters.getBytes(StandardCharsets.UTF_8);
            con = (HttpURLConnection) myurl.openConnection();

            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Java client");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            try (OutputStream os = con.getOutputStream()) {
                os.write(parameterBytes);
            } catch (IOException ioe) {
                throw ioe;
            }

            StringBuilder response;

            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {

                String line;
                response = new StringBuilder();

                while ((line = in.readLine()) != null) {
                    response.append(line);
                    response.append(System.lineSeparator());
                }
            }

            return response.toString();
        } catch (IOException ioe) {
            throw ioe;
        } finally {
            con.disconnect();
        }
    }
}