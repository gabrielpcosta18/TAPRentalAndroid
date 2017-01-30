package br.edu.ufam.icomp.taprental.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

import br.edu.ufam.icomp.taprental.R;
import br.edu.ufam.icomp.taprental.adapter.CustomSpinnerAdapter;
import br.edu.ufam.icomp.taprental.db.CustomerDAO;
import br.edu.ufam.icomp.taprental.db.EmployeeDAO;
import br.edu.ufam.icomp.taprental.db.ProductDAO;
import br.edu.ufam.icomp.taprental.db.RentalDAO;
import br.edu.ufam.icomp.taprental.model.Customer;
import br.edu.ufam.icomp.taprental.model.Product;
import br.edu.ufam.icomp.taprental.model.Rental;

public class RentRegisterActivity extends AppCompatActivity {
    private Spinner spnEmployee;
    private Spinner spnCustomer;
    private Spinner spnProduct;
    private Spinner spnEmployeeReceptor;
    private Button btnConfirm;

    private Rental rental;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_register);

        initializeComponents();

        if(getIntent().hasExtra("rentalObj")) {
            this.rental = (Rental) getIntent().getSerializableExtra("rentalObj");
            fillDataFields();
        } else this.rental = new Rental();
    }

    private void initializeComponents() {
        this.spnCustomer = (Spinner) findViewById(R.id.spnCustomer);
        this.spnEmployee= (Spinner) findViewById(R.id.spnEmployee);
        this.spnEmployeeReceptor = (Spinner) findViewById(R.id.spnEmployeeReceptor);
        this.spnProduct = (Spinner) findViewById(R.id.spnTitle);

        CustomSpinnerAdapter customerAdapter = new CustomSpinnerAdapter(this,
                android.R.layout.simple_spinner_item, new CustomerDAO(this).getAllCustomerList());

        this.spnCustomer.setAdapter(customerAdapter);

        CustomSpinnerAdapter employeeRecepetorAdapter = new CustomSpinnerAdapter(this,
                android.R.layout.simple_spinner_item, new EmployeeDAO(this).getAllEmployeeList());

        this.spnEmployeeReceptor.setAdapter(employeeRecepetorAdapter);

        CustomSpinnerAdapter employeeAdapter = new CustomSpinnerAdapter(this,
                android.R.layout.simple_spinner_item, new EmployeeDAO(this).getAllEmployeeList());

        this.spnEmployee.setAdapter(employeeAdapter);

        CustomSpinnerAdapter productAdapter = new CustomSpinnerAdapter(this,
                android.R.layout.simple_spinner_item, new ProductDAO(this).getAllProductList());

        this.spnProduct.setAdapter(productAdapter);

        this.btnConfirm = (Button) findViewById(R.id.btnRentRegisterConfirm);
        this.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RentRegisterActivity activity = RentRegisterActivity.this;
                RentalDAO rentalDAO = new RentalDAO(activity);

                /*activity.product.setTitle(activity.edtTitle.getText().toString());
                activity.product.setPrice(Float.parseFloat(activity.edtRentPrice.getText().toString()));
                activity.product.setDescription(activity.edtDescription.getText().toString());
                activity.product.setProductPrice(Float.parseFloat(activity.edtPrice.getText().toString()));
                activity.product.setTotalInStock(Integer.parseInt(activity.edtTotalInStock.getText().toString()));
                activity.product.setMaxPeriodRent(Integer.parseInt(activity.edtMaxRentPeriod.getText().toString()));
                activity.product.setType(((RadioButton) findViewById(activity.rdgGroup.getCheckedRadioButtonId())).getText().toString());
                */
                if(activity.rental.getId() != -1) {
                    rentalDAO.updateRental(activity.rental);
                }
                else rentalDAO.addRental(activity.rental);

                activity.setResult(RESULT_OK);
                activity.finish();
            }
        });
    }

    private void fillDataFields() {
        CustomSpinnerAdapter productAdapter = (CustomSpinnerAdapter) this.spnProduct.getAdapter();
        CustomSpinnerAdapter employeeAdapter = (CustomSpinnerAdapter) this.spnEmployee.getAdapter();
        CustomSpinnerAdapter customerAdapter = (CustomSpinnerAdapter) this.spnCustomer.getAdapter();

        this.spnProduct.setSelection(productAdapter.getItemsList().indexOf(this.rental.getProduct()));
        this.spnEmployee.setSelection(employeeAdapter.getItemsList().indexOf(this.rental.getEmployeeRent()));
        this.spnCustomer.setSelection(customerAdapter.getItemsList().indexOf(this.rental.getCustomer()));

        /*CustomSpinnerAdapter employeeRecepetorAdapter = (CustomSpinnerAdapter) this.spnEmployeeReceptor.getAdapter();
        this.spnEmployeeReceptor.setSelection(employeeRecepetorAdapter.getItemsList().indexOf(this.rental.getProduct()));*/
    }
}
