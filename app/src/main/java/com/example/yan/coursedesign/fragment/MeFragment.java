package com.example.yan.coursedesign.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import com.example.yan.coursedesign.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MeFragment extends Fragment {
    GridView grid;
    ImageView imageView;
    int[] imageIds = new int[]
            {
                    R.mipmap.people1 , R.mipmap.iu1 , R.mipmap.people2
                    , R.mipmap.iu1, R.mipmap.pic1, R.mipmap.people3
                    , R.mipmap.pic1, R.mipmap.people4, R.mipmap.iu1
            };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_me, container, false);
        List<Map<String, Object>> listItems =
                new ArrayList<Map<String, Object>>();
        for (int i = 0; i < imageIds.length; i++)
        {
            Map<String, Object> listItem = new HashMap<>();
            listItem.put("image", imageIds[i]);
            listItems.add(listItem);
        }
        imageView = (ImageView) view.findViewById(R.id.imageView);
        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), listItems, R.layout.gridview_item,
                new String[] { "image" },
                new int[] { R.id.image});
        grid = (GridView) view.findViewById(R.id.gridview);
        grid.setAdapter(simpleAdapter);
        return view;
    }
}
