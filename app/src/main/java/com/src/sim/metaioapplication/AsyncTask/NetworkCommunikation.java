package com.src.sim.metaioapplication.asynctask;

import android.app.Activity;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.src.sim.metaioapplication.listener.CustomListener;
import com.src.sim.metaioapplication.logic.resource.History;
import com.src.sim.metaioapplication.logic.resource.Location;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by Simon on 22.05.2015.
 */
public class NetworkCommunikation extends AsyncTask<String, Void, String> {

    private static String PROTOCOL = "http://";
    private CookieManager msCookieManager;
    private Activity activity;
    private CustomListener customListener;

    public NetworkCommunikation(Activity activity){
        super();
        this.activity = activity;
        this.customListener = (CustomListener) activity;
    }

    public static void getResult(Activity activity){
        NetworkCommunikation netCom = new NetworkCommunikation(activity);
        netCom.execute();
    }

    @Override
    protected String doInBackground(String... params) {
        //doGetRequest("address");
        Log.d(NetworkCommunikation.class.getSimpleName(), "doinBackground");
        return "{\"history\":{\"lObjectMap\":[{\"key\":\"FIREDRENCHER\",\"value\":[{\"description\":\"FIREDRENCHER\",\"kind\":\"FIREDRENCHER\"}]},{\"key\":\"ROOM\",\"value\":[{\"description\":\"C\",\"kind\":\"ROOM\"},{\"description\":\"E\",\"kind\":\"ROOM\"},{\"description\":\"Q\",\"kind\":\"ROOM\"},{\"description\":\"A\",\"kind\":\"ROOM\"},{\"description\":\"S\",\"kind\":\"ROOM\"},{\"description\":\"R\",\"kind\":\"ROOM\"},{\"description\":\"H\",\"kind\":\"ROOM\"},{\"description\":\"T\",\"kind\":\"ROOM\"},{\"description\":\"G\",\"kind\":\"ROOM\"},{\"description\":\"M\",\"kind\":\"ROOM\"},{\"description\":\"D\",\"kind\":\"ROOM\"},{\"description\":\"F\",\"kind\":\"ROOM\"},{\"description\":\"N\",\"kind\":\"ROOM\"},{\"description\":\"J\",\"kind\":\"ROOM\"},{\"description\":\"B\",\"kind\":\"ROOM\"},{\"description\":\"L\",\"kind\":\"ROOM\"},{\"description\":\"K\",\"kind\":\"ROOM\"}]},{\"key\":\"TOILET\",\"value\":[{\"description\":\"HANDICAPPED\",\"kind\":\"TOILET\"},{\"description\":\"MALE\",\"kind\":\"TOILET\"},{\"description\":\"FEMAIL\",\"kind\":\"TOILET\"}]},{\"key\":\"MEDIKIT\",\"value\":[{\"description\":\"MEDIKIT\",\"kind\":\"MEDIKIT\"}]},{\"key\":\"EMERGENCYEXIT\",\"value\":[{\"description\":\"EMERGENCYEXIT\",\"kind\":\"EMERGENCYEXIT\"}]}],\"trackerMap\":{\"1\":{\"directions\":[{\"direction\":\"<-\",\"distance\":6,\"locationObjectMap\":[{\"key\":\"FIREDRENCHER\",\"value\":[{\"description\":\"FIREDRENCHER\",\"kind\":\"FIREDRENCHER\"}]},{\"key\":\"ROOM\",\"value\":[{\"description\":\"C\",\"kind\":\"ROOM\"},{\"description\":\"E\",\"kind\":\"ROOM\"},{\"description\":\"Q\",\"kind\":\"ROOM\"}]}],\"locationObjects\":{\"lefthand\":[{\"key\":{\"description\":\"C\",\"kind\":\"ROOM\"},\"value\":1},{\"key\":{\"description\":\"E\",\"kind\":\"ROOM\"},\"value\":5}],\"righthand\":[{\"key\":{\"description\":\"Q\",\"kind\":\"ROOM\"},\"value\":2},{\"key\":{\"description\":\"FIREDRENCHER\",\"kind\":\"FIREDRENCHER\"},\"value\":4}]},\"trackerID\":2},{\"direction\":\"->\",\"distance\":8,\"locationObjectMap\":[{\"key\":\"TOILET\",\"value\":[{\"description\":\"HANDICAPPED\",\"kind\":\"TOILET\"}]},{\"key\":\"ROOM\",\"value\":[{\"description\":\"A\",\"kind\":\"ROOM\"}]},{\"key\":\"MEDIKIT\",\"value\":[{\"description\":\"MEDIKIT\",\"kind\":\"MEDIKIT\"}]}],\"locationObjects\":{\"lefthand\":[{\"key\":{\"description\":\"HANDICAPPED\",\"kind\":\"TOILET\"},\"value\":2}],\"righthand\":[{\"key\":{\"description\":\"A\",\"kind\":\"ROOM\"},\"value\":3},{\"key\":{\"description\":\"MEDIKIT\",\"kind\":\"MEDIKIT\"},\"value\":5}]},\"trackerID\":6},{\"direction\":\"v\",\"distance\":4,\"locationObjectMap\":[],\"locationObjects\":{\"lefthand\":[],\"righthand\":[]},\"trackerID\":9}],\"id\":1,\"trackers\":[2,6,9]},\"2\":{\"directions\":[{\"direction\":\"<-\",\"distance\":6,\"locationObjectMap\":[{\"key\":\"FIREDRENCHER\",\"value\":[{\"description\":\"FIREDRENCHER\",\"kind\":\"FIREDRENCHER\"}]},{\"key\":\"ROOM\",\"value\":[{\"description\":\"C\",\"kind\":\"ROOM\"},{\"description\":\"E\",\"kind\":\"ROOM\"},{\"description\":\"Q\",\"kind\":\"ROOM\"}]}],\"locationObjects\":{\"lefthand\":[{\"key\":{\"description\":\"Q\",\"kind\":\"ROOM\"},\"value\":4},{\"key\":{\"description\":\"FIREDRENCHER\",\"kind\":\"FIREDRENCHER\"},\"value\":2}],\"righthand\":[{\"key\":{\"description\":\"C\",\"kind\":\"ROOM\"},\"value\":5},{\"key\":{\"description\":\"E\",\"kind\":\"ROOM\"},\"value\":1}]},\"trackerID\":1},{\"direction\":\"v\",\"distance\":2,\"locationObjectMap\":[{\"key\":\"ROOM\",\"value\":[{\"description\":\"S\",\"kind\":\"ROOM\"},{\"description\":\"R\",\"kind\":\"ROOM\"}]}],\"locationObjects\":{\"lefthand\":[{\"key\":{\"description\":\"S\",\"kind\":\"ROOM\"},\"value\":1}],\"righthand\":[{\"key\":{\"description\":\"R\",\"kind\":\"ROOM\"},\"value\":1}]},\"trackerID\":12},{\"direction\":\"->\",\"distance\":2,\"locationObjectMap\":[],\"locationObjects\":{\"lefthand\":[],\"righthand\":[]},\"trackerID\":3}],\"id\":2,\"trackers\":[1,12,3]},\"3\":{\"directions\":[{\"direction\":\"->\",\"distance\":2,\"locationObjectMap\":[],\"locationObjects\":{\"lefthand\":[],\"righthand\":[]},\"trackerID\":2},{\"direction\":\"<-\",\"distance\":8,\"locationObjectMap\":[{\"key\":\"ROOM\",\"value\":[{\"description\":\"H\",\"kind\":\"ROOM\"},{\"description\":\"T\",\"kind\":\"ROOM\"}]}],\"locationObjects\":{\"lefthand\":[{\"key\":{\"description\":\"H\",\"kind\":\"ROOM\"},\"value\":5}],\"righthand\":[{\"key\":{\"description\":\"T\",\"kind\":\"ROOM\"},\"value\":3}]},\"trackerID\":5},{\"direction\":\"v\",\"distance\":5,\"locationObjectMap\":[{\"key\":\"ROOM\",\"value\":[{\"description\":\"G\",\"kind\":\"ROOM\"}]}],\"locationObjects\":{\"lefthand\":[],\"righthand\":[{\"key\":{\"description\":\"G\",\"kind\":\"ROOM\"},\"value\":2}]},\"trackerID\":10},{\"direction\":\"v\",\"distance\":4,\"locationObjectMap\":[{\"key\":\"ROOM\",\"value\":[{\"description\":\"G\",\"kind\":\"ROOM\"}]}],\"locationObjects\":{\"lefthand\":[],\"righthand\":[{\"key\":{\"description\":\"G\",\"kind\":\"ROOM\"},\"value\":2}]},\"trackerID\":11}],\"id\":3,\"trackers\":[2,5,10,11]},\"4\":{\"directions\":[{\"direction\":\"->\",\"distance\":2,\"locationObjectMap\":[],\"locationObjects\":{\"lefthand\":[],\"righthand\":[]},\"trackerID\":9},{\"direction\":\"<-\",\"distance\":5,\"locationObjectMap\":[{\"key\":\"ROOM\",\"value\":[{\"description\":\"M\",\"kind\":\"ROOM\"},{\"description\":\"F\",\"kind\":\"ROOM\"},{\"description\":\"D\",\"kind\":\"ROOM\"}]},{\"key\":\"MEDIKIT\",\"value\":[{\"description\":\"MEDIKIT\",\"kind\":\"MEDIKIT\"}]}],\"locationObjects\":{\"lefthand\":[{\"key\":{\"description\":\"M\",\"kind\":\"ROOM\"},\"value\":2},{\"key\":{\"description\":\"MEDIKIT\",\"kind\":\"MEDIKIT\"},\"value\":4}],\"righthand\":[{\"key\":{\"description\":\"F\",\"kind\":\"ROOM\"},\"value\":5},{\"key\":{\"description\":\"D\",\"kind\":\"ROOM\"},\"value\":2}]},\"trackerID\":10},{\"direction\":\"v\",\"distance\":5,\"locationObjectMap\":[{\"key\":\"ROOM\",\"value\":[{\"description\":\"N\",\"kind\":\"ROOM\"}]}],\"locationObjects\":{\"lefthand\":[{\"key\":{\"description\":\"N\",\"kind\":\"ROOM\"},\"value\":3}],\"righthand\":[]},\"trackerID\":7}],\"id\":4,\"trackers\":[9,10,7]},\"5\":{\"directions\":[{\"direction\":\"->\",\"distance\":8,\"locationObjectMap\":[{\"key\":\"ROOM\",\"value\":[{\"description\":\"H\",\"kind\":\"ROOM\"},{\"description\":\"T\",\"kind\":\"ROOM\"}]}],\"locationObjects\":{\"lefthand\":[{\"key\":{\"description\":\"T\",\"kind\":\"ROOM\"},\"value\":5}],\"righthand\":[{\"key\":{\"description\":\"H\",\"kind\":\"ROOM\"},\"value\":3}]},\"trackerID\":3},{\"direction\":\"v\",\"distance\":3,\"locationObjectMap\":[{\"key\":\"TOILET\",\"value\":[{\"description\":\"MALE\",\"kind\":\"TOILET\"}]}],\"locationObjects\":{\"lefthand\":[],\"righthand\":[{\"key\":{\"description\":\"MALE\",\"kind\":\"TOILET\"},\"value\":3}]},\"trackerID\":11},{\"direction\":\"<-\",\"distance\":9,\"locationObjectMap\":[{\"key\":\"ROOM\",\"value\":[{\"description\":\"J\",\"kind\":\"ROOM\"}]},{\"key\":\"EMERGENCYEXIT\",\"value\":[{\"description\":\"EMERGENCYEXIT\",\"kind\":\"EMERGENCYEXIT\"}]}],\"locationObjects\":{\"lefthand\":[{\"key\":{\"description\":\"J\",\"kind\":\"ROOM\"},\"value\":8}],\"righthand\":[{\"key\":{\"description\":\"EMERGENCYEXIT\",\"kind\":\"EMERGENCYEXIT\"},\"value\":3}]},\"trackerID\":8}],\"id\":5,\"trackers\":[3,11,8]},\"6\":{\"directions\":[{\"direction\":\"<-\",\"distance\":8,\"locationObjectMap\":[{\"key\":\"TOILET\",\"value\":[{\"description\":\"HANDICAPPED\",\"kind\":\"TOILET\"}]},{\"key\":\"ROOM\",\"value\":[{\"description\":\"A\",\"kind\":\"ROOM\"}]},{\"key\":\"MEDIKIT\",\"value\":[{\"description\":\"MEDIKIT\",\"kind\":\"MEDIKIT\"}]}],\"locationObjects\":{\"lefthand\":[{\"key\":{\"description\":\"A\",\"kind\":\"ROOM\"},\"value\":5},{\"key\":{\"description\":\"MEDIKIT\",\"kind\":\"MEDIKIT\"},\"value\":3}],\"righthand\":[{\"key\":{\"description\":\"HANDICAPPED\",\"kind\":\"TOILET\"},\"value\":6}]},\"trackerID\":1},{\"direction\":\"v\",\"distance\":4,\"locationObjectMap\":[{\"key\":\"TOILET\",\"value\":[{\"description\":\"FEMAIL\",\"kind\":\"TOILET\"}]},{\"key\":\"ROOM\",\"value\":[{\"description\":\"B\",\"kind\":\"ROOM\"}]}],\"locationObjects\":{\"lefthand\":[{\"key\":{\"description\":\"FEMAIL\",\"kind\":\"TOILET\"},\"value\":1}],\"righthand\":[{\"key\":{\"description\":\"B\",\"kind\":\"ROOM\"},\"value\":2}]},\"trackerID\":9},{\"direction\":\"->\",\"distance\":11,\"locationObjectMap\":[{\"key\":\"EMERGENCYEXIT\",\"value\":[{\"description\":\"EMERGENCYEXIT\",\"kind\":\"EMERGENCYEXIT\"}]}],\"locationObjects\":{\"lefthand\":[{\"key\":{\"description\":\"EMERGENCYEXIT\",\"kind\":\"EMERGENCYEXIT\"},\"value\":9}],\"righthand\":[]},\"trackerID\":7}],\"id\":6,\"trackers\":[1,9,7]},\"7\":{\"directions\":[{\"direction\":\"v\",\"distance\":5,\"locationObjectMap\":[{\"key\":\"ROOM\",\"value\":[{\"description\":\"N\",\"kind\":\"ROOM\"}]}],\"locationObjects\":{\"lefthand\":[],\"righthand\":[{\"key\":{\"description\":\"N\",\"kind\":\"ROOM\"},\"value\":2}]},\"trackerID\":4},{\"direction\":\"<-\",\"distance\":11,\"locationObjectMap\":[{\"key\":\"EMERGENCYEXIT\",\"value\":[{\"description\":\"EMERGENCYEXIT\",\"kind\":\"EMERGENCYEXIT\"}]}],\"locationObjects\":{\"lefthand\":[],\"righthand\":[{\"key\":{\"description\":\"EMERGENCYEXIT\",\"kind\":\"EMERGENCYEXIT\"},\"value\":2}]},\"trackerID\":6},{\"direction\":\"->\",\"distance\":6,\"locationObjectMap\":[{\"key\":\"FIREDRENCHER\",\"value\":[{\"description\":\"FIREDRENCHER\",\"kind\":\"FIREDRENCHER\"}]},{\"key\":\"ROOM\",\"value\":[{\"description\":\"L\",\"kind\":\"ROOM\"}]}],\"locationObjects\":{\"lefthand\":[],\"righthand\":[{\"key\":{\"description\":\"FIREDRENCHER\",\"kind\":\"FIREDRENCHER\"},\"value\":3},{\"key\":{\"description\":\"L\",\"kind\":\"ROOM\"},\"value\":2}]},\"trackerID\":8}],\"id\":7,\"trackers\":[4,6,8]},\"8\":{\"directions\":[{\"direction\":\"->\",\"distance\":9,\"locationObjectMap\":[{\"key\":\"ROOM\",\"value\":[{\"description\":\"J\",\"kind\":\"ROOM\"}]},{\"key\":\"EMERGENCYEXIT\",\"value\":[{\"description\":\"EMERGENCYEXIT\",\"kind\":\"EMERGENCYEXIT\"}]}],\"locationObjects\":{\"lefthand\":[{\"key\":{\"description\":\"EMERGENCYEXIT\",\"kind\":\"EMERGENCYEXIT\"},\"value\":6}],\"righthand\":[{\"key\":{\"description\":\"J\",\"kind\":\"ROOM\"},\"value\":1}]},\"trackerID\":5},{\"direction\":\"<-\",\"distance\":6,\"locationObjectMap\":[{\"key\":\"FIREDRENCHER\",\"value\":[{\"description\":\"FIREDRENCHER\",\"kind\":\"FIREDRENCHER\"}]},{\"key\":\"ROOM\",\"value\":[{\"description\":\"L\",\"kind\":\"ROOM\"}]}],\"locationObjects\":{\"lefthand\":[{\"key\":{\"description\":\"FIREDRENCHER\",\"kind\":\"FIREDRENCHER\"},\"value\":3},{\"key\":{\"description\":\"L\",\"kind\":\"ROOM\"},\"value\":4}],\"righthand\":[]},\"trackerID\":7},{\"direction\":\"v\",\"distance\":5,\"locationObjectMap\":[{\"key\":\"ROOM\",\"value\":[{\"description\":\"K\",\"kind\":\"ROOM\"}]}],\"locationObjects\":{\"lefthand\":[],\"righthand\":[{\"key\":{\"description\":\"K\",\"kind\":\"ROOM\"},\"value\":2}]},\"trackerID\":10},{\"direction\":\"v\",\"distance\":6,\"locationObjectMap\":[{\"key\":\"ROOM\",\"value\":[{\"description\":\"K\",\"kind\":\"ROOM\"}]}],\"locationObjects\":{\"lefthand\":[],\"righthand\":[{\"key\":{\"description\":\"K\",\"kind\":\"ROOM\"},\"value\":4}]},\"trackerID\":11}],\"id\":8,\"trackers\":[5,7,10,11]},\"9\":{\"directions\":[{\"direction\":\"v\",\"distance\":4,\"locationObjectMap\":[],\"locationObjects\":{\"lefthand\":[],\"righthand\":[]},\"trackerID\":1},{\"direction\":\"->\",\"distance\":2,\"locationObjectMap\":[],\"locationObjects\":{\"lefthand\":[],\"righthand\":[]},\"trackerID\":4},{\"direction\":\"<-\",\"distance\":4,\"locationObjectMap\":[{\"key\":\"TOILET\",\"value\":[{\"description\":\"FEMAIL\",\"kind\":\"TOILET\"}]},{\"key\":\"ROOM\",\"value\":[{\"description\":\"B\",\"kind\":\"ROOM\"}]}],\"locationObjects\":{\"lefthand\":[{\"key\":{\"description\":\"B\",\"kind\":\"ROOM\"},\"value\":2}],\"righthand\":[{\"key\":{\"description\":\"FEMAIL\",\"kind\":\"TOILET\"},\"value\":3}]},\"trackerID\":6}],\"id\":9,\"trackers\":[1,4,6]},\"10\":{\"directions\":[{\"direction\":\"v\",\"distance\":5,\"locationObjectMap\":[{\"key\":\"ROOM\",\"value\":[{\"description\":\"G\",\"kind\":\"ROOM\"}]}],\"locationObjects\":{\"lefthand\":[{\"key\":{\"description\":\"G\",\"kind\":\"ROOM\"},\"value\":3}],\"righthand\":[]},\"trackerID\":3},{\"direction\":\"<-\",\"distance\":5,\"locationObjectMap\":[{\"key\":\"ROOM\",\"value\":[{\"description\":\"M\",\"kind\":\"ROOM\"},{\"description\":\"F\",\"kind\":\"ROOM\"},{\"description\":\"D\",\"kind\":\"ROOM\"}]},{\"key\":\"MEDIKIT\",\"value\":[{\"description\":\"MEDIKIT\",\"kind\":\"MEDIKIT\"}]}],\"locationObjects\":{\"lefthand\":[{\"key\":{\"description\":\"F\",\"kind\":\"ROOM\"},\"value\":0},{\"key\":{\"description\":\"D\",\"kind\":\"ROOM\"},\"value\":3}],\"righthand\":[{\"key\":{\"description\":\"M\",\"kind\":\"ROOM\"},\"value\":3},{\"key\":{\"description\":\"MEDIKIT\",\"kind\":\"MEDIKIT\"},\"value\":1}]},\"trackerID\":4},{\"direction\":\"^\",\"distance\":5,\"locationObjectMap\":[{\"key\":\"ROOM\",\"value\":[{\"description\":\"K\",\"kind\":\"ROOM\"}]}],\"locationObjects\":{\"lefthand\":[{\"key\":{\"description\":\"K\",\"kind\":\"ROOM\"},\"value\":3}],\"righthand\":[]},\"trackerID\":8},{\"direction\":\"->\",\"distance\":2,\"locationObjectMap\":[],\"locationObjects\":{\"lefthand\":[],\"righthand\":[]},\"trackerID\":11}],\"id\":10,\"trackers\":[3,4,8,11]},\"11\":{\"directions\":[{\"direction\":\"^\",\"distance\":4,\"locationObjectMap\":[{\"key\":\"ROOM\",\"value\":[{\"description\":\"G\",\"kind\":\"ROOM\"}]}],\"locationObjects\":{\"lefthand\":[{\"key\":{\"description\":\"G\",\"kind\":\"ROOM\"},\"value\":2}],\"righthand\":[]},\"trackerID\":3},{\"direction\":\"<-\",\"distance\":3,\"locationObjectMap\":[{\"key\":\"TOILET\",\"value\":[{\"description\":\"MALE\",\"kind\":\"TOILET\"}]}],\"locationObjects\":{\"lefthand\":[{\"key\":{\"description\":\"MALE\",\"kind\":\"TOILET\"},\"value\":0}],\"righthand\":[]},\"trackerID\":5},{\"direction\":\"v\",\"distance\":6,\"locationObjectMap\":[{\"key\":\"ROOM\",\"value\":[{\"description\":\"K\",\"kind\":\"ROOM\"}]}],\"locationObjects\":{\"lefthand\":[{\"key\":{\"description\":\"K\",\"kind\":\"ROOM\"},\"value\":2}],\"righthand\":[]},\"trackerID\":8},{\"direction\":\"->\",\"distance\":2,\"locationObjectMap\":[],\"locationObjects\":{\"lefthand\":[],\"righthand\":[]},\"trackerID\":10}],\"id\":11,\"trackers\":[3,5,8,10]},\"12\":{\"directions\":[{\"direction\":\"v\",\"distance\":2,\"locationObjectMap\":[{\"key\":\"ROOM\",\"value\":[{\"description\":\"S\",\"kind\":\"ROOM\"},{\"description\":\"R\",\"kind\":\"ROOM\"}]}],\"locationObjects\":{\"lefthand\":[{\"key\":{\"description\":\"R\",\"kind\":\"ROOM\"},\"value\":1}],\"righthand\":[{\"key\":{\"description\":\"S\",\"kind\":\"ROOM\"},\"value\":1}]},\"trackerID\":2}],\"id\":12,\"trackers\":[2]}}},\"name\":\"Test_Name\",\"number\":\"999\",\"place\":\"Test_Place\",\"street\":\"Test_Street\",\"zip\":\"99999\"}";
    }

    private String doGetRequest(String address){
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(address);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.addRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestMethod("GET");
            InputStream is = urlConnection.getInputStream();
            return IOUtils.toString(is);
        } catch (Exception ex) {
            Log.e(NetworkCommunikation.class.getSimpleName(), "" + ex.getMessage());
        }finally {
            urlConnection.disconnect();
        }
        return "";
    }

    private void updateCookieManager(HttpURLConnection urlConnection){
        msCookieManager = new CookieManager();
        Map<String, List<String>> headerFields = urlConnection.getHeaderFields();
        List<String> cookiesHeader = headerFields.get("Set-Cookie");
        if(cookiesHeader != null)
        {
            for (String cookie : cookiesHeader){
                msCookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
            }
        }
    }

    private void updateHttpURLConnection(HttpURLConnection urlConnection){
        if(msCookieManager.getCookieStore().getCookies().size() > 0)        {
            urlConnection.setRequestProperty("Cookie",
                    TextUtils.join(",", msCookieManager.getCookieStore().getCookies()));
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.d(NetworkCommunikation.class.getSimpleName(), "onPostExecute");


            Log.d(NetworkCommunikation.class.getSimpleName(), "Result: "  + result);
            Location location = Location.JsonToLocation(result);

                customListener.getDatabase().addLocation(location);
        Toast.makeText(activity, result, Toast.LENGTH_SHORT).show();
    }
}


