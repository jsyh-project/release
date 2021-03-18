package com.jsyh.project.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

public enum MyUtils {
    INSTANCE;
    private static final String TAG = MyUtils.class.getSimpleName();
    private static Toast mtoast;
    private static Context mContext;

    public static void toast(String msg) {
        showToast(msg);
    }

    public static void showToast(String msg) {
        if (mtoast == null) {
            mtoast = Single.toast;
        }
        mtoast.setText(msg);
        mtoast.show();

    }

    public void init(Context context){
        mContext = context;
    }

    private static class Single {
        private static final Toast toast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);

    }

    public static boolean checkNull(String str) {
        if (TextUtils.isEmpty(str)) return true;
        return false;
    }

    public static void e(String logFilter, String logValue) {
        Log.e(logFilter, logValue);
    }

    /**
     * dp转px
     *
     * @param context 上下文
     * @param dpValue dp值
     * @return px值
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    /**
     * px转dp
     *
     * @param context 上下文
     * @param pxValue px值
     * @return dp值
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }



}
