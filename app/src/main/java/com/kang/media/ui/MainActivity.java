package com.kang.media.ui;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kang.media.R;
import com.kang.media.adapter.ViewPageAdapter;
import com.kang.media.data.ImageInfo;
import com.kang.media.fragment.EmptyFragment;
import com.kang.media.fragment.TabFragment;
import com.kang.media.fragment.VideoFragment;
import com.kang.media.pic.LocalPictureCursorAsync;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private LocalPictureCursorAsync localPictureCursorAsync = null;
    private TabLayout tl;
    private ViewPager vp;

    private static final int MOVE_COUNT = 4;//标签数目小于等于4不滑动
    private int nTabCount = 6;
    private String [] tabName = {"照片","视频"};
    private List<String> mTabs;
    private List<Fragment> mFragments;
    private List<ImageInfo> mImageList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initview();

        initTabs();
        loadPic();
    }

    private void loadPic() {
        localPictureCursorAsync = new LocalPictureCursorAsync(this.getApplicationContext());
        localPictureCursorAsync.setOnLoadPhotoCursor(new LocalPictureCursorAsync.OnLoadPhotoCursor() {
            @Override
            public void onLoadPhotoSursorResult(ArrayList<Uri> uriArray, ArrayList<Long> origIdArray,ArrayList<String> pathArray, ArrayList<String> picNameArray) {
                Log.i(TAG, "uriArray--: " + uriArray.toString());
                Log.i(TAG, "origIdArray--: " + origIdArray.toString());
                Log.i(TAG, "pathArray--: " + pathArray.toString());
                Log.i(TAG, "picNameArray--: " + picNameArray.toString());

                mImageList = new ArrayList<ImageInfo>();
                for(int i = 0;i < origIdArray.size();i++){
                    mImageList.add(new ImageInfo(picNameArray.get(i),uriArray.get(i),pathArray.get(i),origIdArray.get(i)));
                }

                initViewPager();
            }
        });
        localPictureCursorAsync.execute();
    }

    private void initview() {
        setContentView(R.layout.activity_main);

        tl = (TabLayout) findViewById(R.id.tabLayout);
        vp = (ViewPager) findViewById(R.id.viewPager);
    }

    private void initTabs(){
        List temp = Arrays.asList(tabName);
        mTabs =  new ArrayList<>(temp);
        int i = 0;

        //MODE_FIXED标签栏不可滑动，各个标签会平分屏幕的宽度
        tl.setTabMode(nTabCount <= MOVE_COUNT ?TabLayout.MODE_FIXED : TabLayout.MODE_SCROLLABLE);
        //关联tabLayout和ViewPager,两者的选择和滑动状态会相互影响
        tl.setupWithViewPager(vp);

        while(i < nTabCount){
            mTabs.add("" +  i);
            tl.addTab(tl.newTab());
            i++;
        }

        for (i = 0; i < mTabs.size(); i++) {
            TabLayout.Tab itemTab = tl.getTabAt(i);
            if (itemTab != null){
                Log.i(TAG,"itemTab != null" );
                itemTab.setCustomView(R.layout.tab_layout);
/*                TextView itemTv = (TextView) itemTab.getCustomView().findViewById(R.id.tabTextview);
                itemTv.setText(tabs.get(i));*/
            }
        }
        tl.getTabAt(0).getCustomView().setSelected(true);
        //tab之间的分割线
        LinearLayout linearLayout = (LinearLayout) tl.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.layout_divider_vertical));

    }

    private void initViewPager(){
        mFragments = new ArrayList<>();
        Log.i(TAG,"tab size: " +mTabs.size());
        Log.i(TAG, "mImageList: " + mImageList.size());

        mFragments.add(new TabFragment(mImageList, mTabs.get(0)));
        mFragments.add(new VideoFragment("video"));
        for (int i = 0; i < nTabCount - tabName.length; i++) {
            mFragments.add(new EmptyFragment("empty"));
        }
        vp.setAdapter(new ViewPageAdapter(getSupportFragmentManager(), mFragments, mTabs));
    }
}
