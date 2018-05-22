package com.kang.media.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kang.media.R;
import com.kang.media.adapter.MyRecyclerAdapter;
import com.kang.media.data.ImageInfo;
import com.kang.media.util.LogUtil;

import java.util.List;

public class TabFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private  String ARG_PARAM1 = "param1";
    private  String ARG_PARAM2 = "";
    private final String TAG = TabFragment.class.getSimpleName();

    // TODO: Rename and change types of parameters
    private List<ImageInfo> imageList;
    private String mParam2;

    private RecyclerView mRecyclerView;

    public TabFragment() {
    }

    @SuppressLint("ValidFragment")
    public TabFragment(List<ImageInfo> imageList, String param2) {
        // Required empty public constructor
        this.imageList = imageList;
        LogUtil.i(TAG, "TabFragment : " + imageList.size());
        ARG_PARAM2 = param2;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the tab_layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
//        TextView tx = ((TextView)view.findViewById(R.id.tabFragment));
//        LogUtil.i(TAG, "tx: "+ARG_PARAM2 );
//        tx.setText(ARG_PARAM2);

        initView(view);


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void initView(View view){
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        //设置layoutManager,参数含义显而易见，2就是2列，第二个参数是垂直方向
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setAdapter(new MyRecyclerAdapter(getActivity(),imageList));
    }
}
