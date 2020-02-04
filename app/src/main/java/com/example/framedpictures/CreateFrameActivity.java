package com.example.framedpictures;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;


public class CreateFrameActivity extends FragmentActivity  {
    ImageView imageCaptured;
    FragmentManager fragmentManager;
    SeekBar seekBarBlue;
    SeekBar seekBarRed;
    SeekBar seekBarGreen;
    Fragment f;
    String strEditText;

    // button
    Button btn;
    FrameLayout fLayout;
    Spinner spinner;


    int red;
    int green;
    int blue;



    public static final int PICK_IMAGE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_activitylayout);

        Bundle bundle = getIntent().getExtras();
        int resource = bundle.getInt("frame");

        blue = 100;
        green = 100;
        red = 100;

        fragmentManager = getSupportFragmentManager();
        f = fragmentManager.findFragmentById(R.id.fragment);
        ImageView frameView = f.getView().findViewById(R.id.img_frame);
        imageCaptured = f.getView().findViewById(R.id.img_capture);
        frameView.setImageResource(resource);

        //button add text
        btn = findViewById(R.id.btnAddText);
        fLayout = f.getView().findViewById (R.id.frame);
         strEditText = "";

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> sizeAdapter = ArrayAdapter.createFromResource(this, R.array.numbers,
        android.R.layout.simple_spinner_item);
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(sizeAdapter);

        seekBarBlue = findViewById(R.id.seekblue);
        seekBarBlue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                blue = progress;
                changeColor();



            }
        });

        seekBarGreen = findViewById(R.id.seekgreen);
        seekBarGreen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                green = progress;
                changeColor();



            }
        });

        seekBarRed = findViewById(R.id.seekred);
        seekBarRed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                red = progress;
                changeColor();



            }
        });

    }

    private void changeColor(){
        if(imageCaptured.getDrawable() != null){
            imageCaptured.getDrawable().setColorFilter(Color.argb(100,red,green,blue),PorterDuff.Mode.ADD);
        }
    }

    public void saveImage(View view){
        ((ImageFragment) f).saveFrame(view);
    }


    /**
     * ajouter une texte dans la photo
     * @param view
     */
    public void addText(View view){


        Intent intent = new Intent(getApplicationContext(),InputTextActivity.class);
      startActivity(intent);
      

                ((ImageFragment) f).addText(view);

    }
  

    public void setStrEditText(){
        ((ImageFragment) f).setText(strEditText);

    }

    public void setItalic(View view){
        ((ImageFragment) f).setItalic(view);
    }

    public void  setBold(View view){
        ((ImageFragment) f).setBold(view);
    }


    // String t = parent.getItemAtPosition(position).toString();

}




