package com.example.cldla.finalproject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ReviewListActivity extends AppCompatActivity {
    public static Context mContext = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);
        mContext = this;

        loadReviewList();
    }


    public void loadReviewList(){
        SQLiteDatabase db = openOrCreateDatabase(
                "proj.db",
                SQLiteDatabase.CREATE_IF_NECESSARY,
                null
        );

        // 리스트뷰 설정
        final ListView reviewListView = (ListView)findViewById(R.id.reviewList);
        final ReviewListAdapter radapter = new ReviewListAdapter();

        // 모든 review 가져오기
        String sql = "SELECT * FROM review;"; // (1): title   (2): contents  (3): author  (4): date
        Cursor c = db.rawQuery(sql, null);

        if(c!= null && c.getCount() != 0) {
            c.moveToFirst();
            radapter.addItem(c.getString(1), c.getString(3), c.getString(4));

            while (c.moveToNext()) {
                Log.e("DB:review", c.getInt(0) + "|" + c.getString(1) + " | " + c.getString(3) + " | " + c.getString(4));
                radapter.addItem(c.getString(1), c.getString(3), c.getString(4));
            }
            c.close();
        }

        reviewListView.setAdapter(radapter);

        reviewListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String date = radapter.getWriteDate(i);
                choiceReview(date);
            }
        });


        if(db != null) db.close();
    }


    public void choiceReview(String i){
        Intent intent = new Intent(this, SeeReviewActivity.class);
        intent.putExtra("date", i);
        startActivity(intent);
    }

    public void onClickWriteReview(View v){
        Intent i = getIntent();
        String author = i.getStringExtra("user_id");

        i = new Intent(this, WriteReviewActivity.class);
        i.putExtra("user_id", author);
        startActivity(i);

    }
}
