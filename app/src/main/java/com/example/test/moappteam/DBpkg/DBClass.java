package com.example.test.moappteam.DBpkg;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class DBClass {
    private String dbURL = null;
    static private HttpURLConnection urlConnection = null;

    public DBClass() {
        dbURL = "http://10.0.2.2:8080/mDB/JsonTest.jsp?";
    }

    public DBClass(String url) {
        dbURL = url;
    }

    public DBClass(boolean mode) {
        dbURL = "http://jaewoon.iptime.org:8080/mDB/JsonTest.jsp?";
    }

    public void setURL() {
        try {
            URL url = new URL(dbURL);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
            urlConnection.setRequestProperty("Context_Type", "application/x-www-form-urlencoded;charset=UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int writeURL(String msg) {
        try {
            OutputStreamWriter osw = new OutputStreamWriter(urlConnection.getOutputStream());
            osw.write(msg);
            osw.flush();

            return urlConnection.getResponseCode();
        } catch (Exception e) {
            e.printStackTrace();

            return HttpURLConnection.HTTP_BAD_REQUEST;
        }
    }

    public JSONObject getData() {
        JSONObject json = null;

        try {
            BufferedReader bufreader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            String line = null;
            String page = "";

            while ((line = bufreader.readLine()) != null) {
                page += line;
            }
            json = new JSONObject(page);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return json;
    }
}
