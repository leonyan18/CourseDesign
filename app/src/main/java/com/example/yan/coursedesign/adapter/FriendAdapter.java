package com.example.yan.coursedesign.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yan.coursedesign.R;
import com.example.yan.coursedesign.bean.Friend;
import com.example.yan.coursedesign.util.GildeHelp;

import java.util.List;

/**
 * Created by ACM-Yan on 2018/3/15.
 */

public class FriendAdapter extends ArrayAdapter<Friend> {
    private int resourceId;

    public FriendAdapter(Context context, int resourceId, List<Friend> objects) {
        super(context, resourceId, objects);
        this.resourceId=resourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Friend friend =getItem(position);
        View view = null;
        ViewHolder viewHolder;
        if(convertView==null){
            view=LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.name = view.findViewById (R.id.people_name);
            viewHolder.slabel = view.findViewById (R.id.label);
            viewHolder.imageView = view.findViewById (R.id.imgpeo);
            view.setTag(viewHolder);
        }else{
            view=convertView;
            viewHolder= (ViewHolder) view.getTag();
        }
        TextView name=viewHolder.name;
        TextView slabel=viewHolder.slabel;
        ImageView imageView=viewHolder.imageView;
        name.setText(friend.getName());
        Glide.with(getContext()).load(friend.getImage()).apply(GildeHelp.getOptions()).into(imageView);
        slabel.setText(friend.getSlabel());
        return view;
    }
    class ViewHolder {

        TextView name;
        TextView slabel;
        ImageView imageView;
    }
}
