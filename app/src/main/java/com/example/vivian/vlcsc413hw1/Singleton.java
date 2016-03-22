package com.example.vivian.vlcsc413hw1;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.android.volley.Request;

import android.content.Context;

/**
 * Created by Vivian on 2/24/16.
 */
public class Singleton {
    private static Singleton instance;
    private RequestQueue rQueue;
    private static Context myContext;

    // Constructor class
    private Singleton(Context context) {
        myContext = context;
        rQueue = getRequestQueue();
    }

    // Tests if the instance exists. If it doesn’t exist, then the instance
    // gets instantiated as a new Singleton.
    public static synchronized Singleton getsInstance(Context context) {
        if (instance == null) {
            instance = new Singleton(context);
        }
        return instance;
    }

    // Instantiates the request queue.
    public RequestQueue getRequestQueue() {
        if (rQueue == null) {
            rQueue = Volley.newRequestQueue(myContext.getApplicationContext());
        }
        return rQueue;
    }

    // Adds each request to the request queue.
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
