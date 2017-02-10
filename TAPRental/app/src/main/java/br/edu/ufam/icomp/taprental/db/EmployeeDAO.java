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

/**
 * Created by gabri on 28/01/2017.
 */

public class EmployeeDAO {
    private SQLiteDatabase database;
    private static final String TABLE_NAME = "Employee";

    public EmployeeDAO(Context context) {
        this.database = (new Database(context)).getWritableDatabase();
    }

    private Employee cursorToEmployee(Cursor cursor) {
        return new Employee(cursor.getInt(0), cursor.getString(1));
    }

    public Employee getEmployeeById(int id) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE _id = " + id;

        Cursor cursor = this.database.rawQuery(query, null);
        if(cursor.moveToNext()) {
            Employee employee = cursorToEmployee(cursor);

            cursor.close();
            return employee;
        }

        return null;
    }

    public Cursor getAllEmployee() {
        return this.database.rawQuery("SELECT _id, name" +
                " FROM " + TABLE_NAME + " ORDER BY name", null);
    }

    public boolean isBeingUsed(int id) {
        String query = "SELECT * FROM Rental WHERE employeeRentId = " + id + " or employeeRestitutionId = " + id;

        Cursor cursor = this.database.rawQuery(query, null);
        return cursor != null && cursor.getCount() > 0;
    }

    public boolean deleteEmployee(Cursor cursor) {
        try {
            if(!isBeingUsed(cursor.getInt(0))) {
                String sqlCmd = "DELETE FROM " + TABLE_NAME +
                        " WHERE _id = " + Integer.toString(cursor.getInt(0));

                this.database.execSQL(sqlCmd);
                return true;
            }
            return false;
        }
        catch(SQLException e) {
            Log.e("RentalApp", e.getMessage());
            return false;
        }
    }

    public List getAllEmployeeList() {
        ArrayList<Employee> employees = new ArrayList<>();
        Cursor cursor = getAllEmployee();

        while(cursor.moveToNext()) {
            employees.add(cursorToEmployee(cursor));
        }

        return employees;
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
