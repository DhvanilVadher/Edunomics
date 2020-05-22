package com.example.edunomics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

import static com.example.edunomics.Open.CurrRoom;

public class MainActivity extends AppCompatActivity {

    private static final String[] SKILLS = new String[]{
            "Java","Cpp","Python","Node js","Data-Science","CloudComputing"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AutoCompleteTextView edt = findViewById(R.id.SkillAuto);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,SKILLS);
        edt.setAdapter(adapter);
    }

    public void GoToChat(View view) {
        AutoCompleteTextView edt = findViewById(R.id.SkillAuto);
        if(Arrays.asList(SKILLS).contains(edt.getText().toString()))
        {
            CurrRoom = edt.getText().toString();
            Intent intent = new Intent(this,Lgin.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "No Group Found", Toast.LENGTH_LONG).show();
        }
    }
}
