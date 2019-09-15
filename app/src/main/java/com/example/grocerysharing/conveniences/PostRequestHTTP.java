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
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

@TargetApi(19)
public class PostRequestHTTP {

    private static HttpURLConnection con;

    public static String sendPostRequest(String url, String urlParameters) throws IOException {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Content-type", "x-www-form-urlencoded");

            out = new PrintWriter(conn.getOutputStream());
//            out.print(urlParameters);
//            out.flush();

           return "pear";
//            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            String line;
//            while ((line = in.readLine()) != null) {
//                result += line;
//            }
        } catch (Exception e) {
            return e.toString(); //e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                } if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        //return result;
    }
}