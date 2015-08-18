package com.src.sim.metaioapplication.asynctask;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.src.sim.metaioapplication.listener.CustomListener;
import com.src.sim.metaioapplication.logic.resource.Location;
import com.src.sim.metaioapplication.ui.activitiy.start.StartMenuActivity;
import com.src.sim.metaioapplication.ui.fragment.location.ListLocationFragment;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class NetworkCommunikation extends AsyncTask<String, Void, String> {

    private static String PROTOCOL = "http://";
    private CookieManager msCookieManager;
    private StartMenuActivity activity;
    private CustomListener customListener;

    public NetworkCommunikation(StartMenuActivity activity){
        super();
        this.activity = activity;
        this.customListener = (CustomListener) activity;
    }

    public static void getLocation(StartMenuActivity activity){
        NetworkCommunikation netCom = new NetworkCommunikation(activity);
        netCom.execute();
    }

    @Override
    protected String doInBackground(String... params) {
        return TestLocation.SHL;
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
        Log.d(NetworkCommunikation.class.getSimpleName(), "Result: "  + result);
        Location location = Location.JsonToLocation(result);
        customListener.getDatabase().addLocation(location);
        activity.showFragment(new ListLocationFragment(), StartMenuActivity.LOCATIONFRAGMENT);
    }
}


