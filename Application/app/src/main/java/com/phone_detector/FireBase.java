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

    public static String getName(final String number, final Activity activity)
    //public static String getName(final String number)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final String[] name = {""};
            //final CountDownLatch clock = new CountDownLatch(1);
        db.collection("numbers").document(number).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        if (doc.exists()) {
                            int max = 0;
                            for (Map.Entry<String, Long> entry : ((Map<String, Long>)doc.get("names")).entrySet()) {
                                int val = entry.getValue().intValue();//Integer.parseInt(entry.getValue());
                                if (val  > max) {
                                    max = val;
                                    name[0] = entry.getKey();
                                }
                            }
                        }
                    }
                    ((ResultActivity)activity).updateNumber(number,name[0]);
                    //mainActivity.addNumber(new PhoneNumber(number,name[0]));
                    //clock.countDown();
                }
        });
        //try {
            //clock.await();
        //} catch (InterruptedException e) {
        //    e.printStackTrace();
        //}
        return name[0];
    }


    public static void setData(final PhoneNumber n) {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final boolean[] cond = {false};
        //new Data
        db.collection("numbers").whereEqualTo("document_id", n.getNumber()).get() .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                cond[0] = task.getResult().size() == 1;
                Map<String, Object> number = new HashMap<>();
                if (cond[0]) {
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
                            if (((Map<String,String>)documentSnapshot.get("names")).containsKey(n.getPersonName()))
                                db.collection("numbers").document(n.getNumber()).update("names." + n.getPersonName(), FieldValue.increment(1));
                            else
                                db.collection("numbers").document(n.getNumber()).update("names." + n.getPersonName(), 1);

                        }

                    });


                    }

            }
        });
        /*
        if(!cond[0]) {
                Map<String, Object> number = new HashMap<>();
                number.put("homeState", n.getHomeState());
                number.put("names", new HashMap<String, Integer>() {{
                    put(n.getPersonName(), 1);
            }});

            db.collection("numbers").document(n.getNumber()).set(number).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "DocumentSnapshot successfully written!");
                }
            });
        }
        else
        {
            db.collection("numbers").document(n.getNumber()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    cond[0] = ((Map<String,Long>)documentSnapshot.get("names")).containsKey(n.getPersonName());
                }
            });
            if(cond[0])
                db.collection("numbers").document(n.getNumber()).update("names."+n.getPersonName(),1);
            else
                db.collection("numbers").document(n.getNumber()).update("names."+n.getPersonName(), FieldValue.increment(1));

        }
    }*/

    }
}
