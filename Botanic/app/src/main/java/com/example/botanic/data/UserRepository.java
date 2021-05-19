package com.example.botanic.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseUser;

public class UserRepository {
    private final User user;
    private final Application app;
    private static  UserRepository instance;

    private UserRepository(Application app) {
        this.app = app;
        user = new User();
    }

    public static synchronized UserRepository getInstance(Application app) {
        if (instance == null){
            instance = new UserRepository(app);
        }
        return instance;
    }

    public LiveData<FirebaseUser> getUser() {
        return user;
    }
    public void signOut(){
        AuthUI.getInstance().signOut(app.getApplicationContext());
    }
}
