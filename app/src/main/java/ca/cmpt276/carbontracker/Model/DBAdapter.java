// ------------------------------------ DBADapter.java ---------------------------------------------

// TODO: Change the package to match your project.
package ca.cmpt276.carbontracker.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


// TO USE:
// Change the package (at top) to match your project.
// Search for "TODO", and make the appropriate changes.
public class DBAdapter {

    /////////////////////////////////////////////////////////////////////
    //	Constants & Data
    /////////////////////////////////////////////////////////////////////
    // For logging:
    private static final String TAG = "DBAdapter";

    // DB Fields
    public static final String KEY_ROWID = "_id";
    public static final int COL_ROWID = 0;
    /*
     * CHANGE 1:
     */
    // TODO: Setup your fields here:
    public static final String KEY_NAME = "name";
    public static final String KEY_MODEL = "model";
    public static final String KEY_MAKE = "make";
    public static final String KEY_YEAR = "year";
    public static final String KEY_DISPLACEMENT_VOL = "displacementVol";
    public static final String KEY_TRANSMISSION_TYPE = "transmissionType";
    public static final String KEY_FUEL_TYPE = "fuelType";
    public static final String KEY_CITYMPG = "cityMPG";
    public static final String KEY_HWYMPG = "hwyMPG";

    // TODO: Setup your field numbers here (0 = KEY_ROWID, 1=...)
    // This set for cars
    public static final int COL_CAR_NAME = 1;
    public static final int COL_CAR_MODEL = 2;
    public static final int COL_CAR_MAKE = 3;
    public static final int COL_CAR_YEAR = 4;
    public static final int COL_CAR_DISPLACEMENT_VOL = 5;
    public static final int COL_CAR_TRANSMISSION_TYPE = 6;
    public static final int COL_CAR_FUEL_TYPE = 7;
    public static final int COL_CAR_CITYMPG = 8;
    public static final int COL_CAR_HWYMPG = 9;

    public static final int COL_TOTAL_MODEL = 1;
    public static final int COL_TOTAL_MAKE = 2;
    public static final int COL_TOTAL_YEAR = 3;
    public static final int COL_TOTAL_DISPLACEMENT_VOL = 4;
    public static final int COL_TOTAL_TRANSMISSION_TYPE = 5;
    public static final int COL_TOTAL_FUEL_TYPE = 6;
    public static final int COL_TOTAL_CITYMPG = 7;
    public static final int COL_TOTAL_HWYMPG = 8;

    public static String[] ALL_KEYS;

    public static final String[] CAR_ALL_KEYS = new String[] {KEY_ROWID, KEY_NAME, KEY_MODEL, KEY_MAKE,
            KEY_YEAR, KEY_DISPLACEMENT_VOL, KEY_TRANSMISSION_TYPE, KEY_FUEL_TYPE, KEY_CITYMPG, KEY_HWYMPG};

    public static final String[] TOTAL_ALL_KEYS = new String[] {KEY_ROWID, KEY_MODEL, KEY_MAKE,
            KEY_YEAR, KEY_DISPLACEMENT_VOL, KEY_TRANSMISSION_TYPE, KEY_FUEL_TYPE, KEY_CITYMPG, KEY_HWYMPG};

    // DB info: it's name, and the table we are using (just one).
    public static final String DATABASE_NAME = "MyDb";
    public static final String DATABASE_TABLE = "Table";
    public static final String DATABASE_TOTAL_TABLE = "TotalCarTable";
    public static final String DATABASE_CAR_TABLE = "CarTable";
    public static final String DATABASE_ROUTE_TABLE = "RouteTable";
    public static final String DATABASE_JOURNEY_TABLE = "JourneyTable";
    // Track DB version if a new version of your app changes the format.
    public static final int DATABASE_VERSION = 1;

    private static final String CAR_DATABASE_CREATE_SQL =
            "create table " + DATABASE_CAR_TABLE
                    + " (" + KEY_ROWID + " integer primary key autoincrement, "
                    + KEY_NAME + " text not null, "
                    + KEY_MODEL + " text not null, "
                    + KEY_MAKE + " text not null,"
                    + KEY_YEAR + " integer not null,"
                    + KEY_DISPLACEMENT_VOL + " text not null,"
                    + KEY_TRANSMISSION_TYPE + " text not null,"
                    + KEY_FUEL_TYPE + " text not null,"
                    + KEY_CITYMPG + " real not null,"
                    + KEY_HWYMPG + " real not null"

                    // Rest  of creation:
                    + ");";

    private static final String TOTAL_DATABASE_CREATE_SQL =
            "create table " + DATABASE_TOTAL_TABLE
                    + " (" + KEY_ROWID + " integer primary key autoincrement, "
                    + KEY_NAME + " text not null, "
                    + KEY_MODEL + " text not null, "
                    + KEY_MAKE + " text not null,"
                    + KEY_YEAR + " integer not null,"
                    + KEY_DISPLACEMENT_VOL + " text not null,"
                    + KEY_TRANSMISSION_TYPE + " text not null,"
                    + KEY_FUEL_TYPE + " text not null,"
                    + KEY_CITYMPG + " real not null,"
                    + KEY_HWYMPG + " real not null"

                    // Rest  of creation:
                    + ");";

    // Context of application who uses us.
    private final Context context;

    private DatabaseHelper myDBHelper;
    private SQLiteDatabase db;

    /////////////////////////////////////////////////////////////////////
    //	Public methods:
    /////////////////////////////////////////////////////////////////////

    public DBAdapter(Context ctx) {
        this.context = ctx;
        myDBHelper = new DatabaseHelper(context);
    }

    // Open the database connection.
    public DBAdapter open() {
        db = myDBHelper.getWritableDatabase();
        return this;
    }

    // Close the database connection.
    public void close() {
        myDBHelper.close();
    }

    // Add a new set of values to the database.
    public long insertCar(Car car) {
        String name = car.getNickname();
        String model = car.getModel();
        String make = car.getMake();
        int year = car.getYear();
        String displacementVol = car.getDisplacementVol();
        String transmissionType = car.getTransmissionType();
        String fuelType = car.getFuelType();
        float cityMPG = car.getMilesPerGallonCity();
        float hwyMPG = car.getMilesPerGallonHway();

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_MODEL, model);
        initialValues.put(KEY_MAKE, make);
        initialValues.put(KEY_YEAR, year);
        initialValues.put(KEY_DISPLACEMENT_VOL, displacementVol);
        initialValues.put(KEY_TRANSMISSION_TYPE, transmissionType);
        initialValues.put(KEY_FUEL_TYPE, fuelType);
        initialValues.put(KEY_CITYMPG, cityMPG);
        initialValues.put(KEY_HWYMPG, hwyMPG);

        // Insert it into the database.
        return db.insert(DATABASE_CAR_TABLE, null, initialValues);
    }

    public long insertTotalCar(Car car) {
        String model = car.getModel();
        String make = car.getMake();
        int year = car.getYear();
        String displacementVol = car.getDisplacementVol();
        String transmissionType = car.getTransmissionType();
        String fuelType = car.getFuelType();
        float cityMPG = car.getMilesPerGallonCity();
        float hwyMPG = car.getMilesPerGallonHway();

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_MODEL, model);
        initialValues.put(KEY_MAKE, make);
        initialValues.put(KEY_YEAR, year);
        initialValues.put(KEY_DISPLACEMENT_VOL, displacementVol);
        initialValues.put(KEY_TRANSMISSION_TYPE, transmissionType);
        initialValues.put(KEY_FUEL_TYPE, fuelType);
        initialValues.put(KEY_CITYMPG, cityMPG);
        initialValues.put(KEY_HWYMPG, hwyMPG);

        // Insert it into the database.
        return db.insert(DATABASE_TOTAL_TABLE, null, initialValues);
    }

    // Delete a row from the database, by rowId (primary key)
    public boolean deleteRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        return db.delete(DATABASE_TABLE, where, null) != 0;
    }

    public void deleteAllCar() {
        Cursor c = getAllCarRows();
        long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
        if (c.moveToFirst()) {
            do {
                deleteRow(c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c.close();
    }

    public void deleteAllTotal() {
        Cursor c = getAllTotalRows();
        long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
        if (c.moveToFirst()) {
            do {
                deleteRow(c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c.close();
    }

    // Return all data in the database.
    public Cursor getAllCarRows() {
        String where = null;
        Cursor c = 	db.query(true, DATABASE_CAR_TABLE, CAR_ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor getAllTotalRows() {
        String where = null;
        Cursor c = 	db.query(true, DATABASE_TOTAL_TABLE, TOTAL_ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Get a specific row (by rowId)
    public Cursor getRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Change an existing row to be equal to new data.
    public boolean updateCar(long rowId, Car car) {
        String where = KEY_ROWID + "=" + rowId;

        String name = car.getNickname();
        String model = car.getModel();
        String make = car.getMake();
        int year = car.getYear();
        String displacementVol = car.getDisplacementVol();
        String transmissionType = car.getTransmissionType();
        String fuelType = car.getFuelType();
        float cityMPG = car.getMilesPerGallonCity();
        float hwyMPG = car.getMilesPerGallonHway();

        ContentValues newValues = new ContentValues();
        newValues.put(KEY_NAME, name);
        newValues.put(KEY_MODEL, model);
        newValues.put(KEY_MAKE, make);
        newValues.put(KEY_YEAR, year);
        newValues.put(KEY_DISPLACEMENT_VOL, displacementVol);
        newValues.put(KEY_TRANSMISSION_TYPE, transmissionType);
        newValues.put(KEY_FUEL_TYPE, fuelType);
        newValues.put(KEY_CITYMPG, cityMPG);
        newValues.put(KEY_HWYMPG, hwyMPG);

        // Insert it into the database.
        return db.update(DATABASE_CAR_TABLE, newValues, where, null) != 0;
    }

    public boolean updateTotalCar(long rowId, Car car) {
        String where = KEY_ROWID + "=" + rowId;

        String model = car.getModel();
        String make = car.getMake();
        int year = car.getYear();
        String displacementVol = car.getDisplacementVol();
        String transmissionType = car.getTransmissionType();
        String fuelType = car.getFuelType();
        float cityMPG = car.getMilesPerGallonCity();
        float hwyMPG = car.getMilesPerGallonHway();

        ContentValues newValues = new ContentValues();
        newValues.put(KEY_MODEL, model);
        newValues.put(KEY_MAKE, make);
        newValues.put(KEY_YEAR, year);
        newValues.put(KEY_DISPLACEMENT_VOL, displacementVol);
        newValues.put(KEY_TRANSMISSION_TYPE, transmissionType);
        newValues.put(KEY_FUEL_TYPE, fuelType);
        newValues.put(KEY_CITYMPG, cityMPG);
        newValues.put(KEY_HWYMPG, hwyMPG);

        // Insert it into the database.
        return db.update(DATABASE_TOTAL_TABLE, newValues, where, null) != 0;
    }


    /////////////////////////////////////////////////////////////////////
    //	Private Helper Classes:
    /////////////////////////////////////////////////////////////////////

    /**
     * Private class which handles database creation and upgrading.
     * Used to handle low-level database access.
     */
    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(TOTAL_DATABASE_CREATE_SQL);
            _db.execSQL(CAR_DATABASE_CREATE_SQL);

        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading application's database from version " + oldVersion
                    + " to " + newVersion + ", which will destroy all old data!");

            // Destroy old database:
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TOTAL_TABLE);
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_CAR_TABLE);

            // Recreate new database:
            onCreate(_db);
        }
    }
}
