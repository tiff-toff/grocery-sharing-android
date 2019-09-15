package com.example.grocerysharing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void createAccount(View view) {
        Intent createAccountIntent = new Intent(this, CreateAccount.class);
        startActivity(createAccountIntent);
    }

    public void signIn(View view) {
        Intent signInIntent = new Intent(this, SignIn.class);
        startActivity(signInIntent);
    }
}
