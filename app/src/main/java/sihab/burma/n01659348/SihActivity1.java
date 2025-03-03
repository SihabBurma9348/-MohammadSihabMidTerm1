package sihab.burma.n01659348;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class SihActivity1 extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Toolbar
        Toolbar toolbar = findViewById(R.id.sihToolbar);
        setSupportActionBar(toolbar);

        // Initialize Views
        viewPager = findViewById(R.id.sihViewPager);
        tabLayout = findViewById(R.id.sihTabLayout);

        // Set ViewPager Adapter
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);

        // Attach TabLayout to ViewPager2
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            String[] tabTitles = {
                    getString(R.string.sihab_s_tab),
                    getString(R.string.burma_s_tab),
                    getString(R.string.n01659348_s_tab)
            };
            tab.setText(tabTitles[position]);
            tab.view.setContentDescription(tabTitles[position]);
        }).attach();

        // Initialize Fused Location Provider
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Handle Back Press with Exit Dialog
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                showExitDialog();
            }
        });
    }

    // Inflate Menu (Toolbar)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    // Handle Toolbar Menu Click
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_map) {
            checkLocationPermission();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Show Exit Dialog
    private void showExitDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Exit App")
                .setMessage("Do you want to close the app?")
                .setIcon(R.drawable.abc)  // Ensure this icon exists in res/drawable
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, which) -> finishAffinity()) // Closes the app completely
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }

    // Check Location Permission Before Fetching Location
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            fetchLocation();
        }
    }

    // Handle Permission Request Result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchLocation();
            } else {
                Snackbar.make(findViewById(android.R.id.content),
                                "Location Permission Denied",
                                Snackbar.LENGTH_LONG)
                        .setAction("Settings", view -> {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        })
                        .show();
            }
        }
    }

    // Fetch Device Location and Handle SecurityException
    private void fetchLocation() {
        try {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if (location != null) {
                                    openGoogleMaps(location);
                                } else {
                                    requestNewLocation();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SihActivity1.this, "Failed to get location", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(this, "Location Permission Not Granted", Toast.LENGTH_SHORT).show();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
            Toast.makeText(this, "Location access denied: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // Request New Location if Last Known Location is Null
    private void requestNewLocation() {
        try {
            LocationRequest locationRequest = new LocationRequest.Builder(10000) // 10 seconds
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .build();

            fusedLocationClient.requestLocationUpdates(locationRequest, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    fusedLocationClient.removeLocationUpdates(this);
                    if (locationResult != null && locationResult.getLastLocation() != null) {
                        openGoogleMaps(locationResult.getLastLocation());
                    } else {
                        Toast.makeText(SihActivity1.this, "Unable to get current location", Toast.LENGTH_SHORT).show();
                    }
                }
            }, getMainLooper());
        } catch (SecurityException e) {
            e.printStackTrace();
            Toast.makeText(this, "Permission error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // Open Google Maps with Current Location
    private void openGoogleMaps(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        // Show Toast
        Toast.makeText(SihActivity1.this,
                "Latitude: " + latitude + ", Longitude: " + longitude,
                Toast.LENGTH_LONG).show();

        // Open Google Maps
        Uri gmmIntentUri = Uri.parse("geo:" + latitude + "," + longitude);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}
