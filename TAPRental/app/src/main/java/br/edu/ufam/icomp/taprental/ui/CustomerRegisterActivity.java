package br.edu.ufam.icomp.taprental.ui;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.edu.ufam.icomp.taprental.R;
import br.edu.ufam.icomp.taprental.db.CustomerDAO;
import br.edu.ufam.icomp.taprental.model.Customer;

public class CustomerRegisterActivity extends AppCompatActivity {
    private Customer customer;
    private EditText edtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_register);

        Intent intent = getIntent();

        edtName = (EditText) findViewById(R.id.edtCustomerName);
        Button btnConfirm = (Button) findViewById(R.id.btnCustomerRegisterConfirm);

        if(intent.hasExtra("customerObj")) {
            this.customer = (Customer) intent.getSerializableExtra("customerObj");
            edtName.setText(this.customer.getName());
        }
        else this.customer = new Customer();

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomerRegisterActivity activity = CustomerRegisterActivity.this;
                CustomerDAO customerDAO = new CustomerDAO(activity);

                activity.customer.setName(activity.edtName.getText().toString());

                if(activity.customer.getId() != -1) {
                    customerDAO.updateCustomer(activity.customer);
                }
                else customerDAO.addCustomer(activity.customer);

                activity.setResult(RESULT_OK);
                activity.finish();
            }
        });
    }
}
