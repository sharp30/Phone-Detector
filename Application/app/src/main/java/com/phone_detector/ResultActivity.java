package com.phone_detector;

import android.opengl.Visibility;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {
    private TextView tvFail;
    private NumberAdapter adapter;
    private Thread thread;
    private ArrayList<PhoneNumber> numberArray;
    int size;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result2);
        ListView listView = findViewById(R.id.listview);
        numberArray = (ArrayList<PhoneNumber>) this.getIntent().getSerializableExtra("data");
        size = numberArray.size();
        adapter = new NumberAdapter(this, R.layout.phone_number, numberArray);
        listView.setAdapter(adapter);

        tvFail = (TextView) findViewById(R.id.tv_failMessage);
        tvFail.setVisibility(View.GONE);
        for(PhoneNumber i : numberArray)
            FireBase.getName(i.getNumber(),this);



        //thread.start();

    }

    @Override
    public void onDestroy() {
        thread.interrupt();
        super.onDestroy();
    }
    public void updateNumber(String number, String name)
    {
        for(int i =0;i<numberArray.size();i++) {
            if (numberArray.get(i).getNumber() == number) {
                numberArray.get(i).SetPersonName(name);
                this.adapter.notifyDataSetChanged();
                return;
            }
        }
    }
}