package com.example.yan.coursedesign.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.yan.coursedesign.R;

import io.github.tonnyl.spark.Spark;

public class LoginActivity extends AppCompatActivity {
    private Spark spark;
    private LinearLayout linearLayout;
    private Button button;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        linearLayout=findViewById(R.id.frameLayout);
        spark=new Spark.Builder()
                .setView(linearLayout) // View or view group
                .setDuration(4000)
                .setAnimList(Spark.ANIM_BLUE_PURPLE).build();
        spark.startAnimation();
        button=findViewById(R.id.login);
        button.setOnClickListener(view -> {
            Intent intent=new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        spark.startAnimation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        spark.stopAnimation();
    }
}
