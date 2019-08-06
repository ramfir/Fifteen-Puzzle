package com.firda.fifteenpuzzle;

import android.graphics.Bitmap;

public class Image {
    private int width;
    private int height;
    private Bitmap mBitmap;
    private Bitmap[] mPieces;

    public Image(Bitmap bitmap) {
        mBitmap = bitmap;
        width = mBitmap.getWidth();
        height = mBitmap.getHeight();
        mPieces = new Bitmap[16];
        mPieces[0] = Bitmap.createBitmap(mBitmap, 0, 0, width/4, height/4);
        mPieces[1] = Bitmap.createBitmap(mBitmap, width/4, 0, width/4, height/4);
        mPieces[2] = Bitmap.createBitmap(mBitmap, 2*width/4, 0, width/4, height/4);
        mPieces[3] = Bitmap.createBitmap(mBitmap, 3*width/4, 0, width/4, height/4);
        mPieces[4] = Bitmap.createBitmap(mBitmap, 0, height/4, width/4, height/4);
        mPieces[5] = Bitmap.createBitmap(mBitmap, width/4, height/4, width/4, height/4);
        mPieces[6] = Bitmap.createBitmap(mBitmap, 2*width/4, height/4, width/4, height/4);
        mPieces[7] = Bitmap.createBitmap(mBitmap, 3*width/4, height/4, width/4, height/4);
        mPieces[8] = Bitmap.createBitmap(mBitmap, 0, 2*height/4, width/4, height/4);
        mPieces[9] = Bitmap.createBitmap(mBitmap, width/4, 2*height/4, width/4, height/4);
        mPieces[10] = Bitmap.createBitmap(mBitmap, 2*width/4, 2*height/4, width/4, height/4);
        mPieces[11] = Bitmap.createBitmap(mBitmap, 3*width/4, 2*height/4, width/4, height/4);
        mPieces[12] = Bitmap.createBitmap(mBitmap, 0, 3*height/4, width/4, height/4);
        mPieces[13] = Bitmap.createBitmap(mBitmap, width/4, 3*height/4, width/4, height/4);
        mPieces[14] = Bitmap.createBitmap(mBitmap, 2*width/4, 3*height/4, width/4, height/4);
        mPieces[15] = Bitmap.createBitmap(mBitmap, 3*width/4, 3*height/4, width/4, height/4);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Bitmap getImage() {
        return mBitmap;
    }
    public Bitmap[] getPieces() {
        return mPieces;
    }
}

