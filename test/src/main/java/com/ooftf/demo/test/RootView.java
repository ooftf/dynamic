package com.ooftf.demo.test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.appbar.AppBarLayout;

public class RootView extends CoordinatorLayout {
    public RootView(@NonNull Context context) {
        super(context);
    }

    public RootView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RootView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    AppBarLayout appBarLayout = new AppBarLayout(getContext());
    {
        addView(appBarLayout);
    }
    void addHeader(View header){
        AppBarLayout.LayoutParams lp = new AppBarLayout.LayoutParams(AppBarLayout.LayoutParams.MATCH_PARENT,AppBarLayout.LayoutParams.WRAP_CONTENT);
        lp.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL);
        appBarLayout.addView(header,lp);
    }
    void addSticky(View sticky){
        AppBarLayout.LayoutParams lp = new AppBarLayout.LayoutParams(AppBarLayout.LayoutParams.MATCH_PARENT,AppBarLayout.LayoutParams.WRAP_CONTENT);
        lp.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_NO_SCROLL);
        appBarLayout.addView(sticky,lp);
    }
    void addBody(View body){
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        lp.setBehavior(new AppBarLayout.ScrollingViewBehavior());
        addView(body,lp);
    }
}
