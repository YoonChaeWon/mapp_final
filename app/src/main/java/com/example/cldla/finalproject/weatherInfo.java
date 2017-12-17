package com.example.cldla.finalproject;

/**
 * Created by Owner on 2017-12-16.
 */

public class weatherInfo {
    String sky, temp, preciptype, winspd,precip,skytype,time;
    int check;


    public weatherInfo() {
        check=0;
    }

    public void updatepreciptype(String preciptype){
        switch (preciptype){
            case "0":
                setPreciptype("비/눈 없음");
               // preciptype="비/눈 없음";
                break;
            case "1":
                setPreciptype("비");
                //preciptype="비";
                break;
            case "2":
                setPreciptype("비 또는 눈");
                //preciptype="비 또는 눈";
                break;
            case "3":
                setPreciptype("눈");
                //preciptype="눈";
                break;
        }
    }

    public String getSky() {
        return sky;
    }

    public void setSky(String sky) {
        this.sky = sky;
    }



    public String getTemp() {
        return temp+" 도";
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getPrecip() {
        return precip;
    }

    public void setPrecip(String precip) {
        this.precip = precip;
    }

    public String getWinspd() {
        return winspd+" m/s";
    }

    public void setWinspd(String winspd) {
        this.winspd = winspd;
    }

    public String getPreciptype() {
        return preciptype;
    }

    public void setPreciptype(String preciptype) {
        this.preciptype = preciptype;
        if(check==0) {
            check = 1;
            updatepreciptype(preciptype);
        }
    }

    @Override
    public String toString() {
        return "weatherInfo{" +
                "sky='" + getSky() + '\'' +
                ", temp='" + getTemp() + '\'' +
                ", preciptype='" + getPreciptype() + '\'' +
                ", winspd='" + getWinspd() + '\'' +
                ", precip='" + getPrecip() + '\'' +
                '}';
    }

    public String getSkytype() {
        return skytype;
    }

    public void setSkytype(String skytype) {
        this.skytype = skytype;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
