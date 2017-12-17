package com.example.cldla.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Owner on 2017-12-17.
 */

public class WeatherListView extends BaseAdapter {

    ArrayList<weatherInfo> weatherlist = new ArrayList<weatherInfo>();
    ImageView weatherimg;

    public WeatherListView() {
    }

    public void addItem(weatherInfo mi){weatherlist.add(mi);
    }

    @Override
    public int getCount() {
        return weatherlist.size();
    }

    @Override
    public Object getItem(int i) {
        return weatherlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void getImg(String skytype){
        skytype=skytype.substring(5,6);
        int stype = Integer.parseInt(skytype);
        switch(stype){
            case 1://sun
                weatherimg.setImageResource(R.drawable.sun);
                break;
            case 2: case 3:
                weatherimg.setImageResource(R.drawable.cloud);
                break;
            case 4:
                weatherimg.setImageResource(R.drawable.raining);
                break;
            case 12:case 14:
                weatherimg.setImageResource(R.drawable.storm);
                break;
            case 11:
                weatherimg.setImageResource(R.drawable.thunder);
                break;
            case 7:
                weatherimg.setImageResource(R.drawable.cloudy);
                break;
            case 8:
                weatherimg.setImageResource(R.drawable.rain);
                break;
            case 9:
                weatherimg.setImageResource(R.drawable.snow);
                break;
            case 5:
                weatherimg.setImageResource(R.drawable.snowcloud);
                break;
            case 13:
                weatherimg.setImageResource(R.drawable.thundersnow);
                break;
            case 6:case 10:
                weatherimg.setImageResource(R.drawable.rainorsnow);
                break;

        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.weatherlist, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        weatherimg = (ImageView)convertView.findViewById(R.id.weatherimg);
        TextView time = (TextView) convertView.findViewById(R.id.time);
        TextView sky = (TextView)convertView.findViewById(R.id.sky);
        TextView temp = (TextView)convertView.findViewById(R.id.temp);
        TextView preciptype = (TextView)convertView.findViewById(R.id.preciptype);
        TextView precip = (TextView)convertView.findViewById(R.id.precip);
        TextView windspd = (TextView)convertView.findViewById(R.id.wdspd);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        weatherInfo weatherinfo = weatherlist.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        getImg(weatherinfo.getSkytype());
        time.setText(weatherinfo.getTime());
        sky.setText(weatherinfo.getSky());
        temp.setText("기온 : "+weatherinfo.getTemp());
        preciptype.setText(weatherinfo.getPreciptype());
        precip.setText("강수 : "+weatherinfo.getPrecip());
        windspd.setText("풍속 : "+weatherinfo.getWinspd());

        return convertView;
    }


}
