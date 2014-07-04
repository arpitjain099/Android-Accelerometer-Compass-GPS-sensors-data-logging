package com.example.javasamples;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.provider.Settings;
import android.content.DialogInterface;
import android.content.Intent;
import android.app.AlertDialog;
import android.app.Service;

public class gpstracker extends Service implements LocationListener{
private final Context mcontext;
boolean isGpsEnabled = false;
boolean isNetworkEnabled= false;
boolean canGetLocation= false;
Location location;
double latitude;
double longitude;
private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
protected LocationManager locationmanager;
public gpstracker(Context context) {
    this.mcontext = context;
    getLocation();
}
public Location getLocation() {
    try {
        locationmanager = (LocationManager) mcontext
                .getSystemService(LOCATION_SERVICE);

        // getting GPS status
        isGpsEnabled = locationmanager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        // getting network status
        isNetworkEnabled = locationmanager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGpsEnabled && !isNetworkEnabled) {
            // no network provider is enabled
        } else {
            this.canGetLocation = true;
            // First get location from Network Provider
            if (isNetworkEnabled) {
                locationmanager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                Log.d("Network", "Network");
                if (locationmanager != null) {
                    location = locationmanager
                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }
                }
            }
            // if GPS Enabled get lat/long using GPS Services
            if (isGpsEnabled) {
                if (location == null) {
                    locationmanager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("GPS Enabled", "GPS Enabled");
                    if (locationmanager != null) {
                        location = locationmanager
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return location;
}
public void stopUsingGPS(){
    if(locationmanager != null){
        locationmanager.removeUpdates(gpstracker.this);
    }       
}
 
/**
 * Function to get latitude
 * */
public double getLatitude(){
    if(location != null){
        latitude = location.getLatitude();
    }
     
    // return latitude
    return latitude;
}
 
/**
 * Function to get longitude
 * */
public double getLongitude(){
    if(location != null){
        longitude = location.getLongitude();
    }
     
    // return longitude
    return longitude;
}
 
/**
 * Function to check GPS/wifi enabled
 * @return boolean
 * */
public boolean canGetLocation() {
    return this.canGetLocation;
}
public void showSettingsAlert(){
    AlertDialog.Builder alertDialog = new AlertDialog.Builder(mcontext);
  
    // Setting Dialog Title
    alertDialog.setTitle("GPS settings");

    // Setting Dialog Message
    alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

    // On pressing Settings button
    alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog,int which) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            mcontext.startActivity(intent);
        }
    });

    // on pressing cancel button
    alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
        dialog.cancel();
        }
    });
    

    // Showing Alert Message
    alertDialog.show();
}


@Override
public void onLocationChanged(Location location) {
}

@Override
public void onProviderDisabled(String provider) {
}

@Override
public void onProviderEnabled(String provider) {
}

@Override
public void onStatusChanged(String provider, int status, Bundle extras) {
}

@Override
public IBinder onBind(Intent arg0) {
    return null;
}

	}
         
	
