package br.edu.ufam.icomp.taprental;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.edu.ufam.icomp.taprental.adapter.RentListAdapter;
import br.edu.ufam.icomp.taprental.db.ProductDAO;
import br.edu.ufam.icomp.taprental.db.RentalDAO;
import br.edu.ufam.icomp.taprental.model.Rental;
import br.edu.ufam.icomp.taprental.ui.CustomerLstActivity;
import br.edu.ufam.icomp.taprental.ui.EmployeeListActivity;
import br.edu.ufam.icomp.taprental.ui.ProductListActivity;
import br.edu.ufam.icomp.taprental.ui.RentRegisterActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final int REFRESH_LIST = 1;

    private ListView rentalList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeComponents();
    }

    private void initializeComponents() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        rentalList = (ListView) findViewById(R.id.rentalList);
        fillRentList();
    }

    private void fillRentList() {
        RentListAdapter adapter = new RentListAdapter(this,
                R.layout.rental_list_item, new RentalDAO(this).getAllRental());

        rentalList.setAdapter(adapter);
        rentalList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Rental rental = (Rental) rentalList.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, RentRegisterActivity.class);
                intent.putExtra("rentalObj", rental);
                startActivityForResult(intent, REFRESH_LIST);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_client) {
            // Handle the camera action
            Intent intent = new Intent(this, CustomerLstActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_employee) {
            Intent intent = new Intent(this, EmployeeListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_product) {
            Intent intent = new Intent(this, ProductListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_rent) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REFRESH_LIST) {
            if (resultCode == RESULT_OK) {
                fillRentList();
            }
        }
    }
}
