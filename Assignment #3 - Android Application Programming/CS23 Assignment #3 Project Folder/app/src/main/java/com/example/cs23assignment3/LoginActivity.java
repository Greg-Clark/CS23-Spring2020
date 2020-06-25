package com.example.cs23assignment3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void checkUserPass(View view)
    {
        EditText editText1 = findViewById(R.id.usernameEditText);
        String username = editText1.getText().toString();

        EditText editText2 = findViewById(R.id.passwordEditText);
        String password = editText2.getText().toString();

        if(!((username.equals("John Doe") && password.equals("1234")) || (username.equals("Jane Doe") && password.equals("5678"))))
        {
            Context context = getApplicationContext();
            CharSequence text = "Incorrect Username or Password";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            toast.setGravity(Gravity.TOP, 0, 500);

            editText1.getText().clear();
            editText2.getText().clear();
            return;
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
