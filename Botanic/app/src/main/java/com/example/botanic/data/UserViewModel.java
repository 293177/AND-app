package com.example.botanic.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseUser;

public class UserViewModel extends AndroidViewModel {
    private final UserRepository userRepository;
    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = UserRepository.getInstance(application);
    }
    public LiveData<FirebaseUser> getUser(){
        return userRepository.getUser();
    }
}
