
package gr.hua.android.locationprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import gr.hua.android.locationprovider.BDHelper.DBHelper;

/**
 * Created by vasilhs12 on 14/1/2017.
 */

public class LocationProvider extends ContentProvider {

    private static final String AUTHORITY="gr.hua.android.locationprovider";
    private static final String PATH="location";

    //   Creates a uriMatcher
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static{
        sUriMatcher.addURI(AUTHORITY, PATH , 1);
    }

    DBHelper mDBHelper;

    @Override
    public boolean onCreate() {
        mDBHelper = new DBHelper(this.getContext());
        return true;
    }

    //    Returns all data in the database
    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase mDB = mDBHelper.getReadableDatabase();
        Cursor mCursor;
        switch(sUriMatcher.match(uri)){
            case 1:
                mCursor = mDB.rawQuery("SELECT * FROM "+mDBHelper.DATABASE_TABLE+" ;",null);
                break;
            default:
                throw new IllegalArgumentException ("Content URI pattern not recognizable: "+uri);
        }
//        mDB.close();
        return mCursor;
    }

    //    Enters a new value in the base
    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = 0;
        SQLiteDatabase mDB = mDBHelper.getWritableDatabase();
        switch(sUriMatcher.match(uri)){
            case 1:
                id = mDB.insert(DBHelper.DATABASE_TABLE, null, values);
                break;
            default:
                throw new IllegalArgumentException ("Content URI pattern not recognizable: "+uri);
        }
//        mDB.close();
        return Uri.parse(uri.toString()+"/"+id);
    }

    @Nullable
    @Override
    public String getType(Uri uri)
    {
        return null;
    }

    //    Delete all data in the database
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int id = -1;
        SQLiteDatabase mDB = mDBHelper.getWritableDatabase();
        mDBHelper.onCreate(mDB);
        switch(sUriMatcher.match(uri)){
            case 1:
                id = mDB.delete(mDBHelper.DATABASE_TABLE,null,null);
                break;
            default:
                throw new IllegalArgumentException ("Content URI pattern not recognizable: "+uri);
        }

        mDBHelper.onUpgrade(mDB,0,0);

        mDB.close();
        return id;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}

