package com.example.yan.coursedesign.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yan.coursedesign.R;
import com.example.yan.coursedesign.bean.Trend;
import com.example.yan.coursedesign.util.GildeHelp;
import com.example.yan.coursedesign.util.MyImageloader;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ACM-Yan on 2018/3/15.
 */

public class TrendAdapter extends ArrayAdapter<Trend> {
    private int resourceId;
    public TrendAdapter(Context context, int resourceId, List<Trend> objects) {
        super(context, resourceId, objects);
        this.resourceId=resourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Trend trend=getItem(position);
        View view = null;
        ViewHolder viewHolder;
        if(convertView==null){
            view=LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.name = view.findViewById (R.id.name);
            viewHolder.headPic = view.findViewById (R.id.headPic);
            viewHolder.imgcontent = view.findViewById (R.id.nineGrid);
            viewHolder.good = view.findViewById (R.id.like);
            view.setTag(viewHolder);
        }else{
            view=convertView;
            viewHolder= (ViewHolder) view.getTag();
        }
        TextView name=viewHolder.name;
        ImageView  headPic=viewHolder.headPic;
        NineGridView imgContent=viewHolder.imgcontent;
        ArrayList<ImageInfo> imageInfos = new ArrayList<>();
        for (String s:trend.getImgContent()) {
            ImageInfo imageInfo=new ImageInfo();
            imageInfo.setBigImageUrl(s);
            imageInfo.setThumbnailUrl(s);
            imageInfos.add(imageInfo);
        }
        imgContent.setAdapter(new NineGridViewClickAdapter(getContext(),imageInfos));
        name.setText(trend.getName());
        final ImageButton good;
        good= viewHolder.good;
        good.setOnClickListener(v -> {
            if(good.isSelected()){
                good.setSelected(false);
            }else{
                good.setSelected(true);
            }
        });
        Glide.with(getContext()).load(trend.getHeadPic()).apply(GildeHelp.getOptions()).into(headPic);
        return view;
    }
    class ViewHolder {
        TextView name;
        ImageView headPic;
        NineGridView imgcontent;
        ImageButton good;
    }
}
