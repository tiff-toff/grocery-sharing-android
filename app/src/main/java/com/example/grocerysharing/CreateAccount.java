package com.example.grocerysharing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.grocerysharing.conveniences.UserVariables;

import java.util.ArrayList;
import java.util.List;

public class CreateAccount extends AppCompatActivity implements UserVariables {

    private List<String> userData;

    public CreateAccount() {
        userData = new ArrayList<String>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }

    private void collectUserData() {
        EditText showContent = (EditText) findViewById(R.id.firstName);
        userData.add(showContent.getText().toString());

        showContent = (EditText) findViewById(R.id.lastName);
        userData.add(showContent.getText().toString());

        showContent = (EditText) findViewById(R.id.addressLine1);
        userData.add(showContent.getText().toString());

        showContent = (EditText) findViewById(R.id.addressLine2);
        userData.add(showContent.getText().toString());

        showContent = (EditText) findViewById(R.id.city);
        userData.add(showContent.getText().toString());

        showContent = (EditText) findViewById(R.id.state);
        userData.add(showContent.getText().toString());

        showContent = (EditText) findViewById(R.id.zipCode);
        userData.add(showContent.getText().toString());

        showContent = (EditText) findViewById(R.id.phoneNum);
        userData.add(showContent.getText().toString());
    }

    public void createUserPass(View view) {
        Intent createUserPassIntent = new Intent(this, UserPass.class);

        collectUserData();
        System.out.println(userData.toString());
        createUserPassIntent.putStringArrayListExtra(USER_DATA, (ArrayList)userData);

        startActivity(createUserPassIntent);
    }

    public void cancelCreateAccount(View view) {
        Intent cancelAccountIntent = new Intent(this, MainActivity.class);
        startActivity(cancelAccountIntent);
    }
}
