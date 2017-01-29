package br.edu.ufam.icomp.taprental.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import br.edu.ufam.icomp.taprental.model.Employee;
import br.edu.ufam.icomp.taprental.model.Product;

/**
 * Created by gabri on 28/01/2017.
 */

public class ProductDAO {
    private SQLiteDatabase database;
    private static final String TABLE_NAME = "Product";

    public ProductDAO(Context context) {
        this.database = (new Database(context)).getWritableDatabase();
    }

    public Product getProductById(int id) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " +
                "_id=" + id;

        Cursor cursor = this.database.rawQuery(query, null);
        if(cursor.moveToNext()) {
            Product product = new Product(cursor.getInt(0), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3), cursor.getInt(4),
                    cursor.getInt(5), cursor.getFloat(6), cursor.getFloat(7));

            cursor.close();
            return product;
        }

        return null;
    }

    public boolean addProduct(Product product) {
        try {
            String sqlCmd = "INSERT INTO " + TABLE_NAME +
                    " (title, description, type, maxPeriodRent, totalInStock, rentPrice, productPrice)" +
                    " VALUES ('" + product.getTitle() + "', '" + product.getDescription() +"', '" +
            product.getType() + "', '" + product.getMaxPeriodRent() + "', '" + product.getTotalInStock() + "', '" +
            product.getPrice() + "', '" + product.getProductPrice() + "')";
            Log.d("SQL", sqlCmd);

            this.database.execSQL(sqlCmd);
            return true;
        }
        catch (SQLException e) {
            Log.e("RentalApp", e.getMessage());
            return false;
        }
    }

    public Cursor getAllProducts() {
        return this.database.rawQuery("SELECT *" +
        " FROM Product", null);
    }

    public boolean updateProduct(Product product) {
        try {
            String sqlCmd = "UPDATE " + TABLE_NAME +
                    " SET title = '" + product.getTitle() + "',  description = '" + product.getDescription()  +
                    "', rentPrice = " + Float.toString(product.getPrice()) + ", productPrice = " +
                    Float.toString(product.getPrice()) + ", totalInStock = " + Integer.toString(product.getTotalInStock()) +
                    ", type = '" + product.getType() + "', maxPeriodRent = " + Integer.toString(product.getMaxPeriodRent()) +
                    " WHERE _id = " + product.getId();

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
