package com.example.opsc7312_poe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button btnNewRegister,btnEnter;
    private EditText edtPass,edtUser;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNewRegister =(Button) findViewById(R.id.btnNewRegister);
        btnNewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,UserRegister.class));
            }
        });

        btnEnter=(Button) findViewById(R.id.btnEnter);
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });

        edtUser=(EditText) findViewById(R.id.edtUser);
        edtPass=(EditText) findViewById(R.id.edtPass);

        progressBar=(ProgressBar)  findViewById(R.id.progressBar);

        mAuth=FirebaseAuth.getInstance();

    }

    private void userLogin() {
        String email =edtUser.getText().toString().trim();
        String password=edtPass.getText().toString().trim();

        if(email.isEmpty()){
            edtUser.setError("Email is required");
            edtUser.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtUser.setError("Please enter a valid email");
            edtUser.requestFocus();
            return;
        }

        if(password.isEmpty()){
            edtPass.setError("Password is required");
            edtPass.requestFocus();
            return;
        }

        if(password.length()<6){
            edtPass.setError("Min password length is 6 characters");
            edtPass.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //redirect to Map Activity
                    startActivity(new Intent(MainActivity.this,MapActivity.class));
                }else{
                    Toast.makeText(MainActivity.this, "Failed to Log in, recheck credentials", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }
}