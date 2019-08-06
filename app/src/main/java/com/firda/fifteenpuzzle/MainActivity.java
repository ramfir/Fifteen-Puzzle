package com.firda.fifteenpuzzle;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements CardsFragment.GetterImage {
    ImageView goalImage;

    public static final String IMAGE_KEY = "goalImage";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        goalImage = findViewById(R.id.goalImage);
    }

    public void getImage(Bitmap bitmap) {
        if (goalImage != null) goalImage.setImageBitmap(bitmap);
    }
}
