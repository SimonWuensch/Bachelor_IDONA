package com.src.sim.metaioapplication.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.src.sim.metaioapplication.logic.History;
import com.src.sim.metaioapplication.logic.resource.Location;
import com.src.sim.metaioapplication.logic.resource.LocationOnly;

import java.util.ArrayList;
import java.util.List;

public class MyDataBaseSQLite extends SQLiteOpenHelper {

    private static final String TAG = MyDataBaseSQLite.class.getSimpleName();

    private static final String DATABASE_NAME = "idona.db";
    private static final int DATABASE_VERSION = 1;

    private static final String _ID = "id";
    private static final String _LOCATION_JSON = "locationjson";
    private static final String _HISTORY_JSON = "historyjson";

    private static final String TABLE_IDONA = "idona";

    private static final String TABLE_IDONA_CREATE =
            "CREATE TABLE "
                + TABLE_IDONA + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                      + _LOCATION_JSON + " VARCHAR(1000), "
                                      + _HISTORY_JSON + " VARCHAR(20000));";

    private static final String TABLE_TODO_DROP =
            "DROP TABLE IF EXISTS " + TABLE_IDONA;

    public MyDataBaseSQLite(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_IDONA_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Database upgrade from version "
                + oldVersion + " to "
                + newVersion);
        db.execSQL(TABLE_TODO_DROP);
        onCreate(db);
    }

    public Cursor queryTableIdona(){
        SQLiteDatabase db = getWritableDatabase();
        return db.query(TABLE_IDONA, null, null, null, null, null, _ID + " DESC");
    }

    public List<LocationOnly> getLocationOnlyList(){
        List<LocationOnly> locations = new ArrayList<LocationOnly>();
            Cursor cursor = queryTableIdona();
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        long id = Long.parseLong(cursor.getString(cursor.getColumnIndex(_ID)));
                        String locationJson = cursor.getString(cursor.getColumnIndex(_LOCATION_JSON));
                        LocationOnly location = LocationOnly.JsonToLocationOnly(locationJson);
                        locations.add(location);
                        location.setId(id);
                        cursor.moveToNext();
                    }
                }
            }
            cursor.close();
            return locations;
    }

    public History getHistory(LocationOnly location){
        Cursor cursor = queryTableIdona();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    long id = Long.parseLong(cursor.getString(cursor.getColumnIndex(_ID)));
                    if(id == location.getId()){
                        History history = History.JsonToTrackerInitiator(cursor.getString(cursor.getColumnIndex(_HISTORY_JSON)));
                        history.setId(id);
                        return history;
                    }
                    cursor.moveToNext();
                }
            }
        }
        cursor.close();
        return null;
    }

    public void addLocation(final Location location){
        try{
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(_LOCATION_JSON, location.toJsonLocationOnly());
            values.put(_HISTORY_JSON, location.getHistory().toJson());
            db.insert(TABLE_IDONA, null, values);
        }catch(SQLiteException e){
            Log.e(TAG, "ADD TODO ERROR: " + location.toJsonLocationOnly(), e);
        }finally{
            Log.d(TAG, "ADD TODO: " + location.toJsonLocationOnly());
        }
    }


    public void deleteLocation(Location location){
        try{
            SQLiteDatabase db = getWritableDatabase();
            db.delete(TABLE_IDONA, _ID + " = ?", new String[] {
                    Long.toString(location.getId())
            });
        }catch(SQLiteException e){
            Log.e(TAG, "DELETE ERROR: ", e);
        }finally{
            Log.d(TAG, "DELETE: " + location.toJsonLocationOnly());
        }
    }

    public void updateLocation(Location location){
        try{
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(_LOCATION_JSON, location.toJsonLocationOnly());
            values.put(_HISTORY_JSON, location.getHistory().toJson());
            db.update(TABLE_IDONA, values, _ID + " = ?", new String[] {
                    Long.toString(location.getId())});
        }catch(SQLiteException e){
            Log.e(TAG, "UPDATE ERROR TODO ", e);
        }finally{
            Log.d(TAG, "UPDATE TODO " + location.toJsonLocationOnly());
        }
    }
}
