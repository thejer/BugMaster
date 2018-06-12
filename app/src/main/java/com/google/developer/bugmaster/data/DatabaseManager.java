package com.google.developer.bugmaster.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import com.google.developer.bugmaster.data.BugsContract.BugsEntry;
import static com.google.developer.bugmaster.MainActivity.NAME_SORT;
import static com.google.developer.bugmaster.MainActivity.DANGER_SORT;
/**
 * Singleton that controls access to the SQLiteDatabase instance
 * for this application.
 */
public class DatabaseManager {
    private static DatabaseManager sInstance;

    public static synchronized DatabaseManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseManager(context.getApplicationContext());
        }

        return sInstance;
    }

    private BugsDbHelper mBugsDbHelper;
    private SQLiteDatabase mDatabase;


    private DatabaseManager(Context context) {
        mBugsDbHelper = new BugsDbHelper(context);
    }

    /**
     * Return a {@link Cursor} that contains every insect in the database.
     *
     * @param sortOrder Optional sort order string for the query, can be null
     * @return {@link Cursor} containing all insect results.
     */
    public Cursor queryAllInsects(@Nullable String sortOrder) {
        //TODO: Implement the query
        mDatabase = mBugsDbHelper.getReadableDatabase();
        Cursor cursor;

        if (NAME_SORT.equals(sortOrder)){
            cursor = mDatabase.query(
                    BugsEntry.TABLE_NAME,
                    null,
                    null,
                    null,
                    null,
                    null,
                    BugsEntry.COLUMN_FRIENDLY_NAME +"ASC"
            );
        }else if (DANGER_SORT.equals(sortOrder)){
            cursor = mDatabase.query(
                    BugsEntry.TABLE_NAME,
                    null,
                    null,
                    null,
                    null,
                    null,
                    BugsEntry.COLUMN_DANGER_LEVEL +"DESC"
            );
        }else {
            cursor = mDatabase.query(
                    BugsEntry.TABLE_NAME,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );
        }

        return cursor;
    }

    /**
     * Return a {@link Cursor} that contains a single insect for the given unique id.
     *
     * @param id Unique identifier for the insect record.
     * @return {@link Cursor} containing the insect result.
     */
    public Cursor queryInsectsById(int id) {
        //TODO: Implement the query
        String selection = BugsEntry._ID + " = ?";
        String [] selectionArgs = {""+id};

        return mDatabase.query(
                BugsEntry.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
    }
}
