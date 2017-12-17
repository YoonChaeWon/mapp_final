package com.example.cldla.finalproject;

import android.content.Context;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Owner on 2017-12-14.
 */

public class FindMount {
    int cnt;
    String url, tagname,local;
 //   ArrayList<String> list,list2;
    ArrayList<MountInfo> list,list2;
    String Mount_URL = "http://openapi.forest.go.kr/openapi/service/trailInfoService/getforeststoryservice";
    String KEY="KPa6TTiiHkrtyR5uNQPIJe2MeS%2BuYXfKQaxU6hDepbbhsaNnvpogE4tFWQfB23ZIZfTzXFGzMi8Pav4ZYc2vig%3D%3D";
    String value, data;
    Context mcontext;

    public FindMount() {
        try {
            cnt=0;
            apiParserSearch();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public FindMount(String value, String data,Context mcontext) {
        this.value = value;
        this.data = data;
        this.mcontext = mcontext;
        try {
            cnt=0;
            apiParserSearch();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public FindMount(String value, String data,String local, Context mcontext) {
        this.value = value;
        this.data = data;
        this.local=local;
        this.mcontext = mcontext;
        try {
            cnt=0;
            apiParserSearch();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void apiParserSearch() throws Exception {
        URL url = new URL(getURLParam(value, data));
        Log.d("FindMount","////////////////////url : "+url);

        list = new ArrayList<MountInfo>();
        String tagName=null;

        InputStream in = url.openStream();

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(in, "UTF-8");

        int eventType = parser.getEventType();
        boolean isItemTag = false;
        String Mountname = null;
        String Mountaddr=null;
        String Mountway=null;
        String Mountin=null;
        String readtxt=null;
        MountInfo mi = new MountInfo(mcontext);

        while(eventType!=XmlPullParser.END_DOCUMENT){

            if(eventType==XmlPullParser.START_TAG){

                tagName  = parser.getName();

            }else if(eventType==XmlPullParser.TEXT){

                switch (tagName){
                    case "mntnnm":
                        Mountname = parser.getText();
                        mi.setMountName(Mountname);
                        break;
                    case "mntnattchimageseq":
                        mi.setMountImg(parser.getText());
                        break;
                    case "mntninfopoflc":
                        mi.setMountaddr(parser.getText());
                        break;
                    case "pbtrninfodscrt":
                        mi.setMountway(parser.getText());
                        break;
                    case "crcmrsghtnginfoetcdscrt":
                        mi.setMountreco(parser.getText());
                        break;
                    case "mntninfodtlinfocont":
                        mi.setMountinfo(parser.getText());
                        Log.d("FindMount","////mntninfodtlinfocont"+mi.getMountinfo());
                        break;
                    case "mntnsbttlinfo":
                        mi.setMountbrief(parser.getText());
                        break;

                }
            }else if(eventType==XmlPullParser.END_TAG){
                tagName = parser.getName();
                if(tagName.equals("item")){
                    if(value.equals("mntnNm")&&data.equals(mi.getMountName())){
                        list.add(mi);
                        Log.d("FindMount","////이름으로 찾기"+mi.toString());
                    }else if(value.equals("mntnAdd"))
                        list.add(mi);
                    mi=new MountInfo(mcontext);
                }
            }
            eventType = parser.next();
        }
    }

    private String getURLParam(String value, String data){
        try {
          url=Mount_URL+"?"+URLEncoder.encode("serviceKey","UTF-8")+"="+KEY+"&"+URLEncoder.encode(value,"UTF-8")
          +"="+URLEncoder.encode(data,"UTF-8");
            if(value.equals("mntnNm")){
                url+="&mntnAdd="+URLEncoder.encode(local,"UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        return url;
    }

    public ArrayList<MountInfo> getList() {
        return list;
    }

    public String getUrl() {
        return url;
    }

    public int getCnt() {
        return cnt;
    }

    public ArrayList<MountInfo> getList2() {
        return list2;
    }

    public String getTagname() {
        return tagname;
    }
}
