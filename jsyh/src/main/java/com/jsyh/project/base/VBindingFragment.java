package com.jsyh.project.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewbinding.ViewBinding;

public abstract class VBindingFragment<VB extends ViewBinding> extends Fragment {
    protected FragmentActivity mActivity;
    protected boolean mIsFirstVisible = true;
    protected VB binding;//全局变量？

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            onVisible();
        } else {
            onInVisible();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            onVisible();
        } else {
            onInVisible();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (FragmentActivity) context;
    }

    public abstract void initData();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        if (binding==null){
            binding = getBinding(inflater);
        }
        return binding.getRoot();
    }

    protected abstract VB getBinding(LayoutInflater inflater);

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        handleIntent();
        needLazy();
        initData();
    }

    public void clickListener(View view, int[] arr, View.OnClickListener listener) {
        for (int i = 0; i < arr.length; i++) {
            view.findViewById(arr[i]).setOnClickListener(listener);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.mActivity = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mActivity = null;
    }

    protected void handleIntent() {
    }

    private void needLazy() {
        boolean isVis = isHidden() || getUserVisibleHint();
        if (isVis && mIsFirstVisible) {
            lazyLoad();
            mIsFirstVisible = false;
        }
    }

    /**
     * 当界面可见时的操作
     */
    protected void onVisible() {
        if (mIsFirstVisible && isResumed()) {
            lazyLoad();
            mIsFirstVisible = false;
        }
    }

    /**
     * 当界面不可见时的操作
     */
    protected void onInVisible() {

    }

    /**
     * 数据懒加载
     */
    protected void lazyLoad() {

    }

    /**
     * 跳转到其他 Activity 并销毁当前 Activity
     *
     * @param cls 目标Activity的Class
     */
    public void startActivityFinish(Class<? extends Activity> cls) {
        startActivity(cls);
        mActivity.finish();
    }

    /**
     * 跳转到其他Activity
     *
     * @param cls 目标Activity的Class
     */
    public void startActivity(Class<? extends Activity> cls) {
        startActivity(new Intent(getContext(), cls));
    }

}
