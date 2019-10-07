package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Context context  = LoginActivity.this;
        Log.i(ACTIVITY_NAME,"in onCreate()");

        // Get email from shared preferences if one exists
        SharedPreferences sharedPref = LoginActivity.this.getPreferences(Context.MODE_PRIVATE);
        String email = sharedPref.getString("DefaultEmail","email@domain.com");

        // Set email to email text field
        EditText edit = (EditText)findViewById(R.id.login_name);
        edit.setText(email);

        final Button loginBtn = findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = LoginActivity.this.getPreferences(Context.MODE_PRIVATE); // SP Editor
                SharedPreferences.Editor editor = sharedPref.edit();

                // Get login name field
                EditText e = (EditText)findViewById(R.id.login_name);

                // Set value of default email for future use
                editor.putString("DefaultEmail", e.getText().toString());

                // Don't forget to commit!
                editor.commit();

                // Create intent for start activity
                Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                startActivity(intent);

                Log.i(ACTIVITY_NAME, "intent pressed");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }
}
