package com.example.cldla.finalproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loadDB();
    }

    public void onResume(){
        super.onResume();
        loadDB();
    }

    // 첫 Activity에서 테이블 생성해놓기
    public void loadDB(){
        SQLiteDatabase db = openOrCreateDatabase(
                "proj.db",
                SQLiteDatabase.CREATE_IF_NECESSARY,
                null
        );

        db.execSQL("CREATE TABLE IF NOT EXISTS member " +
        "(id TEXT PRIMARY KEY, pass TEXT, name TEXT); ");

        db.execSQL("CREATE TABLE IF NOT EXISTS review " +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT, contents VARCHAR(255), author TEXT, date TEXT); ");

        if(db != null){
            db.close();
        }

    }

    public void onClickLoginButton(View v){
        EditText e_id = null, e_pass = null;

        /* 사용자가 입력한 input */
        e_id = (EditText)findViewById(R.id.id);
        String id = e_id.getText().toString();
        e_pass = (EditText)findViewById(R.id.password);
        String password = e_pass.getText().toString();

        String sql = "select * from member where id == '" + id + "' and pass == '" + password + "';";
        SQLiteDatabase db = openOrCreateDatabase(
                "proj.db",
                SQLiteDatabase.CREATE_IF_NECESSARY,
                null
        );
        Cursor c = db.rawQuery(sql, null);
        int count = c.getCount();

        if (count == 0) {
            Toast.makeText(this, "등록되지 않은 회원입니다.", Toast.LENGTH_SHORT).show();
        } else if (count == 1) {
            Intent i = new Intent(this, WriteReviewActivity.class);
            startActivity(i);
        }
        db.close();
        c.close();
    }

    public void onClickJoinButton(View v){
        Intent i = new Intent(this, JoinActivity.class);
        startActivity(i);
    }
}
