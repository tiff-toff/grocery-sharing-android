package com.example.grocerysharing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.grocerysharing.conveniences.GetRequestHTTP;
import com.example.grocerysharing.conveniences.UserVariables;

import java.io.IOException;

public class SignIn extends AppCompatActivity implements UserVariables {

    private static final String SEPARATOR = "&";
    private static final String EQUALS = "=";
    private static final String URL = "http://3.19.245.83/login";

    private TextView errorMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        errorMsg = (TextView) findViewById(R.id.loginError);
        errorMsg.setVisibility(View.GONE);
    }

    private String getUsername() {
        EditText showUsername = (EditText) findViewById(R.id.username);
        return showUsername.getText().toString();
    }

    private String getPassword() {
        EditText showPassword = (EditText) findViewById(R.id.password1);
        return showPassword.getText().toString();
    }

    public void submitCredentials(View view) throws IOException {
        String username = getUsername();
        String password = getPassword();

        StringBuilder parameters = new StringBuilder();
        parameters.append(USERNAME).append(EQUALS).append(username);
        parameters.append(SEPARATOR);
        parameters.append(PASSWORD).append(EQUALS).append(password);

        String result = GetRequestHTTP.sendGetRequest(URL, parameters.toString());

        if (result.equals("false")) {
            errorMsg.setVisibility(View.VISIBLE);
            return;
        }
        errorMsg.setVisibility(View.GONE);
    }
}
