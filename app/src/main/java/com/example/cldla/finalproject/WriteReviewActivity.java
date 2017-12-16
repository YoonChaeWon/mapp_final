package com.example.cldla.finalproject;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WriteReviewActivity extends AppCompatActivity {
    EditText txt = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);
    }

    public void createNewReview(View v){
        // 사용자가 입력한 리뷰의 제목과 내용 설정
        txt = (EditText)findViewById(R.id.title);
        String title = txt.getText().toString();
        txt = (EditText)findViewById(R.id.contents);
        String contents = txt.getText().toString();

        // 작성 시간 설정
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String writeDate = sdf.format(date);

        // 작성자 설정(임의로 설정해놓은 것)
        Intent i = getIntent(); // LoginActivity의 intent 받아오기
        String author = i.getStringExtra("user_id");

        SQLiteDatabase db = openOrCreateDatabase(
                "proj.db",
                SQLiteDatabase.CREATE_IF_NECESSARY,
                null
        );
        String sql = "INSERT INTO review (title, contents, author, date) VALUES ('"
                + title + "', '" + contents + "', '" + author + "', '" + writeDate +"');";
        Log.d("sql", sql);


        db.execSQL(sql);

        // 작성한 리뷰를 바로 리스트에 적용
        ((ReviewListActivity)ReviewListActivity.mContext).loadReviewList();

        db.close();
        finish();
    }
}
