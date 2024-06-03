package com.example.geograf;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.geograf.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    LocationManager _LocationManager;
    int ACCESS_FINE_LOCATION;
    int ACCESS_COARSE_LOCATION;
    double currentLatitude;
    double currentLongitude;
    String message;

    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        result = findViewById(R.id.locationText);

        _LocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    LocationListener _LocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            if (location == null) return;
            else {
                message = "";
                if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
                    currentLatitude = location.getLatitude();
                    currentLongitude = location.getLongitude();
                    message += "\nМестоположение определено с помощью GPS:\n долгота - " + currentLatitude + " широта - " + currentLongitude;
                }
                if (location.getProvider().equals(LocationManager.NETWORK_PROVIDER)) {
                    message += "\nМестоположение определено с помощью интернета:\n долгота - " + location.getLatitude() + " широта - " + location.getLongitude();
                }
                result.setText(message);
            }
        }
    };
    public Boolean GetPermissionGPS() {
        ACCESS_FINE_LOCATION = ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION);
        ACCESS_COARSE_LOCATION = ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION);
        return ACCESS_FINE_LOCATION == PackageManager.PERMISSION_GRANTED ||
                ACCESS_COARSE_LOCATION == PackageManager.PERMISSION_GRANTED;
    }
    public void OnGetGPS(View view){
        if(GetPermissionGPS() == false)
        {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }
}