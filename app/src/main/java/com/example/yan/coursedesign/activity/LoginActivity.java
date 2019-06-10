package com.example.yan.coursedesign.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.yan.coursedesign.R;
import com.example.yan.coursedesign.bean.Result;
import com.example.yan.coursedesign.service.ApiService;
import com.example.yan.coursedesign.service.LoginService;
import com.example.yan.coursedesign.service.UserService;
import com.example.yan.coursedesign.util.MyApplication;
import com.example.yan.coursedesign.util.UserInfo;

import io.github.tonnyl.spark.Spark;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private Spark spark;
    private LinearLayout linearLayout;
    private Button button;
    private EditText usernameText;
    private EditText passwordText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        linearLayout = findViewById(R.id.frameLayout);
        SharedPreferences sharedPreferences = MyApplication.getContext().getSharedPreferences("data", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", 0);
        if (userId != 0) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            finish();
            startActivity(intent);
        }
        spark = new Spark.Builder()
                .setView(linearLayout) // View or view group
                .setDuration(4000)
                .setAnimList(Spark.ANIM_BLUE_PURPLE).build();
        spark.startAnimation();
        button = findViewById(R.id.login);
        usernameText = findViewById(R.id.username);
        passwordText = findViewById(R.id.password);
        String beforeUsername = sharedPreferences.getString("username", "");
        usernameText.setText(beforeUsername);
        button.setOnClickListener(view -> {
            LoginService loginService = ApiService.retrofit.create(LoginService.class);
            String username = usernameText.getText().toString();
            String password = passwordText.getText().toString();
            Call<Result<Integer>> call = loginService.login(username, password);
            call.enqueue(new Callback<Result<Integer>>() {
                @Override
                public void onResponse(Call<Result<Integer>> call, Response<Result<Integer>> response) {
                    int ans = response.body().getData();
                    Toast.makeText(MyApplication.getContext(), response.body().getData() + "", Toast.LENGTH_SHORT)
                            .show();
                    SharedPreferences.Editor editor = MyApplication.getContext().getSharedPreferences("data", MODE_PRIVATE).edit();
                    editor.putString("username", username);
                    if (ans > 0) {
                        Toast.makeText(MyApplication.getContext(), R.string.login_success, Toast.LENGTH_SHORT)
                                .show();
                        editor.putInt("userId", ans);
                        UserInfo.userId = ans;
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        finish();
                        startActivity(intent);
                    } else if (ans == 0) {
                        Toast.makeText(MyApplication.getContext(), R.string.wrong_password, Toast.LENGTH_SHORT)
                                .show();
                    } else if (ans == -1) {
                        Toast.makeText(MyApplication.getContext(), R.string.no_such_user, Toast.LENGTH_SHORT)
                                .show();
                    }
                    editor.apply();
                }

                @Override
                public void onFailure(Call<Result<Integer>> call, Throwable t) {
                    Toast.makeText(MyApplication.getContext(), R.string.time_out, Toast.LENGTH_SHORT)
                            .show();
                    t.printStackTrace();
                }
            });
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
