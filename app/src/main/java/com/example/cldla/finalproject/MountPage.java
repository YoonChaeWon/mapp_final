package com.example.cldla.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class MountPage extends AppCompatActivity {

    String locality, Mountname;
    String[] local={"서울특별시","세종특별자치시","부산광역시","대구광역시","광주광역시","인천광역시","대전광역시","울산광역시",
            "경기도","강원도","충청남도","충청북도","전라남도","전라북도","경상남도","경상북도","제주도"};
    String localArea;
    TextView txt, way, reccourse, mountinfo;
    FindWeather_ fw;
    FindWeather__ fw_;
    String res;
    FloatingActionButton fab1, fab2, fab3, fab4;
    Animation fab_open, fab_close, rotate_backward, rotate_forward;
    Boolean isFabOpen = false;
    ViewPager viewpager;
//    ArrayList<weatherInfo> wInfo;
//    WeatherListView weatherlistadapter;
    ListView weatherlistview;
    LayoutInflater layoutinf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mount_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Mountname = getIntent().getExtras().getString("mountname");
        locality = getIntent().getExtras().getString("locality");
        getSupportActionBar().setTitle(Mountname);
        int i;
        for(i=0;i<17;i++){
            if(local[i].equals(locality))
                break;
        }
        localArea = Integer.toString(i+1);

        way=(TextView)findViewById(R.id.way);
        reccourse=(TextView)findViewById(R.id.reccourse);
        mountinfo = (TextView)findViewById(R.id.mountinfo);
        //weatherlistview = (ListView)findViewById(R.id.weatherlist);
        fab1 = (FloatingActionButton)findViewById(R.id.fab1);
        fab2=(FloatingActionButton)findViewById(R.id.fab2);
        fab3=(FloatingActionButton)findViewById(R.id.fab3);
        fab4=(FloatingActionButton)findViewById(R.id.fab4);
        viewpager = (ViewPager)findViewById(R.id.pager);
        layoutinf = getLayoutInflater();

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);

        getWeather();
       // getMount();
    }

    public void fabClick(View v){
        int id = v.getId();
        switch (id){
            case R.id.fab1:
                animateFAB();
                break;
            case R.id.fab2:
                Log.d("Raj", "Fab 1");
                Intent fromMain = getIntent();
                String user_id = fromMain.getStringExtra("user_id");
                Intent i = new Intent(this, ReviewListActivity.class);
                i.putExtra("user_id", user_id);
                startActivity(i);
                break;
            case R.id.fab3:

                Log.d("Raj", "Fab 2");
                break;
            case R.id.fab4:
                //등산 기록 액티비티
                break;
        }
    }

    public void animateFAB(){

        if(isFabOpen){
            fab1.startAnimation(rotate_backward);
            fab2.startAnimation(fab_close);
            fab3.startAnimation(fab_close);
            fab4.startAnimation(fab_close);
            fab2.setClickable(false);
            fab3.setClickable(false);
            fab4.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");

        } else {

            fab1.startAnimation(rotate_forward);
            fab2.startAnimation(fab_open);
            fab3.startAnimation(fab_open);
            fab4.startAnimation(fab_open);
            fab2.setClickable(true);
            fab3.setClickable(true);
            fab4.setClickable(true);
            isFabOpen = true;
            Log.d("Raj","open");

        }
    }

    public String tokenn(String str){
        String res="";
        StringTokenizer st = new StringTokenizer(str,"<BR>|&lt;br|/&gt;|&amp;nbsp;");
        while(st.hasMoreTokens()){
            res+=st.nextToken()+"\n";
        }
        return res;
    }
    public void getMount(){
        Log.d("MountPage","///getMount() mount name : "+Mountname);
        FindMount fm = new FindMount("mntnNm",Mountname,locality,MountPage.this);
        ArrayList<MountInfo> mountlist = fm.getList();
        for(MountInfo entity : mountlist){
            way.setText(tokenn(entity.getMountway()));
            reccourse.setText(tokenn(entity.getMountreco()));
            mountinfo.setText(tokenn(entity.getMountinfo()));
        }
    }
    public void getWeather(){

        Thread weathThread = new Thread(){
            @Override
            public void run(){
                //fw=new FindWeather_(37.5714000000,126.9658000000,weatherlistview);
                fw_=new FindWeather__(37.5714000000,126.9658000000,viewpager,layoutinf);
            }
        };

        weathThread.start();
        try {
            weathThread.join();
//            weatherlistadapter = new WeatherListView();
//            weatherlistview.setAdapter(weatherlistadapter);
//
//            ArrayList<weatherInfo> weatherlist = fw.getList();
//            Log.d("Mountpage","/////listsize : "+weatherlist.size());
//            for(weatherInfo entity : weatherlist){
//                weatherlistadapter.addItem(entity);
//            }
          //  res = fw.getStr();
          //  Log.d("MountPage", "///////////////getstr");
            getMount();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }
}
