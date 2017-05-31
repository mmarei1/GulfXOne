package com.example.moham.gulfxone;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by moham on 30/05/2017.
 */

public class AppSingleton extends Application {

    private static AppSingleton mInstance;
    private RequestQueue mRequestQueue;

   private static Context mCtx;

    @Override
    public void onCreate(){
        super.onCreate();
        mInstance = this;
    }

    private AppSingleton(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized AppSingleton getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public void addToRequestQueue(StringRequest req) {
        getRequestQueue().add(req);
    }

}

