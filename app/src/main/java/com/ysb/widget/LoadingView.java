package com.ysb.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Jacky on 2016/10/31.
 * 帆布动画
 * 6.0帧布动画加载到ProgressBar 无效
 */
public class LoadingView extends ImageView {
    private AnimationDrawable mAnimationDrawable;
    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mAnimationDrawable = (AnimationDrawable) this.getBackground();
        mAnimationDrawable.start();
    }

    @Override
    public void setVisibility(int v) {
        super.setVisibility(v);
        if (v == GONE || v == INVISIBLE) {
            stopAnimation();
        } else {
            startAnimation();
        }
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == GONE || visibility == INVISIBLE) {
            stopAnimation();
        } else {
            startAnimation();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnimation();
    }


    private void stopAnimation() {
        if (mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }

    }

    private void startAnimation() {
        if (!mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }
    }
}
