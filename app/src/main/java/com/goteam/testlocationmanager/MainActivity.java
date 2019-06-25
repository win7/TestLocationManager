package com.goteam.testlocationmanager;

import android.Manifest;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements LocationListener {

    String TAG = "Gooo";

    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    // GPSTracker class
    GPSTracker gps;
    Location mLastLocation;


    TextView tw_location;

    Calendar c = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tw_location = findViewById(R.id.textView_location);
        tw_location.setMovementMethod(new ScrollingMovementMethod());

        // create class object
        gps = new GPSTracker(getApplicationContext(), this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show(); */

                gps.getLocation();
                // check if GPS enabled
                if(gps.canGetLocation()){
                    mLastLocation = gps.getLocation();

                    if (mLastLocation != null) {
                        Toast.makeText(getApplicationContext(), mLastLocation.getLatitude() + " " + mLastLocation.getLongitude() + " " +
                                mLastLocation.getSpeed() + " " + mLastLocation.getBearing(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "null", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }
            }
        });

        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != getPackageManager().PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will execute every time, else your else part will work
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //-------------------------------------------------
    //  Lifecycle
    //-------------------------------------------------
    @Override
    protected void onStart() {
        Log.e(TAG, "OnStart");
        super.onStart();
    }

    @Override
    protected void onPause() {
        Log.e(TAG, "OnPause");
        super.onPause();
    }

    @Override
    public void onResume() {
        Log.e(TAG, "OnResume");
        super.onResume();
    }

    @Override
    protected void onStop() {
        Log.e(TAG, "OnStop");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "OnDestroy");
        super.onDestroy();

        gps.stopUsingGPS();
    }

    //-------------------------------------------------
    //  GPS
    //-------------------------------------------------
    @Override
    public void onLocationChanged(Location location) {
        c = Calendar.getInstance();
        String strDate = sdf.format(c.getTime());

        Log.e(TAG, "onLocationChanged " + strDate + " " + location.getLatitude() + " " + location.getLongitude() + " " +
                location.getSpeed() + " " + location.hasSpeed() + " " +
                location.getBearing() + " " + location.hasBearing());

        tw_location.setText(strDate + " " + location.getLatitude() + " " + location.getLongitude() + " " +
                location.getSpeed() + " " + location.hasSpeed() + " " +
                location.getBearing() + " " + location.hasBearing() + "\n" + tw_location.getText());

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.e(TAG, "onStatusChanged");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.e(TAG, "onProviderEnabled");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.e(TAG, "onProviderDisabled");
    }
}
