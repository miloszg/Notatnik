package com.milosz.notepad;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

public class wpiszNotatke extends AppCompatActivity {
    int noteId;
    EditText editText;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wpisz_notatke);
        editText=findViewById(R.id.editText);
        Intent intent=getIntent();
        noteId=intent.getIntExtra("noteId",-1);
        if(noteId!=-1)
        {
            editText.setText(MainActivity.notatki.get(noteId));
        }
        else{
            MainActivity.notatki.add("");
            noteId=MainActivity.notatki.size()-1;
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.notatki.set(noteId,String.valueOf(s));
                MainActivity.arrayAdapter.notifyDataSetChanged();

                sharedPreferences= getApplicationContext().getSharedPreferences("com.milosz.notepad", Context.MODE_PRIVATE);
                HashSet<String> set=new HashSet<>(MainActivity.notatki);
                sharedPreferences.edit().putStringSet("notatki",set).apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
      //  sharedPreferences.edit().putString("notatka",editText.getText().toString());

    }
}
