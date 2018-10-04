package edu.ung.csci.csci3360.tabegaz.weatherapp;;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

public class MainActivityController extends AppCompatActivity{
    //public static Context appContext = this.getApplicationContext();

    static ImageView conditionImageView;
    static TextView dayTextView;
    static TextView lowTempTextView;
    static TextView highTempTextView;
    static TextView humidityTextView;
    static TextView weatherTextView;
    static TextView dayTempTextView;
    private EditText editTextCity;
    private EditText editTextZip;
    private EditText editTextLat;
    private EditText editTextLong;

    Location loc = new Location("");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        conditionImageView = findViewById(R.id.conditionImageView);
        dayTextView = findViewById(R.id.dayTextView);
        lowTempTextView = findViewById(R.id.lowTempTextView);
        highTempTextView = findViewById(R.id.highTempTextView);
        humidityTextView = findViewById(R.id.humidityTextView);
        weatherTextView = findViewById(R.id.weatherTextView);
        dayTempTextView = findViewById(R.id.dayTempTextView);
        editTextCity = findViewById(R.id.editTextCity);
        editTextZip = findViewById(R.id.editTextZip);
        editTextLat = findViewById(R.id.editTextLat);
        editTextLong= findViewById(R.id.editTextLong);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = editTextCity.getText().toString();
                String zip = editTextZip.getText().toString();
                String lat = editTextLat.getText().toString();
                String lon = editTextLong.getText().toString();

                DownloadJSON task = new DownloadJSON();

                try {
                    if(!lat.equals("") && !lon.equals("")){
                        task.execute("http://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&APPID=2a44f430cc82ca859268585a358d672e&units=imperial");
                        Snackbar.make(view, "Showing Weather for: Lat: "+lat+" Lon: "+lon,Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    } else if(!zip.equals("")){
                        task.execute("http://api.openweathermap.org/data/2.5/weather?zip="+zip+"&APPID=2a44f430cc82ca859268585a358d672e&units=imperial");
                        Snackbar.make(view, "Showing Weather for: "+zip,Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    } else if(!city.equals("")){
                        task.execute("http://api.openweathermap.org/data/2.5/weather?q="+city+"&APPID=2a44f430cc82ca859268585a358d672e&units=imperial");
                        Snackbar.make(view, "Showing Weather for: "+city,Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    } else {
                        Snackbar.make(view, "Unable to Load Weather Data", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    }

                } catch (Exception e) {
                    Snackbar.make(view, "Unable to Load Weather Data", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }

                editTextCity.setText("");
                editTextZip.setText("");
                editTextLat.setText("");
                editTextLong.setText("");

                //task.execute("http://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&APPID=2a44f430cc82ca859268585a358d672e&units=imperial");
                //Snackbar.make(view, "Weather for "+lat+", "+lon, Snackbar.LENGTH_SHORT)
                //        .setAction("Action", null).show();

                //task.execute("http://api.openweathermap.org/data/2.5/weather?zip="+zip+",us&APPID=2a44f430cc82ca859268585a358d672e&units=imperial");
                //Snackbar.make(view, "Weather for "+ zip, Snackbar.LENGTH_SHORT)
                //        .setAction("Action", null).show();

                ///task.execute("http://api.openweathermap.org/data/2.5/weather?q=" + city + "&APPID=2a44f430cc82ca859268585a358d672e&units=imperial");
                //Snackbar.make(view, "Weather for " + city, Snackbar.LENGTH_SHORT)
                //        .setAction("Action", null).show();
            }
        });
    }
}


