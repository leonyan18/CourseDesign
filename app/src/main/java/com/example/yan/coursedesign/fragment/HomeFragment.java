package com.example.yan.coursedesign.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.example.yan.coursedesign.bean.Trend;
import com.example.yan.coursedesign.adapter.TrendAdapter;
import com.example.yan.coursedesign.R;
import com.example.yan.coursedesign.service.ApiService;
import com.example.yan.coursedesign.bean.Result;
import com.example.yan.coursedesign.service.TrendService;
import com.example.yan.coursedesign.util.GifSizeFilter;
import com.example.yan.coursedesign.util.Glide4Engine;
import com.example.yan.coursedesign.util.MyApplication;
import com.example.yan.coursedesign.util.MyImageloader;
import com.lzy.ninegrid.NineGridView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zhihu.matisse.listener.OnCheckedListener;
import com.zhihu.matisse.listener.OnSelectedListener;


import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private List<Trend> trends;
    private TrendAdapter trendAdapter;
    private MaterialRefreshLayout materialRefreshLayout;
    private TrendService trendService;
    private ListView homeList;
    private static final String TAG = "HomeFragment";
    public static final int REQUEST_CODE_CHOOSE = 23;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        trendService = ApiService.retrofit.create(TrendService.class);
        NineGridView.setImageLoader(new MyImageloader());
        homeList = view.findViewById(R.id.homeList);
        ImageButton camera = view.findViewById(R.id.camera);
        trends = new ArrayList<Trend>();
        ImageButton upload = view.findViewById(R.id.upload);
        Trend trend = new Trend();
        trend.setHeadPic("http://www.lancedai.cn/people3.jpg");
        List<String> strings = new ArrayList<>();
        strings.add("http://www.lancedai.cn/iu2.jpg");
        strings.add("http://www.lancedai.cn/iu1.jpg");
        trend.setImgContent(strings);
        trend.setName("yan");
        trends.add(trend);
        initData();
        trendAdapter = new TrendAdapter(getActivity(), R.layout.trend_item, trends);
        materialRefreshLayout = view.findViewById(R.id.refresh);
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                Toast.makeText(MyApplication.getContext(), "更新成功!", Toast.LENGTH_SHORT).show();
                initData();
            }
        });
        homeList.setAdapter(trendAdapter);
        camera.setOnClickListener(view1 -> {
            Toast.makeText(MyApplication.getContext(), "添加成功!", Toast.LENGTH_LONG).show();
            homeList.setAdapter(trendAdapter);
        });
        upload.setOnClickListener(view12 -> {
            RxPermissions rxPermissions = new RxPermissions(getActivity());
            rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(new Observer<Boolean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Boolean aBoolean) {
                            if (aBoolean) {
                                Matisse.from(getActivity())
                                        .choose(MimeType.ofAll(), false)
                                        .countable(true)
                                        .capture(true)
                                        .captureStrategy(
                                                new CaptureStrategy(true, "com.example.yan.coursedesign.fileprovider", "test"))
                                        .maxSelectable(9)
                                        .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                                        .gridExpectedSize(
                                                getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                                        .thumbnailScale(0.85f)
//                                            .imageEngine(new GlideEngine())  // for glide-V3
                                        .imageEngine(new Glide4Engine())    // for glide-V4
                                        .setOnSelectedListener(new OnSelectedListener() {
                                            @Override
                                            public void onSelected(
                                                    @NonNull List<Uri> uriList, @NonNull List<String> pathList) {
                                                // DO SOMETHING IMMEDIATELY HERE
                                                Log.e("onSelected", "onSelected: pathList=" + pathList);

                                            }
                                        })
                                        .originalEnable(true)
                                        .maxOriginalSize(10)
                                        .autoHideToolbarOnSingleTap(true)
                                        .setOnCheckedListener(new OnCheckedListener() {
                                            @Override
                                            public void onCheck(boolean isChecked) {
                                                // DO SOMETHING IMMEDIATELY HERE
                                                Log.e("isChecked", "onCheck: isChecked=" + isChecked);
                                            }
                                        })
                                        .forResult(REQUEST_CODE_CHOOSE);
                            } else {
                                Toast.makeText(getActivity(), R.string.permission_request_denied, Toast.LENGTH_LONG)
                                        .show();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        });
        return view;
    }

    private void initData() {
        Call<Result<List<Trend>>> call = trendService.getTrends(1);
        call.enqueue(new Callback<Result<List<Trend>>>() {
            @Override
            public void onResponse(Call<Result<List<Trend>>> call, Response<Result<List<Trend>>> response) {
                trends.clear();
                List<Trend> body = response.body().getData();
                for (Trend f : body) {
                    trends.add(f);
                    Log.d(TAG, "onResponse: " + f);
                }
                materialRefreshLayout.finishRefresh();
            }

            @Override
            public void onFailure(Call<Result<List<Trend>>> call, Throwable t) {
                t.printStackTrace();
                Log.d(TAG, "onResponse: getTrends ERROR");
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == getActivity().RESULT_OK) {
            Log.e("OnActivityResult ", String.valueOf(Matisse.obtainOriginalState(data)));
        }
    }
}
