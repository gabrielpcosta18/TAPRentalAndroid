package br.edu.ufam.icomp.taprental.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import br.edu.ufam.icomp.taprental.R;
import br.edu.ufam.icomp.taprental.db.EmployeeDAO;
import br.edu.ufam.icomp.taprental.db.ProductDAO;
import br.edu.ufam.icomp.taprental.model.Employee;
import br.edu.ufam.icomp.taprental.model.Product;

public class ProductRegisterActivity extends AppCompatActivity {
    private Product product;
    private EditText edtTitle;
    private EditText edtDescription;
    private EditText edtPrice;
    private EditText edtRentPrice;
    private EditText edtTotalInStock;
    private RadioGroup rdgGroup;
    private EditText edtMaxRentPeriod;

    private HashMap<String, Integer> checkRadio;

    private void initializeComponents() {
        checkRadio = new HashMap<String, Integer>();

        checkRadio.put("DVD", R.id.rdDvd);
        checkRadio.put("CD", R.id.rdCd);
        checkRadio.put("Fita", R.id.rdFita);
        checkRadio.put("BluRay", R.id.rdBluRay);

        this.edtTitle = (EditText) findViewById(R.id.edtProductTitle);
        this.edtDescription = (EditText) findViewById(R.id.edtProductDescription);
        this.edtPrice = (EditText) findViewById(R.id.edtProductPrice);
        this.edtRentPrice = (EditText) findViewById(R.id.edtProductRentPrice);
        this.edtMaxRentPeriod = (EditText) findViewById(R.id.edtMaxRentDays);
        this.edtTotalInStock = (EditText) findViewById(R.id.edtTotalInStock);
        this.rdgGroup = (RadioGroup) findViewById(R.id.grpProductType);

        Button btnConfirm = (Button) findViewById(R.id.btnProductRegisterConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductRegisterActivity activity = ProductRegisterActivity.this;
                if (activity.edtTitle.getText().toString().matches("")
                        || activity.edtMaxRentPeriod.getText().toString().matches("")
                        || activity.edtTotalInStock.getText().toString().matches("")
                        || activity.edtPrice.getText().toString().matches("")
                        || activity.edtRentPrice.getText().toString().matches("")) {
                    Toast.makeText(activity, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                    return;
                }
                ProductDAO productDAO = new ProductDAO(activity);

                activity.product.setTitle(activity.edtTitle.getText().toString());
                activity.product.setPrice(Float.parseFloat(activity.edtRentPrice.getText().toString()));
                activity.product.setDescription(activity.edtDescription.getText().toString());
                activity.product.setProductPrice(Float.parseFloat(activity.edtPrice.getText().toString()));
                activity.product.setTotalInStock(Integer.parseInt(activity.edtTotalInStock.getText().toString()));
                activity.product.setMaxPeriodRent(Integer.parseInt(activity.edtMaxRentPeriod.getText().toString()));
                activity.product.setType(((RadioButton) findViewById(activity.rdgGroup.getCheckedRadioButtonId())).getText().toString());

                if(activity.product.getId() != -1) {
                    productDAO.updateProduct(activity.product);
                }
                else productDAO.addProduct(activity.product);

                activity.setResult(RESULT_OK);
                activity.finish();
            }
        });

        this.getSupportActionBar().setHomeButtonEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_register);

        initializeComponents();

        Intent intent = getIntent();
        if(intent.hasExtra("productObj")) {
            this.product = (Product) intent.getSerializableExtra("productObj");

            this.edtTitle.setText(this.product.getTitle());
            this.edtTotalInStock.setText(Integer.toString(this.product.getTotalInStock()));
            this.edtMaxRentPeriod.setText(Integer.toString(this.product.getMaxPeriodRent()));
            this.edtDescription.setText(this.product.getDescription());
            this.edtPrice.setText(Float.toString(this.product.getProductPrice()));
            this.edtRentPrice.setText(Float.toString(this.product.getPrice()));
            Log.d("TYPE", product.getType());
            this.rdgGroup.check(checkRadio.get(this.product.getType()));
        }
        else this.product = new Product();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
