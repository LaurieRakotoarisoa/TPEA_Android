package com.example.framedpictures;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class InputTextActivity extends AppCompatActivity {

    EditText editText;
    private  String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_text);

        editText = findViewById(R.id.input);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if(actionId == EditorInfo.IME_ACTION_DONE){

                    value = editText.getText().toString();

                    // clear focus
                    editText.clearFocus();

                }

                return false;

            }
        });



    }

    public void inputText(View view){
        //SharedPreferences myPrefs = this.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
       // SharedPreferences.Editor prefsEditor = myPrefs.edit();
        //prefsEditor.putString("MY_NAME", value);
        //prefsEditor.commit();

        Intent intent = new Intent();
        intent.putExtra("editTextValue ",editText.getText().toString());
        setResult(RESULT_OK,intent);
        finish();

    }
    public void annuler(View view){
        this.finish();

    }
}
