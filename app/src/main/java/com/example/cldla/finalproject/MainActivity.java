package com.example.cldla.finalproject;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    double latitude, longitude,altitude;
    Geocoder geocoder;
    TextView txt,txt2,txt3;
    FindMount fm;
    MountListView mountlistadapter;
    ListView mountlistview;
    ArrayList<MountInfo> mountlist;
    String locality, throughfare, subthrough;



    static final Integer APP_PERMISSION=1;
    private void askForPermission(String permission, Integer requestCode){
        if(ContextCompat.checkSelfPermission(MainActivity.this, permission)!= PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permission)) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
            }else{
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
            }
        }else{
            Toast.makeText(this,""+permission+"is already granted",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(ActivityCompat.checkSelfPermission(this,permissions[0])==PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this,"Permission granted",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"Permission denied",Toast.LENGTH_SHORT).show();
        }
    }

    public void showNewLocation(Location location){

        if(location!=null) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();

            if (location.hasAltitude())
                altitude = location.getAltitude();
            else
                altitude = 0;

            List<Address> list = null;
            try {
                double d1 = longitude;
                double d2 = latitude;

                list = geocoder.getFromLocation(
                        d2, // 위도
                        d1, // 경도
                        20); // 얻어올 값의 개수
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("test", "입출력 오류 - 서버에서 주소변환시 에러발생");
            }
            if (list != null) {
                if (list.size()==0) {
                    txt.setText("해당되는 주소 정보는 없습니다");
                } else {
                    locality = list.get(0).getLocality().toString();//대구
                    throughfare = list.get(0).getThoroughfare().toString();//월성동
                    subthrough = list.get(0).getSubThoroughfare().toString();
                    txt.setText("현재 위치 : "+locality+"_"+throughfare+"_"+subthrough);
                }
            }

            Thread mountThread = new Thread(){
                @Override
                public void run(){
                    fm = new FindMount("mntnAdd", "경상북도",MainActivity.this);
                }
            };

            mountThread.start();
            try{
                mountThread.join();
                mountlistadapter = new MountListView();
                mountlistview.setAdapter(mountlistadapter);
                mountlistview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    public void onItemClick(AdapterView<?> arg0, View v,int position, long arg3)
                    {
                        MountInfo mifo = (MountInfo)mountlistview.getItemAtPosition(position);
                        String name = mifo.getMountName();
                       // Toast.makeText(getApplicationContext(), "Selected : "+name,   Toast.LENGTH_LONG).show();
                        //LoginActivity한테 id 받아오기
                        Intent fromLogin = getIntent();
                        String user_id = fromLogin.getStringExtra("user_id");

                        Intent i = new Intent(MainActivity.this, MountPage.class);
                        i.putExtra("mountname",name);
                        ///////
                        locality="경상북도";
                        i.putExtra("locality",locality);
                        i.putExtra("user_id", user_id);

                        startActivity(i);
                    }
                });

                ArrayList<MountInfo> mountlist = fm.getList();

                Log.d("MainActivity","////entity length : "+mountlist.size());
                for(MountInfo entity : mountlist){
                   // txt2.setText(entity.getMountImg());
                    mountlistadapter.addItem(entity);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("mainmianmain", "mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //gps
        askForPermission(Manifest.permission.ACCESS_FINE_LOCATION,APP_PERMISSION);

        LocationManager locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
               // Toast.makeText(MainActivity.this,"location changed",Toast.LENGTH_LONG).show();
                showNewLocation(location);
            }


            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED
                &&ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
            return;

        if(locationManager.getAllProviders().contains(LocationManager.NETWORK_PROVIDER))
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,500,1,locationListener);
        if(locationManager.getAllProviders().contains(LocationManager.GPS_PROVIDER))
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,500,1,locationListener);

        geocoder = new Geocoder(this);
        txt = (TextView) findViewById(R.id.textView2);
        txt2=(TextView)findViewById(R.id.textView3);
        txt3=(TextView)findViewById(R.id.textView4);
        txt2.setText("");
        txt3.setText("");
        mountlistview = (ListView)findViewById(R.id.mountlistview);


        //networkonmainthreadexception
        if(Build.VERSION.SDK_INT>9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
