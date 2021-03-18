package com.jsyh.project.utils;

import android.app.ActivityManager;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Stack;

/**
 * Created by liang on 2018/7/25.
 */

public class ActivityManagerUtil {
    private static Stack<AppCompatActivity> activityStack;
    private static ActivityManagerUtil instance;

    private ActivityManagerUtil() {
    }

    public static ActivityManagerUtil getAppManager() {
        if (instance == null) {
            synchronized (ActivityManagerUtil.class) {
                if (instance == null) {
                    instance = new ActivityManagerUtil();
                }
            }
        }
        return instance;
    }

    public void addActivity(AppCompatActivity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 获得当前Activity(栈顶Activity)
     *
     * @return
     */
    public AppCompatActivity currentActivity() {

        if (activityStack == null || activityStack.isEmpty()) {
            return null;
        }
        AppCompatActivity acitivity = activityStack.lastElement();
        return acitivity;
    }

    public AppCompatActivity findActivity(Class<?> cls) {
        AppCompatActivity activity = null;
        for (AppCompatActivity appCompatActivity : activityStack) {
            if (appCompatActivity.getClass().equals(cls)) {
                activity = appCompatActivity;
                break;
            }
        }
        return activity;
    }

    public void finishActivity() {
        AppCompatActivity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    public void finishActivity(AppCompatActivity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    public void finishActivity(Class<?> cls) {
        for (AppCompatActivity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    public void finishAllActivity() {
        if (activityStack==null)return;
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    public void AppExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}