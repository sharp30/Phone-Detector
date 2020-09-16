package com.example.phone_detector;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result2);
        ListView listView = findViewById(R.id.listview);
        ArrayList<PhoneNumber> numberArray = (ArrayList<PhoneNumber>) this.getIntent().getSerializableExtra("data");
        NumberAdapter adapter = new NumberAdapter(this, R.layout.phone_number, numberArray);
        listView.setAdapter(adapter);

        tvFail = (TextView)findViewById(R.id.tv_failMessage);
        if(numberArray.size() > 0)
            tvFail.setVisibility(View.GONE);
    }
}