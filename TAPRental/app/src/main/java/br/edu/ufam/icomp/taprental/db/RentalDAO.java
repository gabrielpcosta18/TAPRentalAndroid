package br.edu.ufam.icomp.taprental.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import br.edu.ufam.icomp.taprental.model.Employee;
import br.edu.ufam.icomp.taprental.model.Product;
import br.edu.ufam.icomp.taprental.model.Rental;

/**
 * Created by gabri on 28/01/2017.
 */

public class RentalDAO {
    private SQLiteDatabase database;
    private static final String TABLE_NAME = "Rental";
    private Context context;

    public RentalDAO(Context context) {
        this.context = context;
        this.database = (new Database(context)).getWritableDatabase();
    }

    public Employee getRentalById(int id) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE _id = " + id;

        Cursor cursor = this.database.rawQuery(query, null);
        if(cursor.moveToNext()) {
            Employee employee = new Employee(cursor.getInt(0), cursor.getString(1));

            cursor.close();
            return employee;
        }

        return null;
    }

    public List<Rental> getAllRental() {
        ArrayList<Rental> rentals = new ArrayList<>();
        Cursor cursor = this.database.rawQuery("SELECT * " +
                " FROM " + TABLE_NAME + " WHERE wasDeveloped = 0", null);

        while(cursor.moveToNext()) {
            rentals.add(new Rental(
                    cursor.getInt(0),
                    new EmployeeDAO(this.context).getEmployeeById(cursor.getInt(2)),
                    new CustomerDAO(this.context).getCustomerById(cursor.getInt(1)),
                    new ProductDAO(this.context).getProductById(cursor.getInt(4)),
                    cursor.getString(5)));
        }
        return rentals;
    }

    public boolean addRental(Rental rental) {
        try {
            String sqlCmd = "INSERT INTO " + TABLE_NAME +
                    " (customerId, employeeRentId, productId)" +
                    " VALUES (" + Integer.toString(rental.getCustomer().getId()) + ", " +
                    Integer.toString(rental.getEmployeeRent().getId()) + ", " +
                    Integer.toString(rental.getProduct().getId()) + ")";
            Log.d("SQL", sqlCmd);

            this.database.execSQL(sqlCmd);
            return true;
        }
        catch (SQLException e) {
            Log.e("RentalApp", e.getMessage());
            return false;
        }
    }

    public boolean updateRental(Rental rental) {
        try {
            String sqlCmd = "UPDATE " + TABLE_NAME +
                    " SET employeerestitutionid = " + rental.getEmployeeRestitution().getId() + ", wasdeveloped = "
                    + (rental.isWasDeveloped()? "1":"0") +
                    " WHERE _id = " + rental.getId();

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
