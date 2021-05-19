package com.example.botanic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;


import com.example.botanic.data.UserViewModel;
import com.firebase.ui.auth.AuthUI;
import java.util.Arrays;
import java.util.List;

public class SignInActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN=42;
    private UserViewModel viewModel;
    public SignInActivity(){}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        checkIfSignedIn();
        setContentView(R.layout.sign_in_activity);
    }
    private void checkIfSignedIn(){
        viewModel.getUser().observe(this, user->{
            if (user!=null){
                goToMainActivity();
            }
        });
    }
    private void goToMainActivity(){
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
    public void signIn(View view){
        List<AuthUI.IdpConfig>providers = Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build(),new AuthUI.IdpConfig.FacebookBuilder().build(),new AuthUI.IdpConfig.GoogleBuilder().build());
        Intent signInIntent = AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).build();
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }
    private void handleSignInRequest(int resultCode) {
        if (resultCode == RESULT_OK)
            goToMainActivity();
        else
            Toast.makeText(this, "SIGN IN CANCELLED", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            handleSignInRequest(resultCode);
        }
    }
}
