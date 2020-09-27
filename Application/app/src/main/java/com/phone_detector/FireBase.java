package com.phone_detector;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import static android.content.ContentValues.TAG;

public class FireBase {
    public static FirebaseDatabase mDataBase;

    public static String getName(final String number, final Activity activity) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final String[] name = {""};
        db.collection("numbers").document(number).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()) {
                        int max = 0;
                        for (Map.Entry<String, Long> entry : ((Map<String, Long>) doc.get("names")).entrySet()) {
                            int val = entry.getValue().intValue();
                            if (val > max) {
                                max = val;
                                name[0] = entry.getKey();
                            }
                        }
                    }
                }
                ((ResultActivity) activity).updateNumber(number, name[0]);

            }
        });

        return name[0];
    }


    public static void setData(final PhoneNumber n) {
        if (n.getPersonName().length() == 0)
            return;

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final boolean[] cond = {false};
        //new Data
        db.collection("numbers").document(n.getNumber()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Map<String, Object> number = new HashMap<>();
                if (!task.getResult().exists()) {
                    number.put("homeState", n.getHomeState());
                    number.put("names", new HashMap<String, Integer>() {{
                        put(n.getPersonName(), 1);
                    }});
                    db.collection("numbers").document(n.getNumber()).set(number);
                }
                else
                {
                    db.collection("numbers").document(n.getNumber()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (((Map<String, String>) documentSnapshot.get("names")).containsKey(n.getPersonName()))
                                db.collection("numbers").document(n.getNumber()).update("names." + n.getPersonName(), FieldValue.increment(1));
                            else
                                db.collection("numbers").document(n.getNumber()).update("names." + n.getPersonName(), 1);

                        }

                    });

                }

            }
        });

    }
}

