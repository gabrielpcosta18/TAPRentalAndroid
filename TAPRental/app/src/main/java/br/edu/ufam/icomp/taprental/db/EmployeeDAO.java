package br.edu.ufam.icomp.taprental.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import br.edu.ufam.icomp.taprental.model.Customer;
import br.edu.ufam.icomp.taprental.model.Employee;

/**
 * Created by gabri on 28/01/2017.
 */

public class EmployeeDAO {
    private SQLiteDatabase database;
    private static final String TABLE_NAME = "Employee";

    public EmployeeDAO(Context context) {
        this.database = (new Database(context)).getWritableDatabase();
    }

    public Employee getEmployeeById(int id) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE _id = " + id;

        Cursor cursor = this.database.rawQuery(query, null);
        if(cursor.moveToNext()) {
            Employee employee = new Employee(cursor.getInt(0), cursor.getString(1));

            cursor.close();
            return employee;
        }

        return null;
    }

    public Cursor getAllEmployee() {
        return this.database.rawQuery("SELECT _id, name" +
                " FROM " + TABLE_NAME, null);
    }

    public boolean addEmployee(Employee employee) {
        try {
            String sqlCmd = "INSERT INTO " + TABLE_NAME +
                    "(name)" +
                    " VALUES ('" + employee.getName() + "')";

            Log.d("SQL", sqlCmd);

            this.database.execSQL(sqlCmd);
            return true;
        }
        catch (SQLException e) {
            Log.e("RentalApp", e.getMessage());
            return false;
        }
    }

    public boolean updateEmployee(Employee employee) {
        try {
            String sqlCmd = "UPDATE " + TABLE_NAME +
                    " SET name = '" + employee.getName() + "' "  +
                    " WHERE _id = " + employee.getId();

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
