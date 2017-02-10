package br.edu.ufam.icomp.taprental.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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
            Product product = cursorToProduct(cursor);

            cursor.close();
            return product;
        }

        return null;
    }

    public boolean isBeingUsed(int id) {
        String query = "SELECT * FROM Rental WHERE productId = " + id;

        Cursor cursor = this.database.rawQuery(query, null);
        return cursor != null && cursor.getCount() > 0;
    }

    public boolean deleteProduct(Cursor cursor) {
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

    private Product cursorToProduct(Cursor cursor) {
        return new Product(cursor.getInt(0), cursor.getString(1),
                cursor.getString(2), cursor.getString(3), cursor.getInt(4),
                cursor.getInt(5), cursor.getFloat(6), cursor.getFloat(7));
    }

    public Cursor getAllProducts(boolean filter) {
        String query = "SELECT *" +
                " FROM Product";

        if(filter) {
            query += " as p WHERE p.totalInStock > (SELECT COUNT(_id) FROM Rental WHERE productId = p._id and wasDeveloped = 0)";
        }

        query += " ORDER BY title";
        return this.database.rawQuery(query, null);
    }

    public List getAllProductList(boolean filter) {
        ArrayList<Product> products = new ArrayList<>();
        Cursor cursor = getAllProducts(filter);

        while(cursor.moveToNext()) {
            products.add(cursorToProduct(cursor));
        }

        return products;
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
