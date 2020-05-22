package com.example.edunomics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import static com.example.edunomics.Open.CurrEmail;
import static com.example.edunomics.Open.CurrRoom;

public class Lgin extends AppCompatActivity {
    EditText email,pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lgin);
        email = findViewById(R.id.emailid);
        pwd = findViewById(R.id.pwd);

    }

    public void LgIn(View view) {
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if(email.getText().toString().equals("") || pwd.getText().toString().equals("")){
            return;
        }
        mAuth.signInWithEmailAndPassword(email.getText().toString(), pwd.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.v("aaa", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            CurrEmail = user.getEmail();
                            Intent intent = new Intent(Lgin.this, ChatActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.v("aaaa", "signInWithEmail:failure", task.getException());
                            Toast.makeText(Lgin.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void GoToSignUp(View view) {
        Intent intent = new Intent(this,SignUp.class);
        startActivity(intent);
    }
}
