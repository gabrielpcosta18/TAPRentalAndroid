package br.edu.ufam.icomp.taprental.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import br.edu.ufam.icomp.taprental.R;
import br.edu.ufam.icomp.taprental.adapter.CustomSpinnerAdapter;
import br.edu.ufam.icomp.taprental.db.CustomerDAO;
import br.edu.ufam.icomp.taprental.db.EmployeeDAO;
import br.edu.ufam.icomp.taprental.db.ProductDAO;
import br.edu.ufam.icomp.taprental.db.RentalDAO;
import br.edu.ufam.icomp.taprental.model.Customer;
import br.edu.ufam.icomp.taprental.model.Employee;
import br.edu.ufam.icomp.taprental.model.Product;
import br.edu.ufam.icomp.taprental.model.Rental;

public class RentRegisterActivity extends AppCompatActivity {
    private Spinner spnEmployee;
    private Spinner spnCustomer;
    private Spinner spnProduct;
    private Spinner spnEmployeeReceptor;

    private EditText edtTotalPrice;
    private EditText edtJuros;
    private EditText edtRentPrice;

    private CheckBox checkLost;

    private Button btnConfirm;

    private Rental rental;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_register);

        if(getIntent().hasExtra("rentalObj")) {
            this.rental = (Rental) getIntent().getSerializableExtra("rentalObj");
        } else this.rental = new Rental();

        initializeComponents();

        if(this.rental.getId() != -1)
            fillDataFields();
    }

    private void initializeComponents() {
        this.spnCustomer = (Spinner) findViewById(R.id.spnCustomer);
        this.spnEmployee= (Spinner) findViewById(R.id.spnEmployee);
        this.spnEmployeeReceptor = (Spinner) findViewById(R.id.spnEmployeeReceptor);
        this.spnProduct = (Spinner) findViewById(R.id.spnTitle);

        this.edtRentPrice = (EditText) findViewById(R.id.edtRentPrice);
        this.edtJuros = (EditText) findViewById(R.id.edtJuros);
        this.edtTotalPrice = (EditText) findViewById(R.id.edtTotalPrice);

        this.checkLost = (CheckBox) findViewById(R.id.checkLost);

        this.btnConfirm = (Button) findViewById(R.id.btnRentRegisterConfirm);

        CustomSpinnerAdapter customerAdapter = new CustomSpinnerAdapter(this,
                android.R.layout.simple_list_item_1, new CustomerDAO(this).getAllCustomerList());

        this.spnCustomer.setAdapter(customerAdapter);

        CustomSpinnerAdapter employeeRecepetorAdapter = new CustomSpinnerAdapter(this,
                android.R.layout.simple_list_item_1, new EmployeeDAO(this).getAllEmployeeList());

        this.spnEmployeeReceptor.setAdapter(employeeRecepetorAdapter);

        CustomSpinnerAdapter employeeAdapter = new CustomSpinnerAdapter(this,
                android.R.layout.simple_list_item_1, new EmployeeDAO(this).getAllEmployeeList());

        this.spnEmployee.setAdapter(employeeAdapter);

        CustomSpinnerAdapter productAdapter = new CustomSpinnerAdapter(this,
                android.R.layout.simple_list_item_1, new ProductDAO(this)
                .getAllProductList(this.rental.getId() == -1));

        this.spnProduct.setAdapter(productAdapter);

        this.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RentRegisterActivity activity = RentRegisterActivity.this;
                RentalDAO rentalDAO = new RentalDAO(activity);

                activity.rental.setProduct((Product) activity.spnProduct.getSelectedItem());
                activity.rental.setCustomer((Customer) activity.spnCustomer.getSelectedItem());
                activity.rental.setEmployeeRent((Employee) activity.spnEmployee.getSelectedItem());

                if(activity.checkLost.isChecked())
                    activity.rental.getProduct().setTotalInStock(
                        activity.rental.getProduct().getTotalInStock() - 1
                    );

                if(activity.rental.getId() != -1) {
                    activity.rental.setEmployeeRestitution((Employee) activity.spnEmployeeReceptor.getSelectedItem());
                    activity.rental.setWasDeveloped(true);

                    rentalDAO.updateRental(activity.rental);
                    if(checkLost.isChecked())
                        new ProductDAO(activity).updateProduct(rental.getProduct());
                }
                else rentalDAO.addRental(activity.rental);

                activity.setResult(RESULT_OK);
                activity.finish();
            }
        });

        this.checkLost.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DecimalFormat decimal = new DecimalFormat("#.##");

                RentRegisterActivity activity = RentRegisterActivity.this;
                Double value = Double.parseDouble(activity.edtTotalPrice.getText().toString());
                if(isChecked) {
                    RentRegisterActivity.this.edtTotalPrice.setText(decimal.format(value +
                            activity.rental.getProduct().getProductPrice()));
                }
                else {
                    RentRegisterActivity.this.edtTotalPrice.setText(decimal.format(value -
                            activity.rental.getProduct().getProductPrice()));
                }
            }
        });
    }

    private void fillDataFields() {
        ((LinearLayout) findViewById(R.id.employeeReceptorContainer)).setVisibility(View.VISIBLE);
        ((LinearLayout) findViewById(R.id.rentPriceContainer)).setVisibility(View.VISIBLE);
        ((LinearLayout) findViewById(R.id.totalPriceContainer)).setVisibility(View.VISIBLE);
        //((LinearLayout) findViewById(R.id.container)).setVisibility(View.VISIBLE);

        CustomSpinnerAdapter productAdapter = (CustomSpinnerAdapter) this.spnProduct.getAdapter();
        CustomSpinnerAdapter employeeAdapter = (CustomSpinnerAdapter) this.spnEmployee.getAdapter();
        CustomSpinnerAdapter customerAdapter = (CustomSpinnerAdapter) this.spnCustomer.getAdapter();

        this.spnProduct.setSelection(productAdapter.getItemsList().indexOf(this.rental.getProduct()));
        this.spnEmployee.setSelection(employeeAdapter.getItemsList().indexOf(this.rental.getEmployeeRent()));
        this.spnCustomer.setSelection(customerAdapter.getItemsList().indexOf(this.rental.getCustomer()));

        this.spnProduct.setEnabled(false);
        this.spnEmployee.setEnabled(false);
        this.spnCustomer.setEnabled(false);

        DecimalFormat formatDec = new DecimalFormat("#.##");

        this.edtRentPrice.setText(formatDec.format(this.rental.getProduct().getPrice()));

        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");
        Date strDate = null;
        try {
            strDate = format.parse(this.rental.getRentDate());
            Date today = new Date();

            long diff = (long)today.getTime() - strDate.getTime();
            diff = this.rental.getProduct().getMaxPeriodRent() - TimeUnit.MILLISECONDS.toDays(diff);

            double juros = 0;
            if(diff < 0)
                juros = this.rental.getProduct().getPrice() * Math.pow(1.1, -1*diff);

            this.edtJuros.setText(formatDec.format(juros));

            this.edtTotalPrice.setText(formatDec.format(this.rental.getProduct().getPrice() + juros));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        /*CustomSpinnerAdapter employeeRecepetorAdapter = (CustomSpinnerAdapter) this.spnEmployeeReceptor.getAdapter();
        this.spnEmployeeReceptor.setSelection(employeeRecepetorAdapter.getItemsList().indexOf(this.rental.getProduct()));*/
    }
}
