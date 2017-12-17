package com.example.cldla.finalproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class SeeReviewActivity extends AppCompatActivity {
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_review);

        provideReview();
    }

    public void provideReview(){
        Intent intent = getIntent();
        String i = intent.getStringExtra("date");

        SQLiteDatabase db = openOrCreateDatabase(
                "proj.db",
                SQLiteDatabase.CREATE_IF_NECESSARY,
                null
        );

        String sql = "SELECT * FROM review WHERE date=='" + i + "';";
        Cursor c = db.rawQuery(sql, null);

        if (c != null && c.getCount() != 0) {
            c.moveToFirst();

            TextView rTitle = (TextView) findViewById(R.id.review_title);
            TextView rContents = (TextView) findViewById(R.id.review_contents);
            TextView rAuthor = (TextView)findViewById(R.id.review_author);
            TextView rDate = (TextView) findViewById(R.id.review_date);
//            ImageView rImage = (ImageView)findViewById(R.id.review_image);

            rTitle.setText(c.getString(1));
            rContents.setText(c.getString(2));
            rAuthor.setText("by. " + c.getString(3));
            rDate.setText(c.getString(4));

           /* byte[] byteImage = c.getBlob(5);
            if(byteImage != null) {
                Bitmap bitmap = getReviewImage(byteImage);
                rImage.setImageBitmap(bitmap);
            }*/
        }
    }

    public Bitmap getReviewImage(byte[] b){
        Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
        return bitmap;
    }

}
