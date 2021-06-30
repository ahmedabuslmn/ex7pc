package com.example.ex7pc2021;
import android.app.Application;

import com.google.firebase.FirebaseApp;

import java.time.LocalDate;

public class SandwichApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }
}
