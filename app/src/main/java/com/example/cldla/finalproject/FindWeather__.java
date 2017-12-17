package com.example.cldla.finalproject;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Owner on 2017-12-16.
 */

public class FindWeather__ {
    double lat, lon;
    JSONObject jwind, jtemp, jsky, jprecip;
    weatherInfo[] wI;
    int[] time={0,7,13,19,25};
    Retrofit retrofit;
    Call<JsonObject> call;
    ArrayList<weatherInfo> winfo;
    int end=0;
    WeatherListView_ weatherlistadapter_;
    TextView txtview;
    ViewPager pager=null;
    LayoutInflater layoutinf;


    public int getEnd() {
        return end;
    }

    public void setStr(String str) {
        this.str = str;
    }

    String str;

    //ListView lv
    public FindWeather__(double lat, double lon, ViewPager vp, LayoutInflater layoutinf) {
        Log.d("FindWeather_","/////////////Findweather");
        this.lat = lat;
        this.lon = lon;
        this.end = 0;
        this.pager = vp;
        Log.d("//findweather__","view pager : "+Integer.toString(vp.getId()));
        this.layoutinf = layoutinf;
        winfo = new ArrayList<weatherInfo>();
        wI = new weatherInfo[5];
        for(int i=0;i<5;i++){
            wI[i] = new weatherInfo();
        }



        retrofit =new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiService_now.BASEURL)
                .build();
        ApiService_now apiService_now = retrofit.create(ApiService_now.class);
        call = apiService_now.get3days(ApiService_now.APPKEY,1,lat,lon);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.isSuccessful()){
                    //날씨데이터를 받아옴
                    JsonObject object = response.body();
                    if (object != null) {
                        setStr(object.toString());
                        //txtview.setText("");
                        jsonParser(getStr(),1);
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
            }
        });


        retrofit =new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiService.BASEURL)
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        call = apiService.get3days(ApiService.APPKEY,1,lat,lon);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.isSuccessful()){
                    //날씨데이터를 받아옴
                    JsonObject object = response.body();
                    if (object != null) {
                        setStr(object.toString());
                        jsonParser(getStr(),2);

                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
            }
        });
        Log.d("Findweather_","/////////parsing endend");


    }

    public String getStr() {
        return str;
    }


    public void jsonParser(String jsonString,int type){
        try {
            JSONObject jobject = new JSONObject(jsonString).getJSONObject("weather");
            JSONArray jarray=null;
            JSONObject jobject_=null;
            if(type==1)
            {
                jarray = new JSONObject(jobject.toString()).getJSONArray("hourly");
                jwind= jarray.getJSONObject(0).getJSONObject("wind");
                jtemp = jarray.getJSONObject(0).getJSONObject("temperature");
                jsky = jarray.getJSONObject(0).getJSONObject("sky");

                jprecip = jarray.getJSONObject(0).getJSONObject("precipitation");
            }
            else if(type==2) {
                jarray = new JSONObject(jobject.toString()).getJSONArray("forecast3days");
                jobject_ = jarray.getJSONObject(0).getJSONObject("fcst3hour");
                jwind = jobject_.getJSONObject("wind");
                jtemp = jobject_.getJSONObject("temperature");
                jsky = jobject_.getJSONObject("sky");
                jprecip = jobject_.getJSONObject("precipitation");
            }


            if(type==1){
                wI[0].setWinspd(jwind.optString("wspd"));
                wI[0].setPreciptype(jprecip.optString("type"));
                wI[0].setPrecip(jprecip.optString("sinceOntime"));
                wI[0].setPrecip(wI[0].getPrecip()+" mm");
                wI[0].setTemp(jtemp.optString("tc"));
                wI[0].setSky(jsky.optString("name"));
                wI[0].setSkytype(jsky.optString("code"));
                wI[0].setTime("현재");
//                txtview.append("현재 "+"//"+wI[0].toString()+"\n\n");
                winfo.add(wI[0]);
                Log.d("FindWeather_","//////////"+wI[0].toString());
            }
            else if(type==2) {
                for (int i = 1; i < 5; i++) {
                    int hour = time[i];
                    wI[i].setWinspd(jwind.optString("wspd" + hour + "hour"));
                    wI[i].setPreciptype(jprecip.optString("type" + hour + "hour"));
                    wI[i].setPrecip(jprecip.optString("prob" + hour + "hour"));
                    wI[i].setPrecip(wI[i].getPrecip()+" %");
                    wI[i].setSky(jsky.optString("name" + hour + "hour"));
                    wI[i].setSkytype(jsky.optString("code"+hour+"hour"));
                    wI[i].setTemp(jtemp.optString("temp" + hour + "hour"));
                    wI[i].setTime(Integer.toString(hour)+"시간 후");
                    // Log.d("FindWeather_","//////"+Integer.toString(hour)+"//"+wI[i].toString());
                    //txtview.append(Integer.toString(hour) + "//" + wI[i].toString() + "\n\n");
                    winfo.add(wI[i]);
                }
                weatherlistadapter_ = new WeatherListView_(layoutinf);

                ArrayList<weatherInfo> weatherlist = winfo;
                pager.setAdapter(weatherlistadapter_);
                for(weatherInfo entity : weatherlist){
                    Log.d("Findweather in loop",entity.getPreciptype());

                    weatherlistadapter_.addItem(entity);
                    weatherlistadapter_.notifyDataSetChanged();

                }


                // Log.d("Mountpage","/////listsize : "+weatherlist.size());

            }

//        for(weatherInfo entity : weatherlist){
//            weatherlistadapter.addItem(entity);
//        }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public weatherInfo[] getwI() {
        return wI;
    }

    public ArrayList<weatherInfo> getList() {
        return winfo;
    }
}
