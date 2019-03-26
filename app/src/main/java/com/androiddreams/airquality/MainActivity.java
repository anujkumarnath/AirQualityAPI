package com.androiddreams.airquality;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import static java.lang.String.valueOf;

public class MainActivity extends AppCompatActivity {
    private final String LOG_TAG = this.getClass().getSimpleName();
    //private final String jsonString = "{\"meta\":{\"name\":\"openaq-api\",\"license\":\"CC BY 4.0\",\"website\":\"https://docs.openaq.org/\",\"page\":1,\"limit\":2,\"found\":335},\"results\":[{\"location\":\"AAQMS Karve Road Pune\",\"city\":\"Pune\",\"country\":\"IN\",\"distance\":10046042.46102848,\"measurements\":[{\"parameter\":\"co\",\"value\":1220,\"lastUpdated\":\"2018-02-22T03:00:00.000Z\",\"unit\":\"µg/m³\",\"sourceName\":\"CPCB\",\"averagingPeriod\":{\"value\":0.25,\"unit\":\"hours\"}},{\"parameter\":\"pm10\",\"value\":165,\"lastUpdated\":\"2018-02-22T03:00:00.000Z\",\"unit\":\"µg/m³\",\"sourceName\":\"CPCB\",\"averagingPeriod\":{\"value\":0.25,\"unit\":\"hours\"}},{\"parameter\":\"pm25\",\"value\":66,\"lastUpdated\":\"2018-02-22T03:00:00.000Z\",\"unit\":\"µg/m³\",\"sourceName\":\"CPCB\",\"averagingPeriod\":{\"value\":0.25,\"unit\":\"hours\"}},{\"parameter\":\"so2\",\"value\":22.85,\"lastUpdated\":\"2018-02-22T03:00:00.000Z\",\"unit\":\"µg/m³\",\"sourceName\":\"CPCB\",\"averagingPeriod\":{\"value\":0.25,\"unit\":\"hours\"}},{\"parameter\":\"o3\",\"value\":15.76,\"lastUpdated\":\"2018-02-22T03:00:00.000Z\",\"unit\":\"µg/m³\",\"sourceName\":\"CPCB\",\"averagingPeriod\":{\"value\":0.25,\"unit\":\"hours\"}}],\"coordinates\":{\"latitude\":18.497484,\"longitude\":73.81349}},{\"location\":\"AP Tirumala\",\"city\":\"Chittoor\",\"country\":\"IN\",\"measurements\":[{\"parameter\":\"so2\",\"value\":5.7,\"lastUpdated\":\"2016-06-30T13:00:00.000Z\",\"unit\":\"µg/m³\",\"sourceName\":\"CPCB\",\"averagingPeriod\":{\"value\":0.25,\"unit\":\"hours\"}},{\"parameter\":\"no2\",\"value\":40.3,\"lastUpdated\":\"2016-07-04T06:15:00.000Z\",\"unit\":\"µg/m³\",\"sourceName\":\"CPCB\",\"averagingPeriod\":{\"value\":0.25,\"unit\":\"hours\"}},{\"parameter\":\"o3\",\"value\":34.07,\"lastUpdated\":\"2016-07-04T06:15:00.000Z\",\"unit\":\"µg/m³\",\"sourceName\":\"CPCB\",\"averagingPeriod\":{\"value\":0.25,\"unit\":\"hours\"}},{\"parameter\":\"no2\",\"value\":49.9,\"lastUpdated\":\"2016-06-30T13:00:00.000Z\",\"unit\":\"µg/m³\",\"sourceName\":\"CPCB\",\"averagingPeriod\":{\"value\":0.25,\"unit\":\"hours\"}},{\"parameter\":\"o3\",\"value\":32.14,\"lastUpdated\":\"2016-06-30T13:00:00.000Z\",\"unit\":\"µg/m³\",\"sourceName\":\"CPCB\",\"averagingPeriod\":{\"value\":0.25,\"unit\":\"hours\"}},{\"parameter\":\"so2\",\"value\":6.9,\"lastUpdated\":\"2016-07-04T06:15:00.000Z\",\"unit\":\"µg/m³\",\"sourceName\":\"CPCB\",\"averagingPeriod\":{\"value\":0.25,\"unit\":\"hours\"}},{\"parameter\":\"co\",\"value\":760,\"lastUpdated\":\"2016-07-04T06:15:00.000Z\",\"unit\":\"µg/m³\",\"sourceName\":\"CPCB\",\"averagingPeriod\":{\"value\":0.25,\"unit\":\"hours\"}},{\"parameter\":\"co\",\"value\":630,\"lastUpdated\":\"2016-06-30T13:00:00.000Z\",\"unit\":\"µg/m³\",\"sourceName\":\"CPCB\",\"averagingPeriod\":{\"value\":0.25,\"unit\":\"hours\"}},{\"parameter\":\"pm10\",\"value\":12,\"lastUpdated\":\"2016-07-04T06:15:00.000Z\",\"unit\":\"µg/m³\",\"sourceName\":\"CPCB\",\"averagingPeriod\":{\"value\":0.25,\"unit\":\"hours\"}},{\"parameter\":\"pm25\",\"value\":38,\"lastUpdated\":\"2016-07-04T06:15:00.000Z\",\"unit\":\"µg/m³\",\"sourceName\":\"CPCB\",\"averagingPeriod\":{\"value\":0.25,\"unit\":\"hours\"}},{\"parameter\":\"pm10\",\"value\":79,\"lastUpdated\":\"2016-06-30T13:00:00.000Z\",\"unit\":\"µg/m³\",\"sourceName\":\"CPCB\",\"averagingPeriod\":{\"value\":0.25,\"unit\":\"hours\"}},{\"parameter\":\"pm25\",\"value\":33,\"lastUpdated\":\"2016-06-30T13:00:00.000Z\",\"unit\":\"µg/m³\",\"sourceName\":\"CPCB\",\"averagingPeriod\":{\"value\":0.25,\"unit\":\"hours\"}}]}]}";
    private final String REQUEST_URL = "https://api.openaq.org/v1/latest?country=IN&limit=100";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AirTask task = new AirTask();
        task.execute();

    }

    private URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "BAD URL STRING", e);
            return url;
        }
        return url;
    }

    private class AirTask extends AsyncTask<URL, Void, ArrayList<DataItem>> {

        @Override
        protected ArrayList<DataItem> doInBackground(URL... strings) {
            ArrayList<DataItem> dataList;
            URL url = createUrl(REQUEST_URL);

            String jsonResponse = "";
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                Log.d(LOG_TAG, "Couldn't make HTTP request", e);
            }

            dataList = getDataList(jsonResponse);
            return dataList;
        }

        @Override
        protected void onPostExecute(ArrayList<DataItem> dataItems) {
            if (dataItems.size() == 0)
                return;

            updateUi(dataItems);
        }
    }

    void updateUi(ArrayList<DataItem> dataItems) {
        DataAdapter dataAdapter = new DataAdapter(this, dataItems);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(dataAdapter);
    }

    private String makeHttpRequest(URL url) throws IOException{
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }

        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "response code" + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error retrieving air quality json response");
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException exception) {
                    Log.e(LOG_TAG, "Inputstream error");
                }
            }
        }
        return jsonResponse;
    }

    private String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder builder = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                builder.append(line);
                line = reader.readLine();
            }
        }
        return builder.toString();
    }

    public ArrayList<DataItem> getDataList(String jsonString) {
        JSONObject root;
        ArrayList<DataItem> dataItems = new ArrayList<>();
        try {
            root = new JSONObject(jsonString);
            JSONArray results = root.optJSONArray("results");

            for (int i = 0; i < results.length() ; i++) {
               JSONObject result = results.optJSONObject(i);
                String location = result.optString("location");
                String city = result.optString("city");

                JSONArray measurements = result.optJSONArray("measurements");
                JSONObject m = measurements.optJSONObject(0);
                Log.e("Object is not", " null");

                String[] contents = new String[5];
                for (int j =0; j < 5; j++) {
                    JSONObject measurement = measurements.optJSONObject(j);
                    if (measurement != null) {
                        contents[j] = measurement.optString("value");
                    }
                    else
                        contents[j] = "N/A";
                }

                dataItems.add(new DataItem(location, city, contents[0], contents[1], contents[2], contents[3], contents[4]));
            }

            return dataItems;
        } catch (JSONException e) {
            Log.e(LOG_TAG,"JSON", e);
        }

        return null;
    }
}
