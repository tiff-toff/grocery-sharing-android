package com.example.grocerysharing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.grocerysharing.conveniences.UserVariables;
import com.example.grocerysharing.conveniences.PostRequestHTTP;

import java.io.IOException;
import java.util.List;

public class UserPass extends AppCompatActivity implements UserVariables {

    private static final String SEPARATOR = "&";
    private static final String EQUALS = "=";
    private static final String URL = "http://3.19.245.83/createacc";

    private TextView errorMsg;
    private ProgressBar loading;
    private Button button1;
    private Button button2;
    public List<String> userData;
    private TextView myText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pass);
        userData = getIntent().getStringArrayListExtra(USER_DATA);

        errorMsg = (TextView) findViewById(R.id.errorMessage);
        loading = (ProgressBar) findViewById(R.id.loading);
        button1 = (Button) findViewById(R.id.continueButton);
        button2 = (Button) findViewById(R.id.cancelButton);
        errorMsg.setVisibility(View.GONE);
        loading.setVisibility(View.GONE);
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

    private void sendAccountPost() throws Exception {
        button1.setVisibility(View.GONE);
        button2.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);

        StringBuilder parameters = new StringBuilder();
        parameters.append(FIRST_NAME).append(EQUALS).append(userData.get(0));
        parameters.append(SEPARATOR);
        parameters.append(LAST_NAME).append(EQUALS).append(userData.get(1));
        parameters.append(SEPARATOR);
        parameters.append(ADDRESS_LINE_1).append(EQUALS).append(userData.get(2));
        parameters.append(SEPARATOR);
        parameters.append(ADDRESS_LINE_2).append(EQUALS).append(userData.get(3));
        parameters.append(SEPARATOR);
        parameters.append(CITY).append(EQUALS).append(userData.get(4));
        parameters.append(SEPARATOR);
        parameters.append(STATE).append(EQUALS).append(userData.get(5));
        parameters.append(SEPARATOR);
        parameters.append(ZIP_CODE).append(EQUALS).append(userData.get(6));
        parameters.append(SEPARATOR);
        parameters.append(PHONE_NUM).append(EQUALS).append(userData.get(7));
        parameters.append(SEPARATOR);
        parameters.append(USERNAME).append(EQUALS).append(userData.get(8));
        parameters.append(SEPARATOR);
        parameters.append(PASSWORD).append(EQUALS).append(userData.get(9));

        String result = PostRequestHTTP.sendPostRequest(URL, parameters.toString());
        LinearLayout lView = new LinearLayout(this);

        myText = new TextView(this);
        myText.setText(result);

        lView.addView(myText);

        setContentView(lView);
        if (result.equals("false")) {
            button1.setVisibility(View.VISIBLE);
            button2.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
        }
    }

    public void submitAccount(View view) throws Exception {
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
