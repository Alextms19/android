package com.example.bills;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.Objects;

public class HelperFireBase {
    public static final  String getUserID(){
        return Objects.requireNonNull(FirebaseAuth
                .getInstance()
                .getCurrentUser()).getUid();
    }

    public static final DatabaseReference getFirebaseReference(String referencePath){
        return FirebaseDatabase.getInstance("https://bills-144d2-default-rtdb.europe-west1.firebasedatabase.app/").getReference(referencePath);
    }
}
