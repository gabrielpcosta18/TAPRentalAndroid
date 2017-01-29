package br.edu.ufam.icomp.taprental.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.edu.ufam.icomp.taprental.R;
import br.edu.ufam.icomp.taprental.db.CustomerDAO;
import br.edu.ufam.icomp.taprental.db.EmployeeDAO;
import br.edu.ufam.icomp.taprental.model.Customer;
import br.edu.ufam.icomp.taprental.model.Employee;

public class EmployeeListActivity extends ListActivity {
    private EmployeeDAO employeeDAO;
    private SimpleCursorAdapter adapter;

    private static final  int REFRESH_LIST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);

        Toolbar collapsingToolbarLayout = (Toolbar) findViewById(R.id.employee_list_toolbar);
        collapsingToolbarLayout.setTitle("TAPRental");

        this.employeeDAO = new EmployeeDAO(this);

        this.adapter = new SimpleCursorAdapter(this,
                R.layout.customer_list_item, employeeDAO.getAllEmployee(),
                new String[] {"name"}, new int[] { R.id.listItemCustomerName});

        this.setListAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmployeeListActivity.this, EmployeeRegisterActivity.class);
                startActivityForResult(intent, REFRESH_LIST);
            }
        });

        collapsingToolbarLayout.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmployeeListActivity.this.finish();
            }
        });
    }

    public void onListItemClick(ListView l, View v, int pos, long id) {
        TextView txtName =
                (TextView) v.findViewById(R.id.listItemEmployeeName);

        Cursor c = ((SimpleCursorAdapter)l.getAdapter()).getCursor();
        c.moveToPosition(pos);

        Toast.makeText(this, "Funcionário "  + c.getString(1),
                Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, EmployeeRegisterActivity.class);
        intent.putExtra("employeeObj", new Employee(c.getInt(0), c.getString(1)));
        startActivityForResult(intent, REFRESH_LIST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REFRESH_LIST) {
            if (resultCode == RESULT_OK) {
                this.employeeDAO = new EmployeeDAO(this);
                Cursor cursor = employeeDAO.getAllEmployee();
                Log.d("ACTIVITY RESULT", "RESULT");

                this.adapter = new SimpleCursorAdapter(this,
                        R.layout.employee_list_item, cursor,
                        new String[] {"name"}, new int[] { R.id.listItemEmployeeName});

                this.setListAdapter(adapter);
            }
        }
    }
}
