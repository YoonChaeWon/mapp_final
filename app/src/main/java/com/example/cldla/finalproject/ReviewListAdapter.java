package com.example.cldla.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hansol on 2017-12-13.
 */

public class ReviewListAdapter extends BaseAdapter {
    private ArrayList<ReviewListItem> reviewList = new ArrayList<ReviewListItem>();

    public ReviewListAdapter(){}

    @Override
    public int getCount() {
        return reviewList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.review_item, parent, false);
        }
        //멤버 목록//
        TextView txtTitle = (TextView) convertView.findViewById(R.id.item_title);
        TextView txtAuthor = (TextView) convertView.findViewById(R.id.item_author);
        TextView txtWDate = (TextView) convertView.findViewById(R.id.item_date);

        ReviewListItem listViewItem = reviewList.get(position);

        txtTitle.setText(listViewItem.getTitle());
        txtAuthor.setText(listViewItem.getAuthor());
        txtWDate.setText(listViewItem.getWriteDate());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return reviewList.get(position);
    }

    //아이템 추가
    public void addItem( String title, String author, String writeDate) {
        ReviewListItem item = new ReviewListItem();

        item.setTitle(title);
        item.setAuthor(author);
        item.setWriteDate(writeDate);

        reviewList.add(item);
    }
}
