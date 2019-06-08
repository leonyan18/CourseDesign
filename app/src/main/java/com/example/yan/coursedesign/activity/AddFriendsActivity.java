package com.example.yan.coursedesign.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yan.coursedesign.R;
import com.example.yan.coursedesign.adapter.FriendAdapter;
import com.example.yan.coursedesign.bean.Friend;
import com.example.yan.coursedesign.bean.Result;
import com.example.yan.coursedesign.service.ApiService;
import com.example.yan.coursedesign.service.UserService;
import com.example.yan.coursedesign.util.MyApplication;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFriendsActivity extends AppCompatActivity {
    private List<Friend> friendList;
    private FriendAdapter friendAdapter;
    private ListView listView;
    private SearchView sv;
    private UserService userService=ApiService.retrofit.create(UserService.class);
    private static final String TAG = "AddFriendsActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_friends);
        listView=findViewById(R.id.peoplelist);
        listView.addFooterView(new TextView(this));
        sv=findViewById(R.id.sv);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchFriend(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        friendList =new ArrayList<>();
        friendAdapter=new FriendAdapter(this,R.layout.people_item, friendList);
        initData();
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            Friend friend=friendList.get(position);
            addFriend(friend.getId(),1);
        });
    }
    public void addFriend(int uid1,int uid2){
        Call<Result<String>> call = userService.addFriend(uid1, uid2);
        call.enqueue(new Callback<Result<String>>() {
            @Override
            public void onResponse(Call<Result<String>> call, Response<Result<String>> response) {
                Toast.makeText(MyApplication.getContext(), R.string.add_success, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onFailure(Call<Result<String>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void initData(){
        Call<Result<List<Friend>>> call = userService.getPeopleList(1);
        call.enqueue(new Callback<Result<List<Friend>>>() {
            @Override
            public void onResponse(Call<Result<List<Friend>>> call, Response<Result<List<Friend>>> response) {
                friendList.clear();
                List<Friend> body = response.body().getData();
                for (Friend f:body) {
                    friendList.add(f);
                    Log.d(TAG, "onResponse: "+f);
                }
                listView.setAdapter(friendAdapter);
            }

            @Override
            public void onFailure(Call<Result<List<Friend>>> call, Throwable t) {
                Log.d(TAG, "onResponse: ERROR");
            }
        });
    }

    public void searchFriend(String keyword){
        Call<Result<List<Friend>>> call = userService.searchPeopleList(1,keyword);
        call.enqueue(new Callback<Result<List<Friend>>>() {
            @Override
            public void onResponse(Call<Result<List<Friend>>> call, Response<Result<List<Friend>>> response) {
                friendList.clear();
                List<Friend> body = response.body().getData();
                for (Friend f:body) {
                    friendList.add(f);
                    Log.d(TAG, "onResponse: "+f);
                }
                listView.setAdapter(friendAdapter);
            }

            @Override
            public void onFailure(Call<Result<List<Friend>>> call, Throwable t) {
                Log.d(TAG, "onResponse: ERROR");
            }
        });
    }

}
