package josue.climaapp;


import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar appbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_nav_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navview);



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                boolean fragmentTransition = false;
                Fragment fragment = null;
                switch(item.getItemId()){
                    case R.id.menu_city_list:
                        fragment = new OpcionCiudades();
                        fragmentTransition = true;
                        break;
                    case R.id.menu_about:
                        fragment = new OpcionAbout();
                        fragmentTransition = true;
                        break;

                }
                if (fragmentTransition){
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

        switch(item.getItemId()) {
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
}
