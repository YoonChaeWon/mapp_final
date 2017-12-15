package com.example.cldla.finalproject;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

public class ReviewListActivity extends AppCompatActivity {
    public static ArrayList<String> itemTitleList = new ArrayList<String>();
    public static ArrayList<String> itemAuthorList = new ArrayList<String>();
    public static ArrayList<String> itemWrieDateList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);
        loadReviewList();
    }

    public void loadReviewList(){
        SQLiteDatabase db = openOrCreateDatabase(
                "proj.db",
                SQLiteDatabase.CREATE_IF_NECESSARY,
                null
        );

        // 모든 review 가져오기
        String sql = "SELECT * FROM review;";
        Cursor c = db.rawQuery(sql, null);
        c.moveToFirst();

        while (c.moveToNext()){
            Log.e("DB:review", c.getString(1) + " | " + c.getString(2) + " | " + c.getString(3));
        }


        // 리스트뷰 설정
        ListView reviewListView = (ListView)findViewById(R.id.reviewList);
        ReviewListAdapter radapter = new ReviewListAdapter();
        radapter.addItem("title", "author", "date");

        reviewListView.setAdapter(radapter);


        if(db != null) db.close();
    }
}
