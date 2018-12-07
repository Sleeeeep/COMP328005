import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class DBClass {
    final String dbURL = "http://10.0.2.2:8080/mDB/JsonTest.jsp";
    HttpURLConnection urlConnection = null;

    private String[] getJsonData = {"", ""};

    class QueryTask extends AsyncTask<String, Void, String> {
        String sendmsg = null;

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(dbURL);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
                urlConnection.setRequestProperty("Context_Type", "application/x-www-form-urlencoded;charset=UTF-8");

                OutputStreamWriter osw = new OutputStreamWriter(urlConnection.getOutputStream());
                sendmsg = "?hey=abc&really=123";
                osw.write(sendmsg);
                osw.flush();

                if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK)
                    Log.i("DB", "connection error");
                else {

                    BufferedReader bufreader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

                    String line = null;
                    String page = "";

                    while ((line = bufreader.readLine()) != null) {
                        page += line;
                    }

                    JSONObject json = new JSONObject(page);
                    JSONArray jArr = json.getJSONArray("dataSend");

                    for (int i = 0; i < jArr.length(); i++) {
                        json = jArr.getJSONObject(i);
                        getJsonData[0] = json.getString("hey");
                        getJsonData[1] = json.getString("really");
                    }
                    String data = "받은 데이터 : " + getJsonData[0] + " " + getJsonData[1];
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public void onClickButton(View v) {
        try {
            String result;
            QueryTask task = new QueryTask();
            result = task.execute("", "").get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
