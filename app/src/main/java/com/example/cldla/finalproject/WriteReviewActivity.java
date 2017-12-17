package com.example.cldla.finalproject;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WriteReviewActivity extends AppCompatActivity {
    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath;
    Bitmap bitmap;
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

//        Drawable d = new BitmapDrawable(bitmap);
 //       byte[] imageByte = getByteArrayFromDrawable(d);
        String sql;

        /*if(imageByte != null){
            sql = "INSERT INTO review (title, contents, author, date, photo) "
                    + "VALUES (?, ?, ?, ?, ?);";
*//*            sql = "INSERT INTO review (title, contents, author, date, photo) VALUES ('"
                    + title + "', '" + contents + "', '" + author + "', '"
                    + writeDate + "' ," + "?);";*//*

            SQLiteStatement p = db.compileStatement(sql);
            p.bindString(1, title);
            p.bindString(2, contents);
            p.bindString(3, author);
            p.bindString(4, writeDate);
            p.bindBlob(5, imageByte);
        }
        else {
            sql = "INSERT INTO review (title, contents, author, date, photo) VALUES ('"
                    + title + "', '" + contents + "', '" + author + "', '" + writeDate + "' ," + "'null'" + ");";

        }*/
        sql = "INSERT INTO review (title, contents, author, date) VALUES ('"
                + title + "', '" + contents + "', '" + author + "', '" + writeDate + "');";
        Log.d("sql", sql);
        db.execSQL(sql);

        // 작성한 리뷰를 바로 리스트에 적용
        ((ReviewListActivity)ReviewListActivity.mContext).loadReviewList();

        db.close();
        finish();
    }

   /* public void selectGallery(View v){
       Intent intent = new Intent();
        intent.setType("image*//*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode==RESULT_OK){
            if(requestCode == SELECT_PICTURE){
                Uri selectedImageUri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                    ImageView image = (ImageView)findViewById(R.id.user_image);
                    image.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                selectedImagePath = getPath(selectedImageUri);
            }
        }
    }

    public byte[] getByteArrayFromDrawable(Drawable d){
        Bitmap b = ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] data = stream.toByteArray();

        return data;
    }


    *//**
     * 사진의 URI 경로를 받는 메소드
     *//*
    public String getPath(Uri uri) {
        // uri가 null일경우 null반환
        if( uri == null ) {
            return null;
        }
        // 미디어스토어에서 유저가 선택한 사진의 URI를 받아온다.
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // URI경로를 반환한다.
        return uri.getPath();
    }
*/
}
