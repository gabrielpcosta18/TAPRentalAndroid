package br.edu.ufam.icomp.taprental.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import br.edu.ufam.icomp.taprental.R;
import br.edu.ufam.icomp.taprental.db.EmployeeDAO;
import br.edu.ufam.icomp.taprental.db.ProductDAO;
import br.edu.ufam.icomp.taprental.model.Employee;
import br.edu.ufam.icomp.taprental.model.Product;

public class ProductListActivity extends ListActivity {
    private ProductDAO productDAO;
    private SimpleCursorAdapter adapter;

    private static final  int REFRESH_LIST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        Toolbar collapsingToolbarLayout = (Toolbar) findViewById(R.id.product_list_toolbar);
        collapsingToolbarLayout.setTitle("TAPRental");
        collapsingToolbarLayout.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductListActivity.this.finish();
            }
        });

        this.productDAO = new ProductDAO(this);

        this.adapter = new SimpleCursorAdapter(this,
                R.layout.product_list_item, productDAO.getAllProducts(),
                new String[] {"title",
                        "description",
                        "rentPrice",
                "_id", "maxPeriodRent","totalInStock","productPrice", "type"},
                new int[] { R.id.listItemProductTitle,
                        R.id.listItemProductDescription,
                        R.id.listItemProductRentPrice});

        this.setListAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductListActivity.this, ProductRegisterActivity.class);
                startActivityForResult(intent, REFRESH_LIST);
            }
        });
    }

    public void onListItemClick(ListView l, View v, int pos, long id) {
        Cursor c = ((SimpleCursorAdapter)l.getAdapter()).getCursor();
        c.moveToPosition(pos);

        Toast.makeText(this, "Product "  + c.getString(1),
                Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, ProductRegisterActivity.class);
        Log.d("Cursor", Integer.toString(c.getInt(5)));
        Log.d("Cursor", Integer.toString(c.getInt(6)));
        intent.putExtra("productObj", new Product(c.getInt(0),
                c.getString(1),
                c.getString(2),
                c.getString(3),
                c.getInt(4),
                c.getInt(5),
                c.getFloat(6),
                c.getFloat(7)));
        startActivityForResult(intent, REFRESH_LIST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REFRESH_LIST) {
            if (resultCode == RESULT_OK) {
                this.productDAO = new ProductDAO(this);
                Cursor cursor = productDAO.getAllProducts();
                Log.d("ACTIVITY RESULT", "RESULT");

                this.adapter = new SimpleCursorAdapter(this,
                        R.layout.product_list_item, productDAO.getAllProducts(),
                        new String[] {"title",
                                "description",
                                "rentPrice"},
                        new int[] { R.id.listItemProductTitle,
                                R.id.listItemProductDescription,
                                R.id.listItemProductRentPrice});

                this.setListAdapter(adapter);
            }
        }
    }
}
