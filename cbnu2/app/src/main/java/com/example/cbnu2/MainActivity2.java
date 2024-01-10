package com.example.cbnu2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.example.cbnu2.databinding.ActivityMain2Binding;


public class MainActivity2 extends AppCompatActivity {

    private ActivityMain2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
            }
        });
        final Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String pwd = intent.getStringExtra("pwd");

        TextView idView = binding.idInfo;
        TextView pwdView = binding.pwdInfo;

        idView.setText("ID : " + id);
        pwdView.setText("Password : " + pwd);

    }
}