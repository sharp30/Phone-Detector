package com.phone_detector;

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
    public static ArrayList<String> removeDuplicates(ArrayList<String> list)
    {
        ArrayList<String> newList = new ArrayList<>();
        for (String element : list) {
            if (!newList.contains(element)) {
                newList.add(element);
            }
        }
        return newList;
    }

    static public ArrayList<String> imageToPhoneNumbers(Bitmap bmp, Context context)
    {
        TextRecognizer textRecognizer = new TextRecognizer.Builder(context).build();
        Frame frame = new Frame.Builder().setBitmap(bmp).build();
        StringBuilder imageText = new StringBuilder();
        SparseArray<TextBlock> textBlocks = textRecognizer.detect(frame);

        for (int i = 0; i < textBlocks.size(); i++) {
            TextBlock textBlock = textBlocks.get(textBlocks.keyAt(i));
            imageText.append(textBlock.getValue());
        }
        System.out.println(imageText.toString());

        String pattern = "((05|07|0|\\+\\d{4})(-?\\d){8})";
        Pattern r = Pattern.compile(pattern);
        ArrayList<String> result = new ArrayList<>();
        Matcher m = r.matcher(imageText.toString());
        while(m.find()) {
             result.add(m.group());
        }
        result = removeDuplicates(result);
        return result;
    }



}
