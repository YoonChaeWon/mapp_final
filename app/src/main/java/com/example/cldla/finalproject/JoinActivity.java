package com.example.cldla.finalproject;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class JoinActivity extends AppCompatActivity {
    EditText edit = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
    }

    public void insertNewMember(View v){

        edit = (EditText)findViewById(R.id.new_id);
        String new_id = edit.getText().toString();
        edit = (EditText)findViewById(R.id.new_pass);
        String new_pass= edit.getText().toString();
        edit = (EditText)findViewById(R.id.name);
        String name = edit.getText().toString();

        String sql = "INSERT INTO member (id, pass, name) VALUES "
                + "(" + "'" + new_id + "', '" + new_pass + "', '" + name + "');";
        Log.d("sql", sql);
        SQLiteDatabase db = openOrCreateDatabase(
                "proj.db",
                SQLiteDatabase.CREATE_IF_NECESSARY,
                null
        );
        db.execSQL(sql);
        finish();
    }
}
