package com.demo.theweather;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.ConnectivityManager.NetworkCallback;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.demo.theweather.adapters.PAdapter;
import com.demo.theweather.mvvm.WeatherViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.material.snackbar.Snackbar;

import static com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY;


public class MainActivity extends AppCompatActivity  {
    private NetworkCallback networkCallback;
    private static final String TAG = "MainActivity1";
    private ConnectivityManager connectivityManager;
    public static Boolean networkIsAvailable = false;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 33;
    private static LocationManager locationManager;
    private FusedLocationProviderClient fusedLocationClient;
    private CancellationTokenSource cancellationTokenSource;
    private SharedPreferences preferences;
    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;
    private WeatherViewModel weatherViewModel;
    private SwipeRefreshLayout swipeRefreshLayout;


    private final ActivityResultLauncher<Intent> gpsActivityResultLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                            requestLocation();
                        } else {
                            makeToast(R.string.cant_determine_location);
                        }
                    });


    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    requestLocation();
                } else {
                    makeToast(R.string.location_access_denyed);
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout = findViewById(R.id.swiperefresh);
//        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        weatherViewModel = new ViewModelProvider(this,
                new SavedStateViewModelFactory(getApplication(), this))
                .get(WeatherViewModel.class);
        viewPager = findViewById(R.id.pager);
        pagerAdapter = new PAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        cancellationTokenSource = new CancellationTokenSource();
        locationManager = (LocationManager)
                this.getSystemService(Context.LOCATION_SERVICE);

        // Checking network connection
        connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkCallback = new NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                super.onAvailable(network);
                networkIsAvailable = true;

            }

            @Override
            public void onLost(@NonNull Network network) {
                super.onLost(network);
                networkIsAvailable = false;


            }
        };
        // End check connection

        // Check permissions and location
        if (savedInstanceState == null) {
            checkPermissions();
        }
        //End Check permissions and location
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checkPermissions();
            }
        });
// Подписываемся на изменение location
        weatherViewModel.getLocation().observe(this, location -> {
            Log.i(TAG, "observe" + location);
            if (location != null) {
                // передаем locationKey
                weatherViewModel.setQueryWeather(location.get(0));
                Log.i(TAG, "onCreate: "+location.get(0)+location.get(1));
            }
        });


    }

    private void checkPermissions() {
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            turnOnGps();
        } else {

            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED) {

                requestLocation();
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                Snackbar.make(findViewById(R.id.activity_main), R.string.location_access_required, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.ok, view -> {
                            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                        }).show();
            } else {
                requestPermissionLauncher.launch(
                        Manifest.permission.ACCESS_FINE_LOCATION);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }



    private void turnOnGps() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.enable_gps)
                .setCancelable(false)
                .setPositiveButton(R.string.ok, (dialog, which) ->
                        gpsActivityResultLauncher.launch(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
                .setNegativeButton(R.string.no, (dialog, which) ->
                        makeToast(R.string.cant_determine_location_gps))
                .show();
    }

    @SuppressLint("MissingPermission")
    private void requestLocation() {
        fusedLocationClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY, cancellationTokenSource.getToken())
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful() && task.getResult() != null) {
                        Location location = task.getResult();
                        preferences = getSharedPreferences("location", MODE_PRIVATE);
                        String stringLocation = location.getLatitude() +
                                "," +
                                location.getLongitude();
                        preferences.edit().putString("location", stringLocation).commit();

                        // start recieving weather data
                        Log.i(TAG, "requestLocation: "+location);

                        // Передаем координаты для получения locationKey
                        weatherViewModel.setQueryLocation(stringLocation);


                    } else {
                        makeToast(R.string.cant_determine_location_fused);
                    }
                });
    }


    private void makeToast(int p) {
        Snackbar.make(findViewById(R.id.activity_main), p, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.ok, view -> {
                }).show();
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onResume() {
        super.onResume();
        NetworkRequest.Builder builder = new NetworkRequest.Builder();
        connectivityManager.registerNetworkCallback(builder.build(), networkCallback);

    }

    @Override
    protected void onPause() {
        super.onPause();
        connectivityManager.unregisterNetworkCallback(networkCallback);
    }

    @Override
    protected void onStop() {
        super.onStop();
        cancellationTokenSource.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

}