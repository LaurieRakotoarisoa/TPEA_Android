package com.example.framedpictures;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Integer> f = new ArrayList<Integer>();
        f.add(R.mipmap.heartframe);
        f.add(R.mipmap.ovalframe);
        f.add(R.mipmap.photoframevintage);
        f.add(R.mipmap.roundedframe);

        final GridView gridView = (GridView)findViewById(R.id.gridView);

        FrameAdapter arrayAdapter =
                new FrameAdapter(MainActivity.this,R.layout.frameitem,f);


        gridView.setAdapter(arrayAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = gridView.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this,CreateFrameActivity.class);
                intent.putExtra("frame",((Integer)o).intValue());
                startActivity(intent);
            }
        });

    }

    protected void saveFrame(View view){

    }
}
