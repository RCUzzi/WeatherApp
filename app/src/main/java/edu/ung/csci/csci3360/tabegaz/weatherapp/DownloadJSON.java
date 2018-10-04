package edu.ung.csci.csci3360.tabegaz.weatherapp;

import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.AsyncTask;
import android.test.mock.MockContext;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

public class DownloadJSON extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... urls){
        String result = "";
        HttpURLConnection urlConnection = null;
        URL url = null;
        try {
            url = new URL(urls[0]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            urlConnection = (HttpURLConnection)url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String read = null;
            StringBuilder output = new StringBuilder();
            while((read = reader.readLine())!=null){
                output.append(read);
            }
            return output.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return  null; }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Date date = new Date();

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject jsonData = new JSONObject(jsonObject.getString("main"));
            JSONArray jsonWeather = jsonObject.getJSONArray("weather");
            JSONObject weatherObj = jsonWeather.getJSONObject(0);

            double temperatureMin = Double.parseDouble(jsonData.getString("temp_min"));
            double temperatureMax = Double.parseDouble(jsonData.getString("temp_max"));
            double humidity = Double.parseDouble(jsonData.getString("humidity"));

            //int temperatureMinFarh = (int)(9/5 * (temperatureMin - 273)) + 32;
            //int temperatureMaxFarh= (int)(9/5 * (temperatureMax - 273)) + 32;
            MainActivityController.lowTempTextView.setText(String.valueOf("Low Temp: "+ temperatureMin));
            MainActivityController.highTempTextView.setText(String.valueOf(" High Temp: " + temperatureMax));
            MainActivityController.humidityTextView.setText(String.valueOf(" Humidity: "+ humidity));
            MainActivityController.conditionImageView.setImageResource(R.drawable.condition); // R.drawable.condition
            MainActivityController.dayTextView.setText(android.text.format.DateFormat.format("EEEE", date) +" ");
            MainActivityController.dayTempTextView.setText(jsonData.getString("temp"));
            MainActivityController.weatherTextView.setText(weatherObj.get("description").toString());

            //Picasso.with(MainActivityController.getContext()).load("http://openweathermap.org/img/w/"+weatherObj.getString("icon")+".png").into(MainActivityController.conditionImageView);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}