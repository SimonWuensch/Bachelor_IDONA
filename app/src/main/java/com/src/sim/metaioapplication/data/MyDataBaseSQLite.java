package com.src.sim.metaioapplication.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.src.sim.metaioapplication.logic.resource.History;
import com.src.sim.metaioapplication.logic.resource.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyDataBaseSQLite extends SQLiteOpenHelper {

    private Map<Location, History> locationHistoryMap = new HashMap<>();

    private static final String TAG = MyDataBaseSQLite.class.getSimpleName();

    private static final String DATABASE_NAME = "idona.db";
    private static final int DATABASE_VERSION = 6;

    private static final String _ID = "id";
    private static final String _LOCATION_JSON = "locationjson";
    private static final String _HISTORY_JSON = "historyjson";

    private static final String TABLE_IDONA = "idona";

    private static final String TABLE_IDONA_CREATE =
            "CREATE TABLE "
                + TABLE_IDONA + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                      + _LOCATION_JSON + " VARCHAR(250), "
                                      + _HISTORY_JSON + " VARCHAR(20000));";

    private static final String TABLE_TODO_DROP =
            "DROP TABLE IF EXISTS " + TABLE_IDONA;

    public MyDataBaseSQLite(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        getLocationHistoryMap();
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

    public Map<Location, History> getLocationHistoryMap(){
        if(locationHistoryMap != null)
            return locationHistoryMap;

        locationHistoryMap = new HashMap<Location, History>();
        Cursor cursor = queryTableIdona();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    long id = Long.parseLong(cursor.getString(cursor.getColumnIndex(_ID)));
                    String locationJson = cursor.getString(cursor.getColumnIndex(_LOCATION_JSON));
                    String historyJson = cursor.getString(cursor.getColumnIndex(_HISTORY_JSON));
                    Location location = Location.JsonToLocation(locationJson);
                    History history = History.JsonToHistory(historyJson);
                    location.setId(id);
                    history.setId(id);
                    locationHistoryMap.put(location, history);

                    cursor.moveToNext();
                }
            }
        }
        cursor.close();
        Log.d(TAG, "LocationHistoryMap obtained with the size " + locationHistoryMap.size());
        return locationHistoryMap;
    }

    public List<Location> getLocationList(){
        if(locationHistoryMap.size() > 0)
           return new ArrayList<Location>(locationHistoryMap.keySet());

        List<Location> locations = new ArrayList<Location>();
            Cursor cursor = queryTableIdona();
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        long id = Long.parseLong(cursor.getString(cursor.getColumnIndex(_ID)));
                        String locationJson = cursor.getString(cursor.getColumnIndex(_LOCATION_JSON));
                        Location location = Location.JsonToLocation(locationJson);
                        locations.add(location);
                        location.setId(id);
                        cursor.moveToNext();
                    }
                }
            }
        cursor.close();
        Log.d(TAG, "Locationlist obtained with the size " + locations.size());
        return locations;
    }

    public History getHistory(Location location){
        if(locationHistoryMap != null)
            for(Location locationValue : locationHistoryMap.keySet()){
                if(locationValue.getId() == location.getId()){
                    return locationHistoryMap.get(locationValue);
                }
            }

        Cursor cursor = queryTableIdona();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    long id = Long.parseLong(cursor.getString(cursor.getColumnIndex(_ID)));
                    if(id == location.getId()){
                        History history = History.JsonToHistory(cursor.getString(cursor.getColumnIndex(_HISTORY_JSON)));
                        history.setId(id);
                        Log.d(TAG, "History obtained");
                        return history;
                    }
                    cursor.moveToNext();
                }
            }
        }
        cursor.close();
        throw new NullPointerException("Database contains not this location " + location.toJson());
    }

    public void addLocation(final Location location){
        if(location.getHistory() != null) {
            History history = location.getHistory();
            location.setHistory(null);
            if(locationHistoryMap != null) {
                locationHistoryMap.put(location, history);
            }

            try {
                SQLiteDatabase db = getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(_LOCATION_JSON, location.toJson());
                values.put(_HISTORY_JSON, history.toJson());
                db.insert(TABLE_IDONA, null, values);
            } catch (SQLiteException e) {
                Log.e(TAG, "ADD LOCATION ERROR: " + location.toJson(), e);
            } finally {
                Log.d(TAG, "ADD LOCATION: " + location.toJson());
            }
        }else{
            throw new NullPointerException("Location does not have a history... " + location.toJson());
        }
    }

    public void deleteLocation(Location location){
        if(locationHistoryMap != null){
            locationHistoryMap.remove(location);
        }
        try{
            SQLiteDatabase db = getWritableDatabase();
            db.delete(TABLE_IDONA, _ID + " = ?", new String[] {
                    Long.toString(location.getId())
            });
        }catch(SQLiteException e){
            Log.e(TAG, "DELETE ERROR: ", e);
        }finally{
            Log.d(TAG, "DELETE: " + location.toJson());
        }
    }

    public void updateLocationAndHistory(Location location, History history){
        String locationJson =  location.toJson();
        String historyJson = history.toJson();
        try{
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(_LOCATION_JSON, locationJson);
            values.put(_HISTORY_JSON, historyJson);
            db.update(TABLE_IDONA, values, _ID + " = ?", new String[] {
                    Long.toString(location.getId())});
        }catch(SQLiteException e){
            Log.e(TAG, "UPDATE ERROR - LOCATION AND HISTORY ", e);
        }finally{
            Log.d(TAG, "UPDATE - LOCATION AND HISTORY: " + locationJson + " HISTORY: " + historyJson);
        }
    }
}
