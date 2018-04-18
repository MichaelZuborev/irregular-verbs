package com.kek.irregularverbs.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class DbHelper extends SQLiteOpenHelper {

    private static final String DBHELPER_LOG = "DBHelper";

    private static String DB_PATH;
    private static final String DB_NAME = "irregularverbs.db";
    private static final String TABLE_NAME = "verbs";

    private final Context mCurrentContext;
    private Context mContext;

    //table columns
    public static final String _ID = "_id";
    public static final String MAIN_VERB = "main_verb";
    public static final String FIRST_FORM = "first_form";
    public static final String SECOND_FORM = "second_form";
    public static final String THIRD_FORM = "third_form";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.mCurrentContext = context;
        this.mContext = context;
        DB_PATH = context.getApplicationInfo().dataDir + "/databases/";

        Log.d(DBHELPER_LOG, "DbHelper has been created");
    }

    public void createDataBase() throws IOException {
        Log.d(DBHELPER_LOG, "creating DB");

        if (!checkDataBase()) {
            Log.d(DBHELPER_LOG, "DB doesn't exist");

            this.getReadableDatabase();

            Log.d(DBHELPER_LOG, "trying to copy DB");
            try {
                copyDatabase();
            } catch (IOException e) {
                mContext.deleteDatabase(DB_NAME);
                Log.e(DBHELPER_LOG, "DB copying exception");
                throw e;
            }
        } else {
            Log.d(DBHELPER_LOG, "DB already exists");
        }
    }

    //check existence of database
    private boolean checkDataBase() {
        Log.d(DBHELPER_LOG, "checking the database");
        File dbFile = new File(DB_PATH + DB_NAME);
        Log.d(DBHELPER_LOG, dbFile + " exists: " + dbFile.exists());
        return dbFile.exists();
    }

    //copy DB form assets folder
    private void copyDatabase() throws IOException {
        Log.d(DBHELPER_LOG, "starting copying DB");
        try {
            InputStream inputDBStream = mCurrentContext.getAssets().open(DB_NAME);
            OutputStream outputDBStream = new FileOutputStream(DB_PATH + DB_NAME);

            byte[] buffer = new byte[4096];
            int length;
            while ((length = inputDBStream.read(buffer)) > 0) {
                outputDBStream.write(buffer, 0, length);
            }

            outputDBStream.flush();
            outputDBStream.close();
            inputDBStream.close();

            Log.d(DBHELPER_LOG, "copying process complete");
        } catch (IOException e) {
            Log.e(DBHELPER_LOG, "copying exception " + e);
            throw e;
        }
    }

    public Cursor getCursor() {
        Log.d(DBHELPER_LOG, "getting cursor");

        String[] columns = new String[]{_ID, MAIN_VERB, FIRST_FORM, SECOND_FORM, THIRD_FORM};

        Cursor cursor = getReadableDatabase().query(TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Log.d(DBHELPER_LOG, "cursor has been gotten");
        return cursor;
    }

    public Cursor getCursor(int id) throws NullPointerException {
        Log.d(DBHELPER_LOG, "getting cursor with id");

        String[] columns = new String[]{_ID, MAIN_VERB, FIRST_FORM, SECOND_FORM, THIRD_FORM};

        Cursor cursor;
        try {
            cursor = getReadableDatabase().query(TABLE_NAME, columns, _ID + "=" + id, null, null, null, null);
        } catch (NullPointerException e) {
            Log.e(DBHELPER_LOG, "exception is " + e);
            throw e;
        }

        cursor.moveToFirst();

        Log.d(DBHELPER_LOG, "cursor has been gotten");
        return cursor;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            createDataBase();

            Log.d(DBHELPER_LOG, "DB has been created(onCreate)");
        } catch (IOException e) {
            Log.d(DBHELPER_LOG, "problem with creating DB(onCreate)");

            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
