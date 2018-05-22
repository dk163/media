package com.kang.media.pic;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;

public class LocalPictureCursorAsync extends AsyncTask {
    private final String TAG = LocalPictureCursorAsync.class.getSimpleName();
    private Context mContext;
    private ContentResolver mContentResolver;
    private final String DIR_CAMERA = "/storage/emulated/0/DCIM/Camera";
    private final String DIR_DOWNLOAD = "/storage/emulated/0/Download";
    private boolean mExitTasksEarly = false;//退出任务线程的标志位

    private ArrayList<Uri> uriArray = new ArrayList<Uri>();//存放图片URI
    private ArrayList<Long> origIdArray = new ArrayList<Long>();//存放图片ID
    private ArrayList<String> pathArray = new ArrayList<String>();//存放图片路径
    private ArrayList<String> picNameArray = new ArrayList<String>();//存放图片展示名字
    private OnLoadPhotoCursor onLoadPhotoCursor;//定义回调接口，获取解析到的数据

    public LocalPictureCursorAsync(Context m) {
        this.mContext = m;
        this.mContentResolver = mContext.getContentResolver();
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String project[] = {MediaStore.Images.Media._ID,MediaStore.Images.ImageColumns.DATA, MediaStore.Images.ImageColumns.DISPLAY_NAME};
        String where = null;
        String orderBy = MediaStore.Images.Media.DATE_ADDED + " desc";

        //final Cursor query (ContentResolver cr, Uri uri, String[] projection, String where, String orderBy)
        Cursor c  = MediaStore.Images.Media.query(mContentResolver, uri, project, where, orderBy);
        int columnIndex = c.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA);
        int columnIndex2 = c.getColumnIndexOrThrow(MediaStore.Images.ImageColumns._ID);
        int columnIndex3 = c.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DISPLAY_NAME);
        if((columnIndex == -1) || (columnIndex2 == -1)||(columnIndex == -1)){
            Log.e(TAG, "columnIndex is -1,is error");
            return null;
        }

        int i = 0;
        while(c.moveToNext() && i < c.getCount() && !mExitTasksEarly){
            long origId =  c.getLong(columnIndex2);
            String p = c.getString(columnIndex);
            if((p.contains(DIR_CAMERA)) || (p.contains(DIR_DOWNLOAD))){
                pathArray.add(p);

                uriArray.add(Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, origId + ""));
                origIdArray.add(origId);

                String displayName = c.getString(columnIndex3);
                picNameArray.add(displayName);
            }else {
                Log.i(TAG, "skip more dir, path: " + p);
            }
            c.moveToPosition(i);
            i++;
        }
        c.close();//关闭数据库

        if(mExitTasksEarly){
            uriArray = new ArrayList<Uri>();
            origIdArray = new ArrayList<Long>();
            picNameArray = new ArrayList<String>();
            pathArray = new ArrayList<String>();

        }
        return null;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        mExitTasksEarly = true;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        Log.i(TAG, "task is finishing");
        if (onLoadPhotoCursor != null && !mExitTasksEarly) {
            /**
             * 查询完成之后，设置回调接口中的数据，把数据传递到Activity中
             */
            onLoadPhotoCursor.onLoadPhotoSursorResult(uriArray, origIdArray, pathArray, picNameArray);
        }
    }

    /**
     * 设置任务状态标识
     * @param mExitTasksEarly
     */
    public void setmExitTasksEarly(boolean mExitTasksEarly) {
        this.mExitTasksEarly = mExitTasksEarly;
    }

    public void setOnLoadPhotoCursor(OnLoadPhotoCursor onLoadPhotoCursor) {
        this.onLoadPhotoCursor = onLoadPhotoCursor;
    }

    public interface OnLoadPhotoCursor {
        public void onLoadPhotoSursorResult(ArrayList<Uri> uriArray, ArrayList<Long> origIdArray, ArrayList<String> pathArray, ArrayList<String> picNameArray);
    }
}
