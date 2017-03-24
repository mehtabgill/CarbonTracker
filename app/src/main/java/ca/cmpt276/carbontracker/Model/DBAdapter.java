// ------------------------------------ DBADapter.java ---------------------------------------------

// TODO: Change the package to match your project.
package ca.cmpt276.carbontracker.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Calendar;

import static android.R.attr.endY;
import static android.R.attr.name;
import static ca.cmpt276.carbontracker.Model.Utilities.BILL.ELECTRICITY;


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
    public static final String KEY_TYPE = "type";
    public static final String KEY_NAME = "name";
    public static final String KEY_MODEL = "model";
    public static final String KEY_MAKE = "make";
    public static final String KEY_YEAR = "year";
    public static final String KEY_DISPLACEMENT_VOL = "displacementVol";
    public static final String KEY_TRANSMISSION_TYPE = "transmissionType";
    public static final String KEY_FUEL_TYPE = "fuelType";
    public static final String KEY_CITYMPG = "cityMPG";
    public static final String KEY_HWYMPG = "hwyMPG";
    public static final String KEY_ROUTE_NAME = "routeName";
    public static final String KEY_CITYDISTANCE = "cityDistance";
    public static final String KEY_HWYDISTANCE = "hwyDistance";
    public static final String KEY_BILL_TYPE = "billType";
    public static final String KEY_AMOUNT = "amount";
    public static final String KEY_START_YEAR = "startYear";
    public static final String KEY_START_MONTH = "startMonth";
    public static final String KEY_START_DAY = "startDay";
    public static final String KEY_END_YEAR = "endYear";
    public static final String KEY_END_MONTH = "endMonth";
    public static final String KEY_END_DAY = "endDay";
    public static final String KEY_NUM_PPL = "numPpl";

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

    // Set of column values for route
    public static final int COL_ROUTE_NAME = 1;
    public static final int COL_ROUTE_CITYDISTANCE = 2;
    public static final int COL_ROUTE_HWYDISTANCE = 3;

    public static final int COL_JOURNEY_TYPE = 1;
    public static final int COL_JOURNEY_NAME = 2;
    public static final int COL_JOURNEY_MODEL = 3;
    public static final int COL_JOURNEY_MAKE = 4;
    public static final int COL_JOURNEY_YEAR = 5;
    public static final int COL_JOURNEY_DISPLACEMENT_VOL = 6;
    public static final int COL_JOURNEY_TRANSMISSION_TYPE = 7;
    public static final int COL_JOURNEY_FUEL_TYPE = 8;
    public static final int COL_JOURNEY_CITYMPG = 9;
    public static final int COL_JOURNEY_HWYMPG = 10;
    public static final int COL_JOURNEY_ROUTE_NAME = 11;
    public static final int COL_JOURNEY_CITYDISTANCE = 12;
    public static final int COL_JOURNEY_HWYDISTANCE = 13;

    public static final int COL_UTILITIES_BILL_TYPE = 1;
    public static final int COL_UTILITIES_AMOUNT = 2;
    public static final int COL_UTILITIES_START_YEAR = 3;
    public static final int COL_UTILITIES_START_MONTH = 4;
    public static final int COL_UTILITIES_START_DAY = 5;
    public static final int COL_UTILITIES_END_YEAR = 6;
    public static final int COL_UTILITIES_END_MONTH = 7;
    public static final int COL_UTILITIES_END_DAY = 8;
    public static final int COL_UTILITIES_NUM_PPL = 9;


    public static final String[] CAR_ALL_KEYS = new String[] {KEY_ROWID, KEY_NAME, KEY_MODEL, KEY_MAKE,
            KEY_YEAR, KEY_DISPLACEMENT_VOL, KEY_TRANSMISSION_TYPE, KEY_FUEL_TYPE, KEY_CITYMPG, KEY_HWYMPG};

    public static final String[] TOTAL_ALL_KEYS = new String[] {KEY_ROWID, KEY_MODEL, KEY_MAKE,
            KEY_YEAR, KEY_DISPLACEMENT_VOL, KEY_TRANSMISSION_TYPE, KEY_FUEL_TYPE, KEY_CITYMPG, KEY_HWYMPG};

    public static final String[] ROUTE_ALL_KEYS = new String[] {KEY_ROWID, KEY_ROUTE_NAME, KEY_CITYDISTANCE, KEY_HWYDISTANCE};

    public static final String[] JOURNEY_ALL_KEYS = new String[] {KEY_ROWID, KEY_TYPE, KEY_NAME, KEY_MODEL, KEY_MAKE,
            KEY_YEAR, KEY_DISPLACEMENT_VOL, KEY_TRANSMISSION_TYPE, KEY_FUEL_TYPE, KEY_CITYMPG, KEY_HWYMPG, KEY_ROUTE_NAME,
            KEY_CITYDISTANCE, KEY_HWYDISTANCE};

    public static final String[] UTILITIES_ALL_KEYS = new String[] {KEY_ROWID, KEY_BILL_TYPE, KEY_AMOUNT, KEY_START_YEAR,
            KEY_START_MONTH, KEY_START_DAY, KEY_END_YEAR, KEY_END_MONTH, KEY_END_DAY, KEY_NUM_PPL};

    // DB info: it's name, and the table we are using (just one).
    public static final String DATABASE_NAME = "MyDb";

    public static final String DATABASE_TABLE = "Table";

    public static final String DATABASE_TOTAL_TABLE = "TotalCarTable";
    public static final String DATABASE_CAR_TABLE = "CarTable";
    public static final String DATABASE_ROUTE_TABLE = "RouteTable";
    public static final String DATABASE_JOURNEY_TABLE = "JourneyTable";
    public static final String DATABASE_UTILITIES_TABLE = "UtilitiesTable";
    // Track DB version if a new version of your app changes the format.
    public static final int DATABASE_VERSION = 4;

    private static final String CAR_DATABASE_CREATE_SQL =
            "create table " + DATABASE_CAR_TABLE
                    + " (" + KEY_ROWID + " integer primary key autoincrement, "
                    + KEY_NAME + " text not null, "
                    + KEY_MODEL + " text not null, "
                    + KEY_MAKE + " text not null, "
                    + KEY_YEAR + " integer not null, "
                    + KEY_DISPLACEMENT_VOL + " text not null, "
                    + KEY_TRANSMISSION_TYPE + " text not null, "
                    + KEY_FUEL_TYPE + " text not null, "
                    + KEY_CITYMPG + " real not null, "
                    + KEY_HWYMPG + " real not null"

                    // Rest  of creation:
                    + ");";

    private static final String TOTAL_DATABASE_CREATE_SQL =
            "create table " + DATABASE_TOTAL_TABLE
                    + " (" + KEY_ROWID + " integer primary key autoincrement, "
                    + KEY_NAME + " text not null, "
                    + KEY_MODEL + " text not null, "
                    + KEY_MAKE + " text not null, "
                    + KEY_YEAR + " integer not null, "
                    + KEY_DISPLACEMENT_VOL + " text not null, "
                    + KEY_TRANSMISSION_TYPE + " text not null, "
                    + KEY_FUEL_TYPE + " text not null, "
                    + KEY_CITYMPG + " real not null, "
                    + KEY_HWYMPG + " real not null"

                    // Rest  of creation:
                    + ");";


    private static final String ROUTE_DATABASE_CREATE_SQL =
            "create table " + DATABASE_ROUTE_TABLE
                    + " (" + KEY_ROWID + " integer primary key autoincrement, "
                    + KEY_ROUTE_NAME + " text not null, "
                    + KEY_CITYDISTANCE + " real not null, "
                    + KEY_HWYDISTANCE + " real not null"

                    // Rest  of creation:
                    + ");";

    private static final String JOURNEY_DATABASE_CREATE_SQL =
            "create table " + DATABASE_JOURNEY_TABLE
                    + " (" + KEY_ROWID + " integer primary key autoincrement, "
                    + KEY_TYPE + " text not null, "
                    + KEY_NAME + " text, "
                    + KEY_MODEL + " text, "
                    + KEY_MAKE + " text, "
                    + KEY_YEAR + " integer, "
                    + KEY_DISPLACEMENT_VOL + " text, "
                    + KEY_TRANSMISSION_TYPE + " text, "
                    + KEY_FUEL_TYPE + " text, "
                    + KEY_CITYMPG + " real, "
                    + KEY_HWYMPG + " real, "
                    + KEY_ROUTE_NAME + " text not null, "
                    + KEY_CITYDISTANCE + " real not null, "
                    + KEY_HWYDISTANCE + " real not null"

                    // Rest  of creation:
                    + ");";

    private static final String UTILITIES_DATABASE_CREATE_SQL =
            "create table " + DATABASE_UTILITIES_TABLE
                    + " (" + KEY_ROWID + " integer primary key autoincrement, "
                    + KEY_BILL_TYPE + " text not null, "
                    + KEY_AMOUNT + " real not null, "
                    + KEY_START_YEAR + " integer not null, "
                    + KEY_START_MONTH + " integer not null, "
                    + KEY_START_DAY + " integer not null, "
                    + KEY_END_YEAR + " integer not null, "
                    + KEY_END_MONTH + " integer not null, "
                    + KEY_END_DAY + " integer not null, "
                    + KEY_NUM_PPL + " integer not null"

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

    public long insertRoute(Route route) {
        String name = route.getName();
        float cityDistance = route.getCityDriveDistance();
        float hwyDistance = route.getHighwayDriveDistance();

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_ROUTE_NAME, name);
        initialValues.put(KEY_CITYDISTANCE, cityDistance);
        initialValues.put(KEY_HWYDISTANCE, hwyDistance);

        // Insert it into the database.
        return db.insert(DATABASE_ROUTE_TABLE, null, initialValues);
    }

    public long insertJourney(Journey journey) {
        Transportation transportation = journey.getTransportation();
        Route route = journey.getRoute();
        Transportation.TRANSPORTATION_TYPE type = transportation.getType();
        ContentValues initialValues = new ContentValues();

        switch (type) {
            case CAR:
                Car car = (Car) transportation;
                String name = car.getNickname();
                String model = car.getModel();
                String make = car.getMake();
                int year = car.getYear();
                String displacementVol = car.getDisplacementVol();
                String transmissionType = car.getTransmissionType();
                String fuelType = car.getFuelType();
                float cityMPG = car.getMilesPerGallonCity();
                float hwyMPG = car.getMilesPerGallonHway();

                int value = Transportation.TRANSPORTATION_TYPE.CAR.ordinal();
                initialValues.put(KEY_TYPE, Transportation.TYPE[value]);
                initialValues.put(KEY_NAME, name);
                initialValues.put(KEY_MODEL, model);
                initialValues.put(KEY_MAKE, make);
                initialValues.put(KEY_YEAR, year);
                initialValues.put(KEY_DISPLACEMENT_VOL, displacementVol);
                initialValues.put(KEY_TRANSMISSION_TYPE, transmissionType);
                initialValues.put(KEY_FUEL_TYPE, fuelType);
                initialValues.put(KEY_CITYMPG, cityMPG);
                initialValues.put(KEY_HWYMPG, hwyMPG);
                break;
            case BUS:
                value = Transportation.TRANSPORTATION_TYPE.BUS.ordinal();
                initialValues.put(KEY_TYPE, Transportation.TYPE[value]);
                break;
            case SKYTRAIN:
                value = Transportation.TRANSPORTATION_TYPE.SKYTRAIN.ordinal();
                initialValues.put(KEY_TYPE, Transportation.TYPE[value]);
                break;
            case BIKE:
                value = Transportation.TRANSPORTATION_TYPE.BIKE.ordinal();
                initialValues.put(KEY_TYPE, Transportation.TYPE[value]);
                break;
            case WALK:
                value = Transportation.TRANSPORTATION_TYPE.WALK.ordinal();
                initialValues.put(KEY_TYPE, Transportation.TYPE[value]);
                break;
        }

        String routeName = route.getName();
        float cityDistance = route.getCityDriveDistance();
        float hwyDistance = route.getHighwayDriveDistance();

        initialValues.put(KEY_ROUTE_NAME, routeName);
        initialValues.put(KEY_CITYDISTANCE, cityDistance);
        initialValues.put(KEY_HWYDISTANCE, hwyDistance);

        // Insert it into the database.
        return db.insert(DATABASE_JOURNEY_TABLE, null, initialValues);
    }

    public long insertUtility(Utilities utilities) {
        Utilities.BILL type = utilities.getBillMode();
        String billType = "";
        switch (type) {
            case ELECTRICITY:
                billType = "ELECTRICITY";
                break;
            case GAS:
                billType = "GAS";
                break;
        }
        Calendar start = utilities.getStartDate();
        Calendar end = utilities.getEndDate();
        float amount = utilities.getElectricityAmount() + utilities.getGasAmount();
        int numPpl = utilities.getNumberOfPeople();

        int startYear = start.get(Calendar.YEAR);
        int startMonth = start.get(Calendar.MONTH);
        int startDay = start.get(Calendar.DAY_OF_MONTH);

        int endYear = end.get(Calendar.YEAR);
        int endMonth = end.get(Calendar.MONTH);
        int endDay = end.get(Calendar.DAY_OF_MONTH);

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_BILL_TYPE, billType);
        initialValues.put(KEY_AMOUNT, amount);
        initialValues.put(KEY_START_YEAR, startYear);
        initialValues.put(KEY_START_MONTH, startMonth);
        initialValues.put(KEY_START_DAY, startDay);
        initialValues.put(KEY_END_YEAR, endYear);
        initialValues.put(KEY_END_MONTH, endMonth);
        initialValues.put(KEY_END_DAY, endDay);
        initialValues.put(KEY_NUM_PPL, numPpl);


        // Insert it into the database.
        return db.insert(DATABASE_UTILITIES_TABLE, null, initialValues);
    }

    // Delete a row from the database, by rowId (primary key)
    public boolean deleteCarRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        return db.delete(DATABASE_CAR_TABLE, where, null) != 0;
    }

    public boolean deleteTotalRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        return db.delete(DATABASE_TOTAL_TABLE, where, null) != 0;
    }

    public boolean deleteRouteRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        return db.delete(DATABASE_ROUTE_TABLE, where, null) != 0;
    }

    public boolean deleteJourneyRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        return db.delete(DATABASE_JOURNEY_TABLE, where, null) != 0;
    }

    public boolean deleteUtilitiesRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        return db.delete(DATABASE_UTILITIES_TABLE, where, null) != 0;
    }

    public void deleteAllCar() {
        Cursor c = getAllCarRows();
        long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
        if (c.moveToFirst()) {
            do {
                deleteCarRow(c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c.close();
    }

    public void deleteAllTotal() {
        Cursor c = getAllTotalRows();
        long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
        if (c.moveToFirst()) {
            do {
                deleteTotalRow(c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c.close();
    }

    public void deleteAllRoute() {
        Cursor c = getAllRouteRows();
        long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
        if (c.moveToFirst()) {
            do {
                deleteRouteRow(c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c.close();
    }

    public void deleteAllJourney() {
        Cursor c = getAllJourneyRows();
        long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
        if (c.moveToFirst()) {
            do {
                deleteJourneyRow(c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c.close();
    }

    public void deleteAllUtilities() {
        Cursor c = getAllUtilitiesRows();
        long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
        if (c.moveToFirst()) {
            do {
                deleteUtilitiesRow(c.getLong((int) rowId));
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

    public Cursor getAllRouteRows() {
        String where = null;
        Cursor c = 	db.query(true, DATABASE_ROUTE_TABLE, ROUTE_ALL_KEYS,
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

    public Cursor getAllJourneyRows() {
        String where = null;
        Cursor c = 	db.query(true, DATABASE_JOURNEY_TABLE, JOURNEY_ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor getAllUtilitiesRows() {
        String where = null;
        Cursor c = 	db.query(true, DATABASE_UTILITIES_TABLE, UTILITIES_ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Get a specific row (by rowId)
    public long findCar(Car car) {

        String name = car.getNickname();
        String model = car.getModel();
        String make = car.getMake();
        int year = car.getYear();
        String displacementVol = car.getDisplacementVol();
        String transmissionType = car.getTransmissionType();
        String fuelType = car.getFuelType();
        float cityMPG = car.getMilesPerGallonCity();
        float hwyMPG = car.getMilesPerGallonHway();

        String[] values = new String[]{name, model, make, String.valueOf(year), displacementVol,
                                        transmissionType, fuelType, String.valueOf(cityMPG), String.valueOf(hwyMPG)};

        String where = KEY_NAME + "=? and " + KEY_MODEL + "=? and " + KEY_MAKE + "=? and " +
                KEY_YEAR + "=? and " + KEY_DISPLACEMENT_VOL + "=? and " + KEY_TRANSMISSION_TYPE + "=? and " +
                KEY_FUEL_TYPE + "=? and " + KEY_CITYMPG + "=? and " + KEY_HWYMPG + "=?";

        Cursor c = 	db.query(true, DATABASE_CAR_TABLE, CAR_ALL_KEYS,
                where, values, null, null, null, null);
        long id = -1;

        if (c != null) {
            c.moveToFirst();
            id = c.getLong(COL_ROWID);
            c.close();
        }
        return id;
    }

    public long findRoute(Route route) {

        String name = route.getName();
        float cityDistance = route.getCityDriveDistance();
        float hwyDistance = route.getHighwayDriveDistance();

        String[] values = new String[]{name, String.valueOf(cityDistance), String.valueOf(hwyDistance)};

        String where = KEY_ROUTE_NAME + "=? and " + KEY_CITYDISTANCE + "=? and " + KEY_HWYDISTANCE + "=?";

        Cursor c = 	db.query(true, DATABASE_ROUTE_TABLE, ROUTE_ALL_KEYS,
                where, values, null, null, null, null);

        long id = -1;

        if (c != null && c.moveToFirst()) {
            id = c.getLong(COL_ROWID);
            c.close();
        }
        return id;
    }

    public long findUtilities(Utilities utilities) {

        Utilities.BILL type = utilities.getBillMode();
        String billType = "";
        switch (type) {
            case ELECTRICITY:
                billType = "ELECTRICITY";
                break;
            case GAS:
                billType = "GAS";
                break;
        }
        Calendar start = utilities.getStartDate();
        Calendar end = utilities.getEndDate();
        float amount = utilities.getElectricityAmount() + utilities.getGasAmount();
        int numPpl = utilities.getNumberOfPeople();

        int startYear = start.get(Calendar.YEAR);
        int startMonth = start.get(Calendar.MONTH);
        int startDay = start.get(Calendar.DAY_OF_MONTH);

        int endYear = end.get(Calendar.YEAR);
        int endMonth = end.get(Calendar.MONTH);
        int endDay = end.get(Calendar.DAY_OF_MONTH);

        String[] values = new String[]{billType, String.valueOf(amount), String.valueOf(startYear), String.valueOf(startMonth),
                                        String.valueOf(startDay), String.valueOf(endYear), String.valueOf(endMonth),
                                        String.valueOf(endDay), String.valueOf(numPpl)};

        String where = KEY_BILL_TYPE + "=? and " + KEY_AMOUNT + "=? and " + KEY_START_YEAR + "=? and " +
                        KEY_START_MONTH + "=? and " + KEY_START_DAY + "=? and " + KEY_END_YEAR + "=? and " +
                        KEY_END_MONTH + "=? and " + KEY_END_DAY + "=? and " + KEY_NUM_PPL + "=?";

        Cursor c = 	db.query(true, DATABASE_UTILITIES_TABLE, UTILITIES_ALL_KEYS,
                where, values, null, null, null, null);
        long id = -1;

        if (c != null) {
            c.moveToFirst();
            id = c.getLong(COL_ROWID);
            c.close();
        }
        return id;
    }

    public long findJourney(Journey journey) {

        String[] values = new String[] {};
        String where = "";

        Transportation transportation = journey.getTransportation();
        Route route = journey.getRoute();

        String routeName = route.getName();
        float cityDistance = route.getCityDriveDistance();
        float hwyDistance = route.getHighwayDriveDistance();

        Transportation.TRANSPORTATION_TYPE type = transportation.getType();
        String transportationType = "";
        switch (type) {
            case CAR:
                transportationType = "CAR";
                Car car = (Car) transportation;
                String name = car.getNickname();
                String model = car.getModel();
                String make = car.getMake();
                int year = car.getYear();
                String displacementVol = car.getDisplacementVol();
                String transmissionType = car.getTransmissionType();
                String fuelType = car.getFuelType();
                float cityMPG = car.getMilesPerGallonCity();
                float hwyMPG = car.getMilesPerGallonHway();

                values = new String[]{transportationType, name, model, make, String.valueOf(year), displacementVol, transmissionType, fuelType,
                        String.valueOf(cityMPG), String.valueOf(hwyMPG), routeName, String.valueOf(cityDistance), String.valueOf(hwyDistance)};

                where = KEY_TYPE + "=? and " + KEY_NAME + "=? and " + KEY_MODEL + "=? and "
                        + KEY_MAKE + "=? and " + KEY_YEAR + "=? and "+ KEY_DISPLACEMENT_VOL + "=? and "
                        + KEY_TRANSMISSION_TYPE + "=? and " + KEY_FUEL_TYPE + "=? and " + KEY_CITYMPG + "=? and "
                        + KEY_HWYMPG + "=? and " + KEY_ROUTE_NAME + "=? and " + KEY_CITYDISTANCE + "=? and "
                        + KEY_HWYDISTANCE + "=?";
                break;
            case BUS:
                transportationType = "BUS";
                break;
            case SKYTRAIN:
                transportationType = "SKYTRAIN";
                break;
            case BIKE:
                transportationType = "BIKE";
                break;
            case WALK:
                transportationType = "WALK";
                break;
        }

        if(type != Transportation.TRANSPORTATION_TYPE.CAR) {
            values = new String[]{transportationType, routeName, String.valueOf(cityDistance), String.valueOf(hwyDistance)};
            where = KEY_TYPE + "=? and " + KEY_ROUTE_NAME + "=? and " + KEY_CITYDISTANCE + "=? and " + KEY_HWYDISTANCE + "=?";
        }
        Cursor c = 	db.query(true, DATABASE_JOURNEY_TABLE, JOURNEY_ALL_KEYS,
                where, values, null, null, null, null);
        long id = -1;

        if (c != null) {
            c.moveToFirst();
            id = c.getLong(COL_ROWID);
            c.close();
        }
        return id;
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

    public boolean updateRoute(long rowId, Route route) {
        String where = KEY_ROWID + "=" + rowId;

        String name = route.getName();
        float cityDistance = route.getCityDriveDistance();
        float hwyDistance = route.getHighwayDriveDistance();

        ContentValues newValues = new ContentValues();
        newValues.put(KEY_ROUTE_NAME, name);
        newValues.put(KEY_CITYDISTANCE, cityDistance);
        newValues.put(KEY_HWYDISTANCE, hwyDistance);

        // Insert it into the database.
        return db.update(DATABASE_ROUTE_TABLE, newValues, where, null) != 0;
    }

    public boolean updateJourney(long rowId, Journey journey) {
        String where = KEY_ROWID + "=" + rowId;

        Transportation transportation = journey.getTransportation();
        Route route = journey.getRoute();
        Transportation.TRANSPORTATION_TYPE type = transportation.getType();
        ContentValues newValues = new ContentValues();

        switch (type) {
            case CAR:
                Car car = (Car) transportation;
                String name = car.getNickname();
                String model = car.getModel();
                String make = car.getMake();
                int year = car.getYear();
                String displacementVol = car.getDisplacementVol();
                String transmissionType = car.getTransmissionType();
                String fuelType = car.getFuelType();
                float cityMPG = car.getMilesPerGallonCity();
                float hwyMPG = car.getMilesPerGallonHway();

                int value = Transportation.TRANSPORTATION_TYPE.CAR.ordinal();
                newValues.put(KEY_TYPE, Transportation.TYPE[value]);
                newValues.put(KEY_NAME, name);
                newValues.put(KEY_MODEL, model);
                newValues.put(KEY_MAKE, make);
                newValues.put(KEY_YEAR, year);
                newValues.put(KEY_DISPLACEMENT_VOL, displacementVol);
                newValues.put(KEY_TRANSMISSION_TYPE, transmissionType);
                newValues.put(KEY_FUEL_TYPE, fuelType);
                newValues.put(KEY_CITYMPG, cityMPG);
                newValues.put(KEY_HWYMPG, hwyMPG);
                break;
            case BUS:
                value = Transportation.TRANSPORTATION_TYPE.BUS.ordinal();
                newValues.put(KEY_TYPE, Transportation.TYPE[value]);
                break;
            case SKYTRAIN:
                value = Transportation.TRANSPORTATION_TYPE.SKYTRAIN.ordinal();
                newValues.put(KEY_TYPE, Transportation.TYPE[value]);
                break;
            case BIKE:
                value = Transportation.TRANSPORTATION_TYPE.BIKE.ordinal();
                newValues.put(KEY_TYPE, Transportation.TYPE[value]);
                break;
            case WALK:
                value = Transportation.TRANSPORTATION_TYPE.WALK.ordinal();
                newValues.put(KEY_TYPE, Transportation.TYPE[value]);
                break;
        }

        String routeName = route.getName();
        float cityDistance = route.getCityDriveDistance();
        float hwyDistance = route.getHighwayDriveDistance();

        newValues.put(KEY_ROUTE_NAME, routeName);
        newValues.put(KEY_CITYDISTANCE, cityDistance);
        newValues.put(KEY_HWYDISTANCE, hwyDistance);

        // Insert it into the database.
        return db.update(DATABASE_JOURNEY_TABLE, newValues, where, null) != 0;
    }

    public boolean updateUtilities(long rowId, Utilities utilities) {
        String where = KEY_ROWID + "=" + rowId;

        Utilities.BILL type = utilities.getBillMode();
        String billType = "";
        switch (type) {
            case ELECTRICITY:
                billType = "ELECTRICITY";
                break;
            case GAS:
                billType = "GAS";
                break;
        }
        Calendar start = utilities.getStartDate();
        Calendar end = utilities.getEndDate();
        float amount = utilities.getElectricityAmount() + utilities.getGasAmount();
        int numPpl = utilities.getNumberOfPeople();

        int startYear = start.get(Calendar.YEAR);
        int startMonth = start.get(Calendar.MONTH);
        int startDay = start.get(Calendar.DAY_OF_MONTH);

        int endYear = end.get(Calendar.YEAR);
        int endMonth = end.get(Calendar.MONTH);
        int endDay = end.get(Calendar.DAY_OF_MONTH);

        ContentValues newValues = new ContentValues();
        newValues.put(KEY_BILL_TYPE, billType);
        newValues.put(KEY_AMOUNT, amount);
        newValues.put(KEY_START_YEAR, startYear);
        newValues.put(KEY_START_MONTH, startMonth);
        newValues.put(KEY_START_DAY, startDay);
        newValues.put(KEY_END_YEAR, endYear);
        newValues.put(KEY_END_MONTH, endMonth);
        newValues.put(KEY_END_DAY, endDay);
        newValues.put(KEY_NUM_PPL, numPpl);

        // Insert it into the database.
        return db.update(DATABASE_UTILITIES_TABLE, newValues, where, null) != 0;
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
            _db.execSQL(ROUTE_DATABASE_CREATE_SQL);
            _db.execSQL(JOURNEY_DATABASE_CREATE_SQL);
            _db.execSQL(UTILITIES_DATABASE_CREATE_SQL);

        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading application's database from version " + oldVersion
                    + " to " + newVersion + ", which will destroy all old data!");

            // Destroy old database:
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TOTAL_TABLE);
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_CAR_TABLE);
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_ROUTE_TABLE);
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_JOURNEY_TABLE);
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_UTILITIES_TABLE);

            // Recreate new database:
            onCreate(_db);
        }
    }
}
