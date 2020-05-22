package com.example.edunomics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.edunomics.Open.CurrEmail;

public class SignUp extends AppCompatActivity {

    EditText email,pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sgn);
        email = findViewById(R.id.emailid);
        pwd = findViewById(R.id.pwd);
    }

    public void SignIn2(View view) {
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if(email.getText().toString().equals("") || pwd.getText().toString().equals("")){
            return;
        }
        mAuth.createUserWithEmailAndPassword(email.getText().toString(),pwd.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.v("aaaa", "createUserWithEmail:success");
                            CurrEmail =mAuth.getCurrentUser().getEmail();
                            Intent intent = new Intent(SignUp.this,ChatActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.v("aaaa", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUp.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
