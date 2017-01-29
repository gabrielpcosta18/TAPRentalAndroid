package br.edu.ufam.icomp.taprental.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by gabri on 28/01/2017.
 */

public class Database extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DB_NAME = "RentalApp.db";

    private SQLiteDatabase myDataBase;
    private final Context context;
    private String DB_PATH;


    public Database(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        this.context = context;

        try {
            this.DB_PATH = "/data/data/" + context.getPackageName()  + "/databases/";

            createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createDataBase() throws IOException{

        boolean dbExist = checkDataBase();

        if(dbExist){
            //do nothing - database already exist
        }else{

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            Log.d("Oi", "oi");
            this.getReadableDatabase();

            try {
                Log.d("Oi2", "oi2");
                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database " + e.getMessage() + " " + this.DB_PATH);

            }
        }

    }

    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException e){

            //database does't exist yet.

        }

        if(checkDB != null){

            checkDB.close();

        }
        Log.d("Oi3", "oi3");
        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException{
        Log.d("Oi4", "oi4");
        //Open your local db as the input stream
        InputStream myInput = context.getAssets().open(DB_NAME);
        Log.d("Oi5", "oi5");
        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;
        Log.d("Oi6", "oi6");
        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        Log.d("Oi7", "oi7");
        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException{

        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL(SQL_CREATE_TABLE_PRODUCT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}
