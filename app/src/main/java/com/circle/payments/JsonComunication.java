package com.circle.payments;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Leonardo on 03/12/2017.
 */

public class JsonComunication {
    private static JsonComunication mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static Context mCtx;

    private JsonComunication(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();

        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    public static synchronized JsonComunication getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new JsonComunication(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}
//        implements Runnable {
//
//    public static Thread performOnBackgroundThread(final Runnable runnable) {
//        final Thread t = new Thread() {
//            @Override
//            public void run() {
//                try {
//                    runnable.run();
//                } finally {
//
//                }
//            }
//        };
//        t.start();
//        return t;
//    }
//
//    public JsonComunication(){
//        //set context variables if required
//    }
//
//    @Override
//    public void run() {
//        // Instantiate the RequestQueue.
//        RequestQueue queue = Volley.newRequestQueue(this);
//        String url ="http://www.google.com";
//
//        // Request a string response from the provided URL.
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // Display the first 500 characters of the response string.
//                        mTextView.setText("Response is: "+ response.substring(0,500));
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
////                mTextView.setText("That didn't work!");
//            }
//        });
//        // Add the request to the RequestQueue.
//        queue.add(stringRequest);
//
////        URL reqURL = null; //the URL we will send the request to
////        HttpURLConnection request = null;
////        try {
////            reqURL = new URL("http://www.hackaton-cielo-rbm4.c9users.io:8080");
////            request = (HttpURLConnection) (reqURL.openConnection());
////
////        } catch (MalformedURLException e) {
////            e.printStackTrace();
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////        String post = "this will be the post data that you will send";
////        request.setDoOutput(true);
////        request.addRequestProperty("Content-Length", Integer.toString(post.length())); //add the content length of the post data
////        request.addRequestProperty("Content-Type", "application/x-www-form-urlencoded"); //add the content type of the request, most post data is of this type
////        try {
////            request.setRequestMethod("POST");
////            request.connect();
////            OutputStreamWriter writer = new OutputStreamWriter(request.getOutputStream()); //we will write our request data here
////            writer.write(post);
////            writer.flush();
////
////        } catch (ProtocolException e) {
////            e.printStackTrace();
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//    }
//
////    @Override
////    protected void onPreExecute() {
////        super.onPreExecute();
////    }
//
//
////    @Override
////    protected String doInBackground(String... params) {
////
////        String urlString = params[0]; // URL to call
////
////        String data = params[1]; //data to post
////
////        OutputStream out = null;
////        try {
////
////            URL url = new URL(urlString);
////
////            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
////
////            out = new BufferedOutputStream(urlConnection.getOutputStream());
////
////            BufferedWriter writer = new BufferedWriter (new OutputStreamWriter(out, "UTF-8"));
////
////            writer.write(data);
////
////            writer.flush();
////
////            writer.close();
////
////            out.close();
////
////            urlConnection.connect();
////
////
////        } catch (Exception e) {
////
////            System.out.println(e.getMessage());
////
////
////
////        }
////    return "";
////    }
//}
