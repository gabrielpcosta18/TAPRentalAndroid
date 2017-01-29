package br.edu.ufam.icomp.taprental.ui;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import br.edu.ufam.icomp.taprental.R;
import br.edu.ufam.icomp.taprental.db.EmployeeDAO;
import br.edu.ufam.icomp.taprental.model.Employee;

public class CustomerListActivity extends ListActivity {
    private EmployeeDAO employeeDAO;
    private SimpleCursorAdapter adapter;
    private List<Employee> employees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);

        this.employeeDAO = new EmployeeDAO(this);
        this.employees = new ArrayList<Employee>();
        employees.add(new Employee("Gabriel Pereira da Costa"));

        ArrayAdapter<Employee> myAdapter = new ArrayAdapter <Employee>(this,
                R.layout.customer_list_item, R.id.listItemCustomerName, employees);


        this.adapter = new SimpleCursorAdapter(this,
                R.layout.customer_list_item, employeeDAO.getAllEmployee(),
                new String[] {"name"}, new int[] { R.id.listItemCustomerName});

        this.setListAdapter(myAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
