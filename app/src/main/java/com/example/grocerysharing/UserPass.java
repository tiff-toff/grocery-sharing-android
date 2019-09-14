package com.example.grocerysharing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.grocerysharing.conveniences.UserVariables;

import java.util.List;

public class UserPass extends AppCompatActivity implements UserVariables {

    private static final String SEPARATOR = ",";

    private TextView errorMsg;
    public List<String> userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pass);
        errorMsg = (TextView) findViewById(R.id.errorMessage);
        userData = getIntent().getStringArrayListExtra(USER_DATA);
        errorMsg.setVisibility(View.GONE);
    }

    private String getUsername() {
        EditText showUsername = (EditText) findViewById(R.id.username);
        return showUsername.getText().toString();
    }

    private String getPassword1() {
        EditText showPassword = (EditText) findViewById(R.id.password1);
        return showPassword.getText().toString();
    }

    private String getPassword2() {
        EditText showPassword = (EditText) findViewById(R.id.password2);
        return showPassword.getText().toString();
    }

    private void sendAccountPost() {

    }

    public void submitAccount(View view) {
        String password1 = getPassword1();
        String password2 = getPassword2();

        if (!password1.equals(password2)) {
            errorMsg.setVisibility(View.VISIBLE);
            return;
        }

        errorMsg.setVisibility(View.GONE);
        String username = getUsername();
        userData.add(username);
        userData.add(password1);

        sendAccountPost();
    }

    public void cancelCreateAccount(View view) {
        Intent cancelAccountIntent = new Intent(this, MainActivity.class);
        startActivity(cancelAccountIntent);
    }
}
