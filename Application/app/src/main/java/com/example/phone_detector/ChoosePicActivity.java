package com.example.phone_detector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.io.IOException;
<<<<<<< HEAD
=======
import java.io.OutputStream;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
>>>>>>> d7cf6bb181a8c2db21a7bcda47a2a80bcaf1cce7


public class ChoosePicActivity extends AppCompatActivity
{
    private static final int SELECT_IMAGE = 1;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_pic);

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE)
        {
            if(resultCode == RESULT_OK)
            {
                Uri imageUri = data.getData();
                Bitmap bmp = null;
                try {
                    bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ImageView imageView = findViewById(R.id.img);
                imageView.setImageBitmap(bmp);

<<<<<<< HEAD

=======
                Tesseract tesseract = new Tesseract();
                tesseract.setDatapath("C:/Users/Administrator/Downloads/Tess4J/tessdata");

                /* this line is wrong
                try {
                    String text = tesseract.doOCR(new File("C:\\Users\\SharpComp\\Pictures\\avatar.png"));
                } catch (TesseractException e) {
                    e.printStackTrace();
                }
                */
>>>>>>> d7cf6bb181a8c2db21a7bcda47a2a80bcaf1cce7
            }
        }

    };
}