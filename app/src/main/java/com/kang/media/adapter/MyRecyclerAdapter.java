package com.kang.media.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kang.media.R;
import com.kang.media.data.ImageInfo;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyHolder> {

    private final String TAG = MyRecyclerAdapter.class.getSimpleName();
    private Context mContext;
    private List<ImageInfo> mImageList;

    public MyRecyclerAdapter(Context mContext, List<ImageInfo> mImageList) {
        this.mContext = mContext;
        this.mImageList = mImageList;
        Log.i(TAG, "MyRecyclerAdapter : " + mImageList.size());
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycle_item, null);
        MyHolder holder = new MyHolder(view);
        Log.i(TAG ,"onCreateViewHolder");

        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        ImageInfo imageInfo = mImageList.get(position);
//        holder.imageView.setImageURI(imageInfo.getUri());
        Log.i(TAG, "imageInfo.getUri(): " + imageInfo.getUri());
        holder.textView.setText(imageInfo.getName());

        Glide.with(mContext)//glide加载图片
                .load(imageInfo.getUri())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;

        public MyHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.pic_iamge);
            textView = view.findViewById(R.id.pic_name);
        }
    }
}
