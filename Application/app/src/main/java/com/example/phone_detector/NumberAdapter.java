package com.example.phone_detector;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class NumberAdapter extends ArrayAdapter<PhoneNumber>
{

    public NumberAdapter(@NonNull Context context, int resource, @NonNull List<PhoneNumber> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
