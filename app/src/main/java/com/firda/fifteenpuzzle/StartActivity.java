package com.firda.fifteenpuzzle;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class StartActivity extends AppCompatActivity implements CardsFragment.GetterImage {

    CardsFragment cards;
    ImageView goalImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        goalImage = findViewById(R.id.goalImage);
    }

    public void start(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void about(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);
        builder.setTitle("About app")
                .setMessage("On each card there is a piece of of image that you should gather.\n" +
                        "Select one of four card that are around of empty space to move card")
                .setCancelable(false)
                .setNegativeButton("Got it!",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void startLarge(View view) {
        if (cards == null) {
            cards = new CardsFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.fragment_container, cards);
            ft.addToBackStack(null);
            ft.commit();
            if (goalImage != null) goalImage.setImageResource(R.drawable.cat);
        }
    }

    public void getImage(Bitmap bitmap) {
        if (goalImage != null) goalImage.setImageBitmap(bitmap);
    }
}
