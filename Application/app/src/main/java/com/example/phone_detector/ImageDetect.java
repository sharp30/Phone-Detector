package com.example.phone_detector;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageDetect {
    static public ArrayList<String> imageToPhoneNumbers(Bitmap bmp, Context context)
    {
        TextRecognizer textRecognizer = new TextRecognizer.Builder(context).build();
        Frame frame = new Frame.Builder().setBitmap(bmp).build();
        String imageText = "";
        SparseArray<TextBlock> textBlocks = textRecognizer.detect(frame);

        for (int i = 0; i < textBlocks.size(); i++) {
            TextBlock textBlock = textBlocks.get(textBlocks.keyAt(i));
            imageText = textBlock.getValue();
        }
        System.out.println(imageText);

        String pattern = "05?\\d(-?\\d){7}";
        Pattern r = Pattern.compile(pattern);
        ArrayList<String> result = new ArrayList<>();
        Matcher m = r.matcher(imageText);
        while(m.find()) {
             result.add(m.group());
        }
        return result;
    }
}
