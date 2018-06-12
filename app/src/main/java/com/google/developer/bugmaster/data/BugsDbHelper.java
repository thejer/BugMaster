package com.google.developer.bugmaster.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.developer.bugmaster.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.google.developer.bugmaster.data.BugsContract.BugsEntry;

/**
 * Database helper class to facilitate creating and updating
 * the database from the chosen schema.
 */
public class BugsDbHelper extends SQLiteOpenHelper {
    private static final String TAG = BugsDbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "insects.db";
    private static final int DATABASE_VERSION = 1;

    //Used to read data from res/ and assets/
    private Resources mResources;

    public BugsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        mResources = context.getResources();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //TODO: Create and fill the database

        final String SQL_CREATE_BUGS_TABLE = "CREATE TABLE " + BugsEntry.TABLE_NAME + " (" +
                BugsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                BugsEntry.COLUMN_FRIENDLY_NAME + " TEXT NOT NULL, " +
                BugsEntry.COLUMN_SCIENTIFIC_NAME + " TEXT NOT NULL, " +
                BugsEntry.COLUMN_CLASSIFICATION + " TEXT NOT NULL, " +
                BugsEntry.COLUMN_IMAGE_ASSET + " TEXT NOT NULL, " +
                BugsEntry.COLUMN_DANGER_LEVEL + " INTEGER NOT NULL " +
                "); ";
        db.execSQL(SQL_CREATE_BUGS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO: Handle database version upgrades
        db.execSQL("DROP TABLE IF EXISTS "  + BugsEntry.TABLE_NAME);
        onCreate(db);
    }

    /**
     * Streams the JSON data from insect.json, parses it, and inserts it into the
     * provided {@link SQLiteDatabase}.
     *
     * @param db Database where objects should be inserted.
     * @throws IOException
     * @throws JSONException
     */
    private void readInsectsFromResources(SQLiteDatabase db) throws IOException, JSONException {
        StringBuilder builder = new StringBuilder();
        InputStream in = mResources.openRawResource(R.raw.insects);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }

        //Parse resource into key/values
        final String rawJson = builder.toString();
        //TODO: Parse JSON data and insert into the provided database instance
        insertDataToDb(rawJson, db);

    }

    private void insertDataToDb(String rawJson, SQLiteDatabase db) {

        if(db == null){
            return;
        }

        List<ContentValues> insects = new ArrayList<>();


        try {
            JSONObject insectJsonObject = new JSONObject(rawJson);
            JSONArray insectsArray = insectJsonObject.getJSONArray("insects");

            for (int i = 0; i < insectsArray.length(); i++){
                JSONObject bug = insectsArray.getJSONObject(i);

                String name = bug.getString("friendlyName");
                String scientificName = bug.getString("scientificName");
                String classification = bug.getString("classification");
                String image = bug.getString("imageAsset");
                int dangerLevel = bug.getInt("dangerLevel");

                ContentValues contentValues = new ContentValues();
                contentValues.put(BugsEntry.COLUMN_FRIENDLY_NAME, name);
                contentValues.put(BugsEntry.COLUMN_SCIENTIFIC_NAME, scientificName);
                contentValues.put(BugsEntry.COLUMN_CLASSIFICATION, classification);
                contentValues.put(BugsEntry.COLUMN_IMAGE_ASSET, image);
                contentValues.put(BugsEntry.COLUMN_DANGER_LEVEL, dangerLevel);
                insects.add(contentValues);
            }
            try {
                db.beginTransaction();
                db.delete (BugsEntry.TABLE_NAME,null,null);
                for(ContentValues contentValues : insects){
                    db.insert(BugsEntry.TABLE_NAME, null, contentValues);
                }
                db.setTransactionSuccessful();
            } catch (SQLException e) {
                Log.i(TAG, "insertDataToDb: error inserting to database");
            } finally { db.endTransaction(); }

        }
        catch (JSONException e) {
            Log.e(TAG, "Problem parsing the insects JSON results", e);
        }


    }


}
