package josue.climaapp;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.icu.util.Calendar;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;

import java.util.List;

import josue.climaapp.modelos.ListadoCiudades;


public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private static final int PETICION_PERMISO_LOCALIZACION = 101;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar appbar;
    LocationManager ubicacionManager;
    Location ultimaUbicacion;
    private GoogleApiClient apiClient;
    ProgressDialog dialogo_espere;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialogo_espere = new ProgressDialog(this);
        dialogo_espere.setMessage("Loading GPS...");
        /*
        Adquirir ubicacion para listar las ciudades cercanas
         */



        apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();

        dialogo_espere.show();


        ubicacionManager = (LocationManager) getSystemService(this.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PETICION_PERMISO_LOCALIZACION);
        } else {

            ultimaUbicacion = ubicacionManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (ultimaUbicacion == null){

                ultimaUbicacion =
                        LocationServices.FusedLocationApi.getLastLocation(apiClient);
            }
        }

        dialogo_espere.dismiss();

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_nav_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navview);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                boolean fragmentTransition = false;
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.menu_city_list:
                        fragment = new OpcionCiudades();
                        Bundle argumentos = new Bundle();
                        argumentos.putDouble("lat", ultimaUbicacion.getLatitude());
                        argumentos.putDouble("lng", ultimaUbicacion.getLongitude());
                        fragment.setArguments(argumentos);
                        fragmentTransition = true;
                        break;
                    case R.id.menu_about:
                        fragment = new OpcionAbout();
                        fragmentTransition = true;
                        break;

                }
                if (fragmentTransition) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, fragment)
                            .commit();
                    item.setChecked(true);
                    getSupportActionBar().setTitle(item.getTitle());
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_opciones, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.menu_settings:
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Settings");
                alertDialog.setMessage("App settings");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Save",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("CLIMA", "error de GPS");
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PETICION_PERMISO_LOCALIZACION);
        } else {

            ultimaUbicacion =
                    LocationServices.FusedLocationApi.getLastLocation(apiClient);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
