package br.edu.ufam.icomp.taprental.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.edu.ufam.icomp.taprental.model.Customer;
import br.edu.ufam.icomp.taprental.model.Employee;
import br.edu.ufam.icomp.taprental.model.Product;

/**
 * Created by gabri on 28/01/2017.
 */

public class CustomerDAO {
    private SQLiteDatabase database;
    private static final String TABLE_NAME = "Customer";

    public CustomerDAO(Context context) {
        this.database = (new Database(context)).getWritableDatabase();
    }

    private Customer cursorToCustomer(Cursor cursor) {
        return new Customer(cursor.getInt(0), cursor.getString(1));
    }

    public Customer getCustomerById(int id) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE _id = " + id;

        Cursor cursor = this.database.rawQuery(query, null);
        if(cursor.moveToNext()) {
            Customer customer = cursorToCustomer(cursor);

            cursor.close();
            return customer;
        }

        return null;
    }

    public Cursor getAllCustomer() {
        return this.database.rawQuery("SELECT _id, name" +
                " FROM " + TABLE_NAME + " ORDER BY name", null);
    }

    public List getAllCustomerList() {
        ArrayList<Customer> customers = new ArrayList<>();
        Cursor cursor = getAllCustomer();

        while(cursor.moveToNext()) {
            customers.add(cursorToCustomer(cursor));
        }

        return customers;
    }

    public boolean addCustomer(Customer customer) {
        try {
            String sqlCmd = "INSERT INTO " + TABLE_NAME +
                    "(name)" +
                    " VALUES ('" + customer.getName() + "')";

            Log.d("SQL", sqlCmd);

            this.database.execSQL(sqlCmd);
            return true;
        }
        catch (SQLException e) {
            Log.e("RentalApp", e.getMessage());
            return false;
        }
    }

    public boolean updateCustomer(Customer customer) {
        try {
            String sqlCmd = "UPDATE " + TABLE_NAME +
                    " SET name = '" + customer.getName() + "' "  +
                    " WHERE _id = " + customer.getId();

            Log.d("SQL", sqlCmd);

            this.database.execSQL(sqlCmd);
            return true;
        }
        catch(SQLException e) {
            Log.e("RentalApp", e.getMessage());
            return false;
        }
    }
}
