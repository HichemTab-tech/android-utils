package com.hichemtech.android_utils.http;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hichemtech.android_utils.Logger;

import java.util.HashMap;
import java.util.Map;

public class RequestsVolley {
    private final Map<String,String> params;
    private final String url;
    private boolean noTimeOut = false;
    private final Context context;
    private RequestQueue queue;

    private RequestsListener.Response responseListener;
    private RequestsListener.ErrorResponse errorResponseListener;
    private RequestsListener.RequestStart requestStartListener;

    public static class RequestsListener {

        public interface Response{
            void onResponse(String response);
        }
        public interface ErrorResponse{
            void onErrorResponse(VolleyError error);
        }
        public interface RequestStart{
            void onRequestStart();
        }
    }

    public void setResponseListener(RequestsListener.Response responseListener) {
        this.responseListener = responseListener;
    }

    public void setErrorResponseListener(RequestsListener.ErrorResponse errorResponseListener) {
        this.errorResponseListener = errorResponseListener;
    }

    public void setRequestStartListener(RequestsListener.RequestStart requestStartListener) {
        this.requestStartListener = requestStartListener;
    }

    public RequestsVolley(Context context, String url, Map<String,String> params) {
        this.params = params;
        this.url = url;
        this.context = context;
    }

    public RequestsVolley(Context context, String url) {
        this.params = new HashMap<>();
        this.url = url;
        this.context = context;
    }

    public void setNoTimeOut(boolean noTimeOut) {
        this.noTimeOut = noTimeOut;
    }

    public void start() {
        StringRequest stringRequest = setRequest();
        if (requestStartListener != null) {
            requestStartListener.onRequestStart();
        }
        queue.add(stringRequest);
    }

    private StringRequest setRequest() {
        queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    Logger.e("RequestsVolley:response", response, true);
                    if (responseListener != null) {
                        responseListener.onResponse(response);
                    }
                },
                error -> {
                    if (errorResponseListener != null) {
                        Log.e("onRequestFailed", String.valueOf(error));
                        errorResponseListener.onErrorResponse(error);
                    }
                }
        ){
            @Override
            protected Map<String,String> getParams(){
                return params;
            }
            @Override
            public Map<String, String> getHeaders() {
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        int timeout = 0;
        if (noTimeOut) {
            timeout = 10000;
        }
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return stringRequest;
    }
}
