package com.example.yan.coursedesign.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yan.coursedesign.R;
import com.example.yan.coursedesign.bean.Result;
import com.example.yan.coursedesign.bean.Trend;
import com.example.yan.coursedesign.service.ApiService;
import com.example.yan.coursedesign.service.TrendService;
import com.example.yan.coursedesign.util.GildeHelp;
import com.example.yan.coursedesign.util.MyApplication;
import com.example.yan.coursedesign.util.UserInfo;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        final Trend trend=getItem(position);
        View view = null;
        ViewHolder viewHolder;
        if(convertView==null){
            view=LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.name = view.findViewById (R.id.name);
            viewHolder.headPic = view.findViewById (R.id.headPic);
            viewHolder.imgcontent = view.findViewById (R.id.nineGrid);
            viewHolder.good = view.findViewById (R.id.like);
            viewHolder.more=view.findViewById(R.id.more);
            view.setTag(viewHolder);
        }else{
            view=convertView;
            viewHolder= (ViewHolder) view.getTag();
        }
        TextView name=viewHolder.name;
        ImageView  headPic=viewHolder.headPic;
        NineGridView imgContent=viewHolder.imgcontent;
        ArrayList<ImageInfo> imageInfos = new ArrayList<>();
        ImageButton more=viewHolder.more;
        more.setOnClickListener(view1 -> doOnClick(view1,trend));

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
        ImageButton more;
    }
    public void doOnClick(View v ,Trend trend) {
        PopupMenu popup = new PopupMenu(getContext(), v);//第二个参数是绑定的那个view
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.main, popup.getMenu());
        popup.setOnMenuItemClickListener(menuItem -> {
            doOnMenuItemClick(menuItem,trend);
            return false;
        });
        popup.show();

    }

    public boolean doOnMenuItemClick(MenuItem item,Trend trend) {
        // TODO Auto-generated method stub
        if (item.getItemId() == R.id.delete) {
            if(trend.getName().equals(UserInfo.name))
                deleteTrend(trend);
            else
                Toast.makeText(MyApplication.getContext(), R.string.no_authority, Toast.LENGTH_SHORT)
                        .show();

        }
        return false;
    }
    public void deleteTrend(Trend trend){
        TrendService trendService= ApiService.retrofit.create(TrendService.class);
        Call<Result<String>> call = trendService.deleteTrend(trend.getId());
        call.enqueue(new Callback<Result<String>>() {
            @Override
            public void onResponse(Call<Result<String>> call, Response<Result<String>> response) {
                remove(trend);
                Toast.makeText(MyApplication.getContext(), R.string.delete_success, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onFailure(Call<Result<String>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
