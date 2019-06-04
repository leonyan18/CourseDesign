package com.example.yan.coursedesign.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yan.coursedesign.activity.ChatActivity;
import com.example.yan.coursedesign.service.ApiService;
import com.example.yan.coursedesign.bean.Result;
import com.example.yan.coursedesign.service.UserService;
import com.example.yan.coursedesign.bean.Friend;
import com.example.yan.coursedesign.adapter.FriendAdapter;
import com.example.yan.coursedesign.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class FriendsFragment extends Fragment {
    private static final String TAG = "FriendsFragment";
    List<Friend> friendList;
    FriendAdapter friendAdapter;
    ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_friends, container, false);
        listView=view.findViewById(R.id.peoplelist);
        friendList =new ArrayList<>();
        ImageButton imageButton=view.findViewById(R.id.addfriend);
        imageButton.setOnClickListener(v -> {
            friendList.add(new Friend("new",1,"帅哥","http://www.lancedai.cn/people3.jpg"));
            listView.setAdapter(friendAdapter);
        });
        UserService userService=ApiService.retrofit.create(UserService.class);
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
        friendList.add(new Friend("yan",2,"帅哥","http://www.lancedai.cn/people2.jpg"));
        friendList.add(new Friend("chen",3,"帅哥","http://www.lancedai.cn/people1.jpg"));
        friendList.add(new Friend("xi",4,"帅哥","http://www.lancedai.cn/people3.jpg"));
        friendAdapter =new FriendAdapter(getActivity(),R.layout.people_item, friendList);
        listView.setAdapter(friendAdapter);
        listView.addFooterView(new TextView(getActivity()));
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            TextView textView= view1.findViewById(R.id.people_name);
            String name=textView.getText().toString();
            Intent intent=new Intent(getActivity(), ChatActivity.class);
            intent.putExtra("name",name);
            intent.putExtra("headPic", friendList.get(position).getImage());
            startActivityForResult(intent,1);
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if (resultCode==RESULT_OK){
                    String name=data.getStringExtra("name");
                    String content=data.getStringExtra("content");
                    if(!"".equals(content)&&content!=null){
                        Log.d(TAG, "onActivityResult:"+content);
                        for (Friend friend : friendList){
                            if(friend.getName().equals(name)){
                                friend.setSlabel(content);
                                listView.setAdapter(friendAdapter);
                                break;
                            }
                        }
                    }
                }break;
            default:
        }

    }
}
