package com.jsyh.project.utils;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.jsyh.project.R;
import com.jsyh.project.inteface.TimePickCallBack;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MyUtils {
    INSTANCE;
    public static final String TAG = MyUtils.class.getSimpleName();
    private static Toast mtoast;
    private static Context mContext;

    public static void toast(String msg) {
        showToast(msg);
    }

    private static void showToast(String msg) {
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

    public static void e(String logValue) {
        Log.e(TAG, logValue);
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

    /**
     * 手机号验证
     * @param phoneNumber
     * @return
     */
    public static boolean dial(final String phoneNumber) {
        Pattern p = Pattern.compile("^(1[3-9])\\d{9}$");
        Matcher m = p.matcher(phoneNumber);
        return m.matches();
    }

    public void setTimePickCallBack(TimePickCallBack timePickCallBack) {
        this.timePickCallBack = timePickCallBack;
    }

    private TimePickCallBack timePickCallBack;
    private Dialog mDialog;

    public void initTimePicker(Context context, final TextView textView) {
        Calendar startDate = Calendar.getInstance();
        startDate.set(1949, 00, 01);//开始日期
        TimePickerView pvTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(java.util.Date date, View v) {
                String str = getTime(date);
                textView.setText(str);
                if (timePickCallBack != null) {
                    timePickCallBack.back(str);
                }
            }
        }).setType(new boolean[]{true, true, true, false, false, false})
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")
                .setTitleText("选择时间")
                .setLabel("", "", "", "", "", "")//默认设置为年月日时分秒
                .setDate(Calendar.getInstance())
                .setRangDate(startDate, Calendar.getInstance())
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .build();
        Button btnSubmit = pvTime.getDialogContainerLayout().getRootView().findViewById(R.id.btnSubmit);
        Button btnCancel = pvTime.getDialogContainerLayout().getRootView().findViewById(R.id.btnCancel);
        btnSubmit.setBackground(null);
        btnCancel.setBackground(null);
        mDialog = pvTime.getDialog();
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);
            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);
            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }
    }

    public String getTime(java.util.Date date) {//可根据需要自行截取数据显示		 HH:mm:ss
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public MyUtils showDialog() {
        if (mDialog != null) {
            mDialog.show();
        }
        return this;
    }



}
