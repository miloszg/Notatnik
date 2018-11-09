package com.milosz.notepad;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView lista;
    static ArrayList<String> notatki=new ArrayList<>();
    SharedPreferences sharedPreferences;
    static ArrayAdapter<String> arrayAdapter;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.note:
                Intent intent=new Intent(this,wpiszNotatke.class);
                startActivity(intent);
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences= getApplicationContext().getSharedPreferences("com.milosz.notepad", Context.MODE_PRIVATE);
        lista=findViewById(R.id.lista);

        HashSet<String> set= (HashSet<String>) sharedPreferences.getStringSet("notatki",null);
        if(set==null){
            notatki.add("Przykładowa notatka");
        } else {
            notatki=new ArrayList(set);

        }
        arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, notatki);
        lista.setAdapter(arrayAdapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1=new Intent(getApplicationContext(),wpiszNotatke.class);
                intent1.putExtra("noteId",position);
                startActivity(intent1);
            }
        });
        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Chcesz usunąć tą notatkę ?")
                        .setMessage("Jesteś pewny, że chcesz ją usunąć ?")
                        .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                notatki.remove(position);
                                arrayAdapter.notifyDataSetChanged();

                                HashSet<String> set=new HashSet<>(MainActivity.notatki);
                                sharedPreferences.edit().putStringSet("notatki",set).apply();
                            }
                        })
                        .setNegativeButton("Nie",null).show();

                return true;
            }
        });
    }
}
