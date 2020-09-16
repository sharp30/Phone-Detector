package com.example.phone_detector;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class NumberAdapter extends ArrayAdapter<PhoneNumber>
{
        private  Context context;
        private  List<PhoneNumber> data;
    public NumberAdapter(@NonNull Context context, int resource, @NonNull List<PhoneNumber> objects) {
        super(context, resource, objects);

        this.context = context;
        this.data = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        @SuppressLint("ViewHolder") View view = layoutInflater.inflate(R.layout.phone_number,parent,false);

        TextView tvNumber = (TextView)view.findViewById(R.id.tv_number);
        TextView tvPerson = (TextView)view.findViewById(R.id.tv_person);
        ImageButton btnCall = (ImageButton) view.findViewById(R.id.btn_call);
        ImageButton btnContact=(ImageButton)view.findViewById(R.id.btn_contact);
        ImageButton btnWhatsapp = (ImageButton)view.findViewById(R.id.btn_whatsapp);
        final PhoneNumber temp = data.get(position);


        tvNumber.setText(temp.getNumber());
        tvPerson.setText(temp.getPersonName());
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+temp.getNumber()));
                context.startActivity(intent);
            }
        });

        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                intent.putExtra(ContactsContract.Intents.Insert.PHONE, temp.getNumber());
                intent.putExtra(ContactsContract.Intents.Insert.NAME, temp.getPersonName());
                context.startActivity(intent);
            }
        });
        btnWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://wa.me/972"+temp.getNumber().substring(1)));
                context.startActivity(intent);
            }
        });
        return view;
    }
}
