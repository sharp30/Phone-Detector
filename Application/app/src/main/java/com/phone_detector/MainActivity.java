package com.phone_detector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.biometrics.BiometricManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.ActionMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private static final int CAMERA_REQ = 1;
    private static final int SELECT_IMAGE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.choose_pic);
        Button button1 = findViewById(R.id.open_camera);
        button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE);
            }
        });
        button1.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQ);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bmp = null;
        if (requestCode == CAMERA_REQ && resultCode == RESULT_OK)
                {
                    bmp = (Bitmap) data.getExtras().get("data");
                }
        else if (requestCode == SELECT_IMAGE && resultCode == RESULT_OK)
        {
            Uri imageUri = data.getData();
            try
            {
                bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        ArrayList<String> numbers = ImageDetect.imageToPhoneNumbers(bmp, getApplicationContext());
        ArrayList<PhoneNumber> arr = new ArrayList<>();
        int i = 0;
        for (String number : numbers)
        {
            System.out.println(number);
            arr.add(getData((number.replace("-",""))));
            i++;
        }
        Intent next = new Intent(this,ResultActivity.class);
        next.putExtra("data",arr);
        startActivity(next);
    }

    public PhoneNumber getData(final String number)
    {
        final PhoneNumber[] output = {new PhoneNumber(number, "")};
        final String a ="";
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.hasChild(number))
                    return;
                output[0].SetPersonName(snapshot.child(number).child("personName").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return  output[0];

    }
}