package com.phone_detector;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Person;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static android.Manifest.permission.CALL_PHONE;
import static androidx.core.app.ActivityCompat.requestPermissions;

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

        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        @SuppressLint("ViewHolder") View view = layoutInflater.inflate(R.layout.phone_number, parent, false);

        TextView tvNumber = (TextView) view.findViewById(R.id.tv_number);
        final EditText etName = (EditText) view.findViewById(R.id.et_name);
        ImageButton btnCall = (ImageButton) view.findViewById(R.id.btn_call);
        ImageButton btnContact = (ImageButton) view.findViewById(R.id.btn_contact);
        ImageButton btnWhatsapp = (ImageButton) view.findViewById(R.id.btn_whatsapp);
        final ImageButton btnSave = (ImageButton) view.findViewById(R.id.btn_save);
        final PhoneNumber temp = data.get(position);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                temp.SetPersonName(name);
                FireBase.setData(temp);
                //check if changes in Contact;
                view.setVisibility(View.GONE);
            }
        });
        btnSave.setVisibility(View.GONE);

        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if ((charSequence.toString()).length() > 0)
                    btnSave.setVisibility(View.VISIBLE);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + temp.getNumber()));
                if (ContextCompat.checkSelfPermission((Activity) context, CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions((Activity) context, new String[]{CALL_PHONE}, 1);
                }
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
        ViewGroup layout = (ViewGroup) btnWhatsapp.getParent();
        if (layout != null && temp.getHomeState())
            layout.removeView(btnWhatsapp);
        btnWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String number = temp.getNumber();
                if(temp.getNumber().charAt(0) == '0')
                {
                    number = "972" + number.substring(1);
                }
                else if(temp.getNumber().charAt(0) == '+')
                {
                    number = number.substring(1);
                }
                intent.setData(Uri.parse("https://wa.me/"+number));
                context.startActivity(intent);
            }
        });

        tvNumber.setText(temp.getNumber());
        etName.setText(temp.getPersonName());
        return view;
    }

    public void setData(PhoneNumber p)
    {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child(p.getNumber());
        db.setValue(p);
    }


}
