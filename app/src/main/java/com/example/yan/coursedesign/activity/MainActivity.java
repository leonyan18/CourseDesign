package com.example.yan.coursedesign.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.yan.coursedesign.R;
import com.example.yan.coursedesign.fragment.FriendsFragment;
import com.example.yan.coursedesign.fragment.HomeFragment;
import com.example.yan.coursedesign.service.UserService;
import com.example.yan.coursedesign.util.MyApplication;
import com.example.yan.coursedesign.adapter.MyPagerAdapter;
import com.example.yan.coursedesign.service.ApiService;
import com.example.yan.coursedesign.service.ImgService;
import com.example.yan.coursedesign.bean.Result;
import com.example.yan.coursedesign.bean.Token;
import com.example.yan.coursedesign.service.TrendService;
import com.example.yan.coursedesign.util.QiniuUtil;
import com.zhihu.matisse.Matisse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private List<ImageButton> btns;
    public static Token token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImgService imgService= ApiService.retrofit.create(ImgService.class);
        Call<Result<Token>> call = imgService.getToken();
        call.enqueue(new Callback<Result<Token>>() {
            @Override
            public void onResponse(Call<Result<Token>> call, Response<Result<Token>> response) {
                token = response.body().getData();
                Log.d(TAG, "onResponse: "+token);
            }

            @Override
            public void onFailure(Call<Result<Token>> call, Throwable t) {
                t.printStackTrace();
            }
        });
        btns=new ArrayList<>();
        final ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        ImageButton homebtn=findViewById(R.id.homebtn);
        ImageButton friendsbtn=findViewById(R.id.friendsbtn);
        ImageButton mebtn=findViewById(R.id.mebtn);
        btns.add(homebtn);
        btns.add(friendsbtn);
        btns.add(mebtn);
        homebtn.setSelected(true);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                for (ImageButton btn: btns) {
                    btn.setSelected(false);
                }
                btns.get(position).setSelected(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        homebtn.setOnClickListener(v -> viewPager.setCurrentItem(0));
        friendsbtn.setOnClickListener(v -> viewPager.setCurrentItem(1));
        mebtn.setOnClickListener(v -> viewPager.setCurrentItem(2));
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == HomeFragment.REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
//            mAdapter.setData(Matisse.obtainResult(data), Matisse.obtainPathResult(data));
            List<String > paths=Matisse.obtainPathResult(data);
            String pids="";
            for (String s:paths) {
                File file=new File(s);
                String pid=UUID.randomUUID().toString();
                String[] strings=s.split("\\.");
                String type=strings[strings.length-1];
                pid=pid+"."+type;
                Log.d(TAG, "onActivityResult: "+pid);
                pids+=pid+",";
                QiniuUtil.upload(file,pid,token.getAns());
            }
            Log.d(TAG, "onActivityResult: "+pids);
            TrendService trendService= ApiService.retrofit.create(TrendService.class);
            Call<Result<String>> call = trendService.uploadTrend(pids,1);
            call.enqueue(new Callback<Result<String>>() {
                @Override
                public void onResponse(Call<Result<String>> call, Response<Result<String>> response) {
                    Toast.makeText(MyApplication.getContext(), R.string.upload_success, Toast.LENGTH_SHORT)
                            .show();
                }

                @Override
                public void onFailure(Call<Result<String>> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        Log.v(TAG, "populate context menu");
        menu.add(0, 1, Menu.NONE, "删除");
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Log.v(TAG, "context item seleted ID="+ menuInfo.id);
        switch (item.getItemId()){
            case 1 :
                FriendsFragment.deleteFriend(1, (int) menuInfo.id);
                break;
        }
        return true;
    }

}
