package gr.hua.android.locationprovider.BDHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by vasilhs12 on 14/1/2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final String KEY_ID = "ID";
    public static final String KEY_USERID = "USERID";
    public static final String KEY_LONGITUDE = "LONGITUDE";
    public static final String KEY_LATITUDE = "LATITUDE";
    public static final String KEY_DT = "DT";

    private static final String DATABASE_NAME = "locations_manager";
    public static final String DATABASE_TABLE = "location";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS "+ DATABASE_TABLE +" ("
            + KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_USERID +" VARCHAR(10) NOT NULL, "
            + KEY_LONGITUDE +" FLOAT NOT NULL, "
            + KEY_LATITUDE + " FLOAT NOT NULL, "
            + KEY_DT + " VARCHAR(20) NOT NULL "
            + ");";

    private static final String DATABASE_DROP = "DROP TABLE IF EXISTS " +DATABASE_TABLE+ " ;"   ;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        db.execSQL(DATABASE_DROP);
        onCreate(db);
    }
}
