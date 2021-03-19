package com.jsyh.project.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import com.jsyh.project.utils.MyUtils;

public abstract class VBindingActivity<VB extends ViewBinding> extends AppCompatActivity {
    public VB binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (binding==null){
            binding = getBinding();
        }
        setContentView(binding.getRoot());
        initData();
//        AppManager.getInstance().pushActivity(this); //入栈
    }

    public abstract VB getBinding();

    public Context getContext() {
        return getBaseContext();
    }

    public void clickListener(int[] arr, View.OnClickListener listener) {
        for (int i = 0; i < arr.length; i++) {
            this.findViewById(arr[i]).setOnClickListener(listener);
        }
    }

    public abstract void initData();

    public void showToast(String str) {
        MyUtils.toast(str);
    }

    /**
     * 跳转到其他 Activity
     */
    public void startActivity(Class<? extends Activity> cls) {
        startActivity(new Intent(this, cls));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //退栈
//        AppManager.getInstance().popActivity(this);
    }
}
