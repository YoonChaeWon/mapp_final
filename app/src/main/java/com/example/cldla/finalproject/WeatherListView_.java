package com.example.cldla.finalproject;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Owner on 2017-12-17.
 */

public class WeatherListView_ extends PagerAdapter {

    ArrayList<weatherInfo> weatherlist = new ArrayList<weatherInfo>();
    ImageView weatherimg;
    LayoutInflater inflater;

    public WeatherListView_(   LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public void addItem(weatherInfo mi){weatherlist.add(mi);

    }

    @Override
    public int getCount() {
        return weatherlist.size();

    }


    public Object getItem(int i) {
        return weatherlist.get(i);
    }


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

    public Object instantiateItem(ViewGroup container, int position) {

        // TODO Auto-generated method stub

        View view=null;
        view= inflater.inflate(R.layout.weatherlist, null);

        weatherimg = (ImageView)view.findViewById(R.id.weatherimg);
        TextView time = (TextView) view.findViewById(R.id.time);
        TextView sky = (TextView)view.findViewById(R.id.sky);
        TextView temp = (TextView)view.findViewById(R.id.temp);
        TextView preciptype = (TextView)view.findViewById(R.id.preciptype);
        TextView precip = (TextView)view.findViewById(R.id.precip);
        TextView windspd = (TextView)view.findViewById(R.id.wdspd);

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
        Log.d("WeatherListView_","/////"+preciptype.getText().toString());

        //ViewPager에 만들어 낸 View 추가

        container.addView(view);



        //Image가 세팅된 View를 리턴

        return view;

    }

    @Override

    public void destroyItem(ViewGroup container, int position, Object object) {

        // TODO Auto-generated method stub

        //ViewPager에서 보이지 않는 View는 제거

        //세번째 파라미터가 View 객체 이지만 데이터 타입이 Object여서 형변환 실시

        container.removeView((View)object);



    }

    @Override

    public boolean isViewFromObject(View v, Object obj) {

        // TODO Auto-generated method stub

        return v==obj;

    }




}
