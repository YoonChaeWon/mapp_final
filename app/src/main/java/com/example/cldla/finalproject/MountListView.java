package com.example.cldla.finalproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Owner on 2017-12-14.
 */

public class MountListView extends BaseAdapter {

    ArrayList<MountInfo> mountlist = new ArrayList<MountInfo>();

    public MountListView() {
    }


    @Override
    public int getCount() {
        return mountlist.size();
    }

    @Override
    public Object getItem(int i) {
        return mountlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.mountlist, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView mountname = (TextView)convertView.findViewById(R.id.mountname);
        TextView mountaddr = (TextView)convertView.findViewById(R.id.addr);
        TextView mountway = (TextView)convertView.findViewById(R.id.way);
        ImageView mountimg = (ImageView)convertView.findViewById(R.id.mountimg);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        MountInfo mountitem = mountlist.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        mountname.setText(mountitem.getMountName());
        mountaddr.setText(mountitem.getMountaddr());
        mountway.setText(mountitem.getMountbrief());
        //이미지가 다 안들어온다,,,,,ㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠ
        mountimg.setImageBitmap(mountitem.getMountbitmap());
        Log.d("Mountlistview","bitmap size : "+Integer.toString(mountitem.getMountbitmap().getWidth()));

        return convertView;


    }

    public void addItem(MountInfo mi){
        mountlist.add(mi);
    }

}
