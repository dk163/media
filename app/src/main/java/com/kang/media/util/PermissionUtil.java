package com.kang.media.util;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.support.design.widget.Snackbar;

import com.kang.media.permission.PermissionListener;
import com.kang.media.permission.PermissionManager;

/**
 * Created by kang on 2017/8/18.
 */

public class PermissionUtil {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_PHONE_STATE"};
    private static PermissionManager helper;


    public static void verifyStoragePermissions(final Activity activity) {

        try {
            //检测是否有写的权限
//            int permission = ActivityCompat.checkSelfPermission(activity,
//                    "android.permission.ACCESS_FINE_LOCATION");
//            if (permission != PackageManager.PERMISSION_GRANTED) {
//                // 没有写的权限，去申请写的权限，会弹出对话框
//                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
//            }

            helper = PermissionManager.with(activity)
                    //添加权限请求码
                    .addRequestCode(REQUEST_EXTERNAL_STORAGE)
                    //设置权限，可以添加多个权限
                    .permissions(PERMISSIONS_STORAGE)
                    //设置权限监听器
                    .setPermissionsListener(new PermissionListener() {

                        @Override
                        public void onGranted() {
                            //当权限被授予时调用
                            //Toast.makeText(activity.getApplicationContext(), "Permission granted",Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onDenied() {
                            //用户拒绝该权限时调用
                            Toast.makeText(activity, "Permission denied",Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onShowRationale(String[] permissions) {
                            //当用户拒绝某权限时并点击`不再提醒`的按钮时，下次应用再请求该权限时，需要给出合适的响应（比如,给个展示对话框来解释应用为什么需要该权限）
//                            Snackbar.make(view, "需要权限", Snackbar.LENGTH_INDEFINITE)
//                                    .setAction("ok", new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            //必须调用该`setIsPositive(true)`方法
//                                            helper.setIsPositive(true);
//                                            helper.request();
//                                        }
//                                    }).show();
                        }
                    })
                    //请求权限
                    .request();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
