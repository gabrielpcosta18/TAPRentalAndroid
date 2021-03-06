package br.edu.ufam.icomp.taprental.ui;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

public class CustomerLstActivity extends ListActivity {
    private CustomerDAO customerDAO;
    private SimpleCursorAdapter adapter;

    private static final  int REFRESH_LIST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_lst);

        Toolbar collapsingToolbarLayout = (Toolbar) findViewById(R.id.confirm_order_toolbar_layout);
        collapsingToolbarLayout.setTitle("TAPRental");

        this.customerDAO = new CustomerDAO(this);
        Cursor cursor = customerDAO.getAllCustomer();
        Log.d("CURSOR", new Integer(cursor.getCount()).toString());

        this.adapter = new SimpleCursorAdapter(this,
                R.layout.customer_list_item, cursor,
                new String[] {"name"}, new int[] { R.id.listItemCustomerName});

        this.setListAdapter(adapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent intent = new Intent(CustomerLstActivity.this, CustomerRegisterActivity.class);
                startActivityForResult(intent, REFRESH_LIST);
            }
        });

        collapsingToolbarLayout.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerLstActivity.this.finish();
            }
        });

        this.getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                new AlertDialog.Builder(CustomerLstActivity.this)
                        .setTitle("Deletar Cliente")
                        .setMessage("Você tem certeza que deseja deletar o cliente?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Cursor cursor = (Cursor) getListView().getItemAtPosition(pos);
                                boolean result = new CustomerDAO(CustomerLstActivity.this).deleteCustomer(cursor);

                                if(result)
                                    fillList();
                                else Toast.makeText(CustomerLstActivity.this,
                                        "Cliente não pode ser deletado", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

                return true;
            }
        });
    }

    public void onListItemClick(ListView l, View v, int pos, long id) {
        TextView txtName =
            (TextView) v.findViewById(R.id.listItemCustomerName);

        Cursor c = ((SimpleCursorAdapter)l.getAdapter()).getCursor();
        c.moveToPosition(pos);

         Toast.makeText(this, "Usuário "  + c.getString(1),
         Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, CustomerRegisterActivity.class);
        intent.putExtra("customerObj", new Customer(c.getInt(0), c.getString(1)));
        startActivityForResult(intent, REFRESH_LIST);
    }

    private void fillList() {
        this.customerDAO = new CustomerDAO(this);
        Cursor cursor = customerDAO.getAllCustomer();
        Log.d("ACTIVITY RESULT", "RESULT");

        this.adapter = new SimpleCursorAdapter(this,
                R.layout.customer_list_item, cursor,
                new String[] {"name"}, new int[] { R.id.listItemCustomerName});

        this.setListAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REFRESH_LIST) {
            if (resultCode == RESULT_OK) {
                fillList();
            }
        }
    }
}
