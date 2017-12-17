package com.example.cldla.finalproject;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

        SQLiteDatabase db = openOrCreateDatabase(
                "proj.db",
                SQLiteDatabase.CREATE_IF_NECESSARY,
                null
        );

        String sql = "select * from member where id == '" + new_id + "';";
            Cursor c = db.rawQuery(sql, null);
            c.moveToFirst();

            //이미 존재하는 id인 경우
            if(c.getCount() > 0){
                Toast.makeText(this, "사용할 수 없는 ID", Toast.LENGTH_SHORT).show();
            }
            else {
                sql = "INSERT INTO member (id, pass, name) VALUES "
                        + "(" + "'" + new_id + "', '" + new_pass + "', '" + name + "');";
                //Log.d("sql", sql);

                db.execSQL(sql);
                Toast.makeText(this, "회원가입 완료", Toast.LENGTH_SHORT).show();
                finish();
                c.close();
                db.close();
        }
       // c.close();

        //db.close();
        //finish();
    }
}
