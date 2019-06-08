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
import android.widget.Toast;

import com.example.yan.coursedesign.activity.AddFriendsActivity;
import com.example.yan.coursedesign.activity.ChatActivity;
import com.example.yan.coursedesign.bean.Msg;
import com.example.yan.coursedesign.service.ApiService;
import com.example.yan.coursedesign.bean.Result;
import com.example.yan.coursedesign.service.UserService;
import com.example.yan.coursedesign.bean.Friend;
import com.example.yan.coursedesign.adapter.FriendAdapter;
import com.example.yan.coursedesign.R;
import com.example.yan.coursedesign.util.MyApplication;

import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class FriendsFragment extends Fragment {
    private static final String TAG = "FriendsFragment";
    private static List<Friend> friendList;
    private static FriendAdapter friendAdapter;
    private static ListView listView;
    private static UserService userService=ApiService.retrofit.create(UserService.class);
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_friends, container, false);
        listView=view.findViewById(R.id.peoplelist);
        friendList =new ArrayList<>();
        Connector.getDatabase();
        initData();
        Msg msg=new Msg();
        msg.setFrom(1);
        msg.setTo(2);
        msg.setContent("database test");
        msg.setType(Msg.TYPE_SENT);
        msg.save();
        Msg msg1=new Msg();
        msg1.setFrom(2);
        msg1.setTo(1);
        msg1.setContent("database test");
        msg1.setType(Msg.TYPE_RECEIVED);
        msg1.save();
        ImageButton imageButton=view.findViewById(R.id.addfriend);
        imageButton.setOnClickListener(v -> {
            Intent intent=new Intent(getActivity(), AddFriendsActivity.class);
            startActivity(intent);
        });
        friendAdapter =new FriendAdapter(getActivity(),R.layout.friend_item, friendList);
        listView.setAdapter(friendAdapter);
        listView.addFooterView(new TextView(getActivity()));
        getActivity().registerForContextMenu(listView);
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            Intent intent=new Intent(getActivity(), ChatActivity.class);
            intent.putExtra("name",friendList.get(position).getName());
            intent.putExtra("headPic", friendList.get(position).getImage());
            intent.putExtra("id",friendList.get(position).getId());
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
    public void initData(){
        Call<Result<List<Friend>>> call = userService.getFriendList(1);
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
    public static void deleteFriend(int uid1,int itemId){
        UserService userService=ApiService.retrofit.create(UserService.class);
        Call<Result<String>> call = userService.deleteFriend(uid1, friendList.get(itemId).getId());
        call.enqueue(new Callback<Result<String>>() {
            @Override
            public void onResponse(Call<Result<String>> call, Response<Result<String>> response) {
                friendList.remove(itemId);
                listView.setAdapter(friendAdapter);
                Toast.makeText(MyApplication.getContext(), R.string.delete_success, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onFailure(Call<Result<String>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }
}
