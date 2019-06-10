package com.example.yan.coursedesign.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yan.coursedesign.R;
import com.example.yan.coursedesign.bean.Friend;
import com.example.yan.coursedesign.bean.Result;
import com.example.yan.coursedesign.service.ApiService;
import com.example.yan.coursedesign.service.UserService;
import com.example.yan.coursedesign.util.GildeHelp;
import com.example.yan.coursedesign.util.MyApplication;
import com.example.yan.coursedesign.util.UserInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by ACM-Yan on 2018/3/15.
 */

public class FriendAdapter extends ArrayAdapter<Friend> {
    private int resourceId;
    private static UserService userService = ApiService.retrofit.create(UserService.class);

    public FriendAdapter(Context context, int resourceId, List<Friend> objects) {
        super(context, resourceId, objects);
        this.resourceId = resourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Friend friend = getItem(position);
        View view = null;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name = view.findViewById(R.id.people_name);
            viewHolder.slabel = view.findViewById(R.id.label);
            viewHolder.imageView = view.findViewById(R.id.imgpeo);
            if (resourceId == R.layout.people_item)
                viewHolder.add = view.findViewById(R.id.add);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        TextView name = viewHolder.name;
        TextView slabel = viewHolder.slabel;
        ImageView imageView = viewHolder.imageView;
        name.setText(friend.getName());
        Glide.with(getContext()).load(friend.getImage()).apply(GildeHelp.getOptions()).into(imageView);
        slabel.setText(friend.getSlabel());
        if (resourceId == R.layout.people_item) {
            viewHolder.add.setOnClickListener(view1 -> addFriend(friend.getId(), UserInfo.userId, friend));
        }
        return view;
    }

    class ViewHolder {
        ImageButton add;
        TextView name;
        TextView slabel;
        ImageView imageView;
    }

    private void addFriend(int uid1, int uid2, Friend friend) {
        Call<Result<String>> call = userService.addFriend(uid1, uid2);
        call.enqueue(new Callback<Result<String>>() {
            @Override
            public void onResponse(Call<Result<String>> call, Response<Result<String>> response) {
                Toast.makeText(MyApplication.getContext(), R.string.add_success, Toast.LENGTH_SHORT)
                        .show();
                remove(friend);
            }

            @Override
            public void onFailure(Call<Result<String>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
