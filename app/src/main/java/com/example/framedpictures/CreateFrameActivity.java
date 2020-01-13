package com.example.framedpictures;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import static com.example.framedpictures.MainActivity.REQUEST_IMAGE_CAPTURE;

public class CreateFrameActivity extends FragmentActivity {
    ImageView mImageView;
    FragmentManager fragManag;
    Fragment fr1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frames);
        fragManag = getSupportFragmentManager();
        fr1 = fragManag.findFragmentById(R.id.fragment1);
    }

    public void dispatchTakePictureIntent() {

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent,REQUEST_IMAGE_CAPTURE);
    }
}
