package com.androiddreams.airquality;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static java.lang.String.valueOf;

public class MainActivity extends AppCompatActivity {
    private final String LOG_TAG = this.getClass().getSimpleName();
    private final String jsonString = "{\"meta\":{\"name\":\"openaq-api\",\"license\":\"CC BY 4.0\",\"website\":\"https://docs.openaq.org/\",\"page\":1,\"limit\":2,\"found\":335},\"results\":[{\"location\":\"AAQMS Karve Road Pune\",\"city\":\"Pune\",\"country\":\"IN\",\"distance\":10046042.46102848,\"measurements\":[{\"parameter\":\"co\",\"value\":1220,\"lastUpdated\":\"2018-02-22T03:00:00.000Z\",\"unit\":\"µg/m³\",\"sourceName\":\"CPCB\",\"averagingPeriod\":{\"value\":0.25,\"unit\":\"hours\"}},{\"parameter\":\"pm10\",\"value\":165,\"lastUpdated\":\"2018-02-22T03:00:00.000Z\",\"unit\":\"µg/m³\",\"sourceName\":\"CPCB\",\"averagingPeriod\":{\"value\":0.25,\"unit\":\"hours\"}},{\"parameter\":\"pm25\",\"value\":66,\"lastUpdated\":\"2018-02-22T03:00:00.000Z\",\"unit\":\"µg/m³\",\"sourceName\":\"CPCB\",\"averagingPeriod\":{\"value\":0.25,\"unit\":\"hours\"}},{\"parameter\":\"so2\",\"value\":22.85,\"lastUpdated\":\"2018-02-22T03:00:00.000Z\",\"unit\":\"µg/m³\",\"sourceName\":\"CPCB\",\"averagingPeriod\":{\"value\":0.25,\"unit\":\"hours\"}},{\"parameter\":\"o3\",\"value\":15.76,\"lastUpdated\":\"2018-02-22T03:00:00.000Z\",\"unit\":\"µg/m³\",\"sourceName\":\"CPCB\",\"averagingPeriod\":{\"value\":0.25,\"unit\":\"hours\"}}],\"coordinates\":{\"latitude\":18.497484,\"longitude\":73.81349}},{\"location\":\"AP Tirumala\",\"city\":\"Chittoor\",\"country\":\"IN\",\"measurements\":[{\"parameter\":\"so2\",\"value\":5.7,\"lastUpdated\":\"2016-06-30T13:00:00.000Z\",\"unit\":\"µg/m³\",\"sourceName\":\"CPCB\",\"averagingPeriod\":{\"value\":0.25,\"unit\":\"hours\"}},{\"parameter\":\"no2\",\"value\":40.3,\"lastUpdated\":\"2016-07-04T06:15:00.000Z\",\"unit\":\"µg/m³\",\"sourceName\":\"CPCB\",\"averagingPeriod\":{\"value\":0.25,\"unit\":\"hours\"}},{\"parameter\":\"o3\",\"value\":34.07,\"lastUpdated\":\"2016-07-04T06:15:00.000Z\",\"unit\":\"µg/m³\",\"sourceName\":\"CPCB\",\"averagingPeriod\":{\"value\":0.25,\"unit\":\"hours\"}},{\"parameter\":\"no2\",\"value\":49.9,\"lastUpdated\":\"2016-06-30T13:00:00.000Z\",\"unit\":\"µg/m³\",\"sourceName\":\"CPCB\",\"averagingPeriod\":{\"value\":0.25,\"unit\":\"hours\"}},{\"parameter\":\"o3\",\"value\":32.14,\"lastUpdated\":\"2016-06-30T13:00:00.000Z\",\"unit\":\"µg/m³\",\"sourceName\":\"CPCB\",\"averagingPeriod\":{\"value\":0.25,\"unit\":\"hours\"}},{\"parameter\":\"so2\",\"value\":6.9,\"lastUpdated\":\"2016-07-04T06:15:00.000Z\",\"unit\":\"µg/m³\",\"sourceName\":\"CPCB\",\"averagingPeriod\":{\"value\":0.25,\"unit\":\"hours\"}},{\"parameter\":\"co\",\"value\":760,\"lastUpdated\":\"2016-07-04T06:15:00.000Z\",\"unit\":\"µg/m³\",\"sourceName\":\"CPCB\",\"averagingPeriod\":{\"value\":0.25,\"unit\":\"hours\"}},{\"parameter\":\"co\",\"value\":630,\"lastUpdated\":\"2016-06-30T13:00:00.000Z\",\"unit\":\"µg/m³\",\"sourceName\":\"CPCB\",\"averagingPeriod\":{\"value\":0.25,\"unit\":\"hours\"}},{\"parameter\":\"pm10\",\"value\":12,\"lastUpdated\":\"2016-07-04T06:15:00.000Z\",\"unit\":\"µg/m³\",\"sourceName\":\"CPCB\",\"averagingPeriod\":{\"value\":0.25,\"unit\":\"hours\"}},{\"parameter\":\"pm25\",\"value\":38,\"lastUpdated\":\"2016-07-04T06:15:00.000Z\",\"unit\":\"µg/m³\",\"sourceName\":\"CPCB\",\"averagingPeriod\":{\"value\":0.25,\"unit\":\"hours\"}},{\"parameter\":\"pm10\",\"value\":79,\"lastUpdated\":\"2016-06-30T13:00:00.000Z\",\"unit\":\"µg/m³\",\"sourceName\":\"CPCB\",\"averagingPeriod\":{\"value\":0.25,\"unit\":\"hours\"}},{\"parameter\":\"pm25\",\"value\":33,\"lastUpdated\":\"2016-06-30T13:00:00.000Z\",\"unit\":\"µg/m³\",\"sourceName\":\"CPCB\",\"averagingPeriod\":{\"value\":0.25,\"unit\":\"hours\"}}]}]}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataAdapter dataAdapter = new DataAdapter(this, getDataList(jsonString));
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(dataAdapter);
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
                    contents[j] = measurement.optString("value");
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
