package com.example.framedpictures;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.app.Activity.RESULT_OK;


@SuppressLint("NewApi")
public class ImageFragment extends Fragment implements View.OnClickListener,
        View.OnTouchListener, GestureDetector.OnGestureListener {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String TAG = "DEBUG";

    ImageView imgView; //ImageView for the image to capture
    String currentPhotoPath;
    Uri photoURI;
    FrameLayout frameLayout;
    EditText newtext = null;
    private int _xDelta;
    private int _yDelta;

    private GestureDetector gestureDetector ;

        static final int REQUEST_TAKE_PHOTO = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        newtext = null;
        gestureDetector = new GestureDetector(getContext(),this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frame_layout, container, false);
        imgView = view.findViewById(R.id.img_capture);
        frameLayout = view.findViewById(R.id.frame);
        imgView.setOnClickListener(this);


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap bitmap = null;

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), photoURI);
                imgView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(getContext(),
                        "com.example.framedpictures.fileprovider",
                        photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        }


        //startActivityForResult(intent,REQUEST_IMAGE_CAPTURE);
    }

    private File createImageFile() throws IOException {

        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;


    }

    public void saveFrame(View view) {
        if (imgView.getDrawable() != null) {
            frameLayout.setDrawingCacheEnabled(true);
            Bitmap bitmap = frameLayout.getDrawingCache();
            File file,f;
            try {


                if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
                    file = new File(getContext().getExternalFilesDir(null), "Pictures");
                    if (!file.exists()) {
                        file.mkdirs();

                    }
                    Date todayDate = Calendar.getInstance().getTime();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-ddhhmmss");
                    String todayString = formatter.format(todayDate);
                    String filepath = file.getAbsolutePath() + file.separator + "framedpicture"+todayString + ".png";
                    f = new File(filepath);
                    FileOutputStream ostream = new FileOutputStream(f);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 10, ostream);
                    ostream.close();
                    Toast.makeText(getContext(), "image saved at "+filepath, Toast.LENGTH_LONG).show();
                }

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        else{
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setTitle("No picture captured");
            alertDialog.setMessage("You have to take a picture before saving the frame");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }
    public void addText(View view){
        newtext = new EditText(getContext());


        newtext.setHint("add text ici...");
        newtext.setImeOptions(EditorInfo.IME_ACTION_DONE);
        newtext.setFocusableInTouchMode(true);

        //set layout

        final FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);

        newtext.setLayoutParams(layoutParams);


        // seulement une line
        newtext.setInputType(InputType.TYPE_CLASS_TEXT);



        newtext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if(actionId == EditorInfo.IME_ACTION_DONE){
                    // clear focus
                    newtext.clearFocus();
                }

                return false;

            }
        });
        newtext.setOnTouchListener(this);

        newtext.setTypeface(Typeface.DEFAULT_BOLD);
        newtext.setTextSize(20);


        frameLayout.addView(newtext);



    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent){

        gestureDetector.onTouchEvent(motionEvent);

        int X = (int) motionEvent.getRawX();
        int Y = (int) motionEvent.getRawY();

        int action = motionEvent.getAction();
        switch(action & MotionEvent.ACTION_MASK) {
            case (MotionEvent.ACTION_DOWN) :

                break;
            case (MotionEvent.ACTION_MOVE) :
                FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(view.getLayoutParams());


               layout.leftMargin = X ;
               layout.topMargin = Y ;
               layout.rightMargin = -250;
               layout.bottomMargin = -250;
               view.setLayoutParams(layout);
               break;

            case (MotionEvent.ACTION_UP) :

                Log.d(TAG,"Action was UP");

                return true;
            case (MotionEvent.ACTION_CANCEL) :
                Log.d(TAG,"Action was CANCEL");
                return true;
            case (MotionEvent.ACTION_OUTSIDE) :
                Log.d(TAG,"Movement occurred outside bounds " +
                        "of current screen element");
                return true;

        }

        return true;
    }


    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {


    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }



}
