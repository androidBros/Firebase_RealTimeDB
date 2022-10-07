package com.example.firebase_realtimedb;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.net.MalformedURLException;
import java.net.URL;

public class NetworkRequest {
    public interface RequestStrategy {
        static void request(AppCompatActivity activity, Context context, URL url, int flag){
            Intent intent = new Intent(context, NetworkIntentService.class);
            intent.putExtra("url",url.toString());
            activity.startService(intent);
        }
    }
    public static class RequestGoCampingNetwork implements RequestStrategy{
        private static URL goCampingServerURL;

        private static void setXYgoCampingServerURL(Double lat, Double lon){
            Uri.Builder builder = new Uri.Builder()
                    .scheme("http")
                    .authority("api.visitkorea.or.kr")
                    .appendPath("openapi")
                    .appendPath("service")
                    .appendPath("rest")
                    .appendPath("GoCamping")
                    .appendPath("locationBasedList")
                    .appendQueryParameter("MobileOS", "AND")
                    .appendQueryParameter("MobileApp", "AppTest")
                    .appendQueryParameter("ServiceKey", "7WALrTfheoEJehY3WIHFZ+eOBW7iXbzkozVjJCEaPXEFm56hhkBuP58K+mEAMdpjdmgMzeMqLwOddGUIPCshCQ==")
                    .appendQueryParameter("mapX", lon.toString())
                    .appendQueryParameter("mapY", lat.toString())
                    .appendQueryParameter("radius", "10000")
                    .appendQueryParameter("numOfRows", "500")
                    .appendQueryParameter("_type", "json");
            Uri uri = builder.build();
            try {
                goCampingServerURL = new URL(uri.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        public static void requestCampInfo(AppCompatActivity activity, Context context, Double lat, Double lon){
            setXYgoCampingServerURL(lat, lon);
            RequestStrategy.request(activity, context, goCampingServerURL,0);
        }
    }
}
