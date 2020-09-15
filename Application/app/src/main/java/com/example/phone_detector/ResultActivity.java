package com.example.phone_detector;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<PhoneNumber> numberArray = new ArrayList<PhoneNumber>();
    private NumberAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result2);
        listView = findViewById(R.id.listview);
        adapter = new NumberAdapter(getApplicationContext(),R.layout.phone_number,numberArray);
        listView.setAdapter(adapter);

        // next thing you have to do is check if your adapter has changed
        adapter.notifyDataSetChanged();
    }
}