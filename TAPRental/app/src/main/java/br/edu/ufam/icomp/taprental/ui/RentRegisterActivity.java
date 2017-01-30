package br.edu.ufam.icomp.taprental.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import br.edu.ufam.icomp.taprental.R;
import br.edu.ufam.icomp.taprental.db.CustomerDAO;
import br.edu.ufam.icomp.taprental.model.Customer;

public class RentRegisterActivity extends AppCompatActivity {
    private Spinner spnEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_register);

        initializeComponents();
    }

    private void initializeComponents() {
        this.spnEmployee = (Spinner) findViewById(R.id.spnCustomer);
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, new CustomerDAO(this).getAllCustomerList());

        this.spnEmployee.setAdapter(spinnerArrayAdapter);
    }
}
