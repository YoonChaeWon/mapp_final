package com.example.cldla.finalproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by Owner on 2017-12-14.
 */

public class MountInfo {
    String MountName;
    String MountImg;
    String Mountaddr;
    String Mountway;
    String Mountinfo;
    String Mountreco;
    String Mountbrief;
    Bitmap Mountbitmap;
    int bitmapcheck;
    Context mcontext;

    public String getMountbrief() {
        return Mountbrief;
    }

    public void setMountbrief(String mountbrief) {
        Mountbrief = mountbrief;
    }

    public String getMountreco() {
        return Mountreco;
    }

    public void setMountreco(String mountreco) {
        Mountreco = mountreco;
    }

    public String getMountway() {
        return Mountway;
    }

    public void setMountway(String mountway) {
        Mountway = mountway;
    }


    public String getMountaddr() {
        return Mountaddr;
    }

    public void setMountaddr(String mountaddr) {
        Mountaddr = mountaddr;
    }


    public MountInfo(Context mcontext) {
        this.mcontext = mcontext;
    }

    public String getMountName() {
        return MountName;
    }

    public void setMountName(String mountName) {
        MountName = mountName;
    }


    public void setMountImg(String mountImg) {
        MountImg = mountImg;

      //  URL url=null;
      //  HttpURLConnection conn=null;
        try {

            URL url = new URL(MountImg);
           // HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            //conn.setDoInput(true);
            //conn.connect();
            InputStream is = url.openStream();
            //InputStream is = conn.getInputStream();
            Mountbitmap = BitmapFactory.decodeStream(is);
            if(Mountbitmap==null) {
                BitmapDrawable drawable = (BitmapDrawable) mcontext.getResources().getDrawable(R.drawable.mountain);
                Bitmap bitmap = drawable.getBitmap();
                Mountbitmap=bitmap;
               setBitmapcheck(1);
                Log.d("Mountinfo", "//////Mountbitmap is null");
            }
            else
            {
                setBitmapcheck(0);
                Log.d("Mountinfo","///////////////mountbitmap"+Mountbitmap.toString());
            }
            int resizeWidth = 300;

            double aspectRatio = (double) Mountbitmap.getHeight() / (double) Mountbitmap.getWidth();
            int targetHeight = (int) (resizeWidth * aspectRatio);
            Mountbitmap = Bitmap.createScaledBitmap(Mountbitmap, resizeWidth, targetHeight, false);

        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    public Bitmap getMountbitmap() {
        return Mountbitmap;
    }

    public String getMountImg() {
        return MountImg;
    }

    public String getMountinfo() {
        return Mountinfo;
    }

    public void setMountinfo(String mountinfo) {
        Mountinfo = mountinfo;
    }

    @Override
    public String toString() {
        return "MountInfo{" +
                "MountName='" + MountName + '\'' +
                ", Mountway='" + Mountway + '\'' +
                ", Mountreco='" + Mountreco + '\'' +
                ", Mountinfo='" + Mountinfo + '\'' +
                '}';
    }

    public int getBitmapcheck() {
        return bitmapcheck;
    }

    public void setBitmapcheck(int bitmapcheck) {
        this.bitmapcheck = bitmapcheck;
    }
}
