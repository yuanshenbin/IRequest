package com.ysb.network;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.ysb.R;
import com.yuanshenbin.network.INetDialog;

/**
 * author : yuanshenbin
 * time   : 2018/2/7
 * desc   :
 */

public class NetDialogImpl implements INetDialog {

    private LayoutInflater inflater;
    private WindowManager.LayoutParams lp;
    private TextView loadtext;
    private Dialog mDialog;

    @Override
    public void init(Context context) {
        mDialog = new Dialog(context, R.style.MyLoadDialog);
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.widget_dialog, null);
        loadtext = (TextView) layout.findViewById(R.id.loading_text);
        mDialog.setContentView(layout);
        // 设置window属性
        lp = mDialog.getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.dimAmount = 0; // 去背景遮盖
        // lp.alpha = 1.0f;//透明效果
        mDialog.getWindow().setAttributes(lp);
    }

    @Override
    public void show() {
        if (mDialog != null && !isShowing()) {
            mDialog.show();
        }
    }

    @Override
    public void dismiss() {
        if (mDialog != null && isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override
    public boolean isShowing() {
        return mDialog.isShowing();
    }

    @Override
    public void setCancelable(boolean flag) {
        if (!flag) {
            mDialog.setCancelable(false);
            mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog,
                                     int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_SEARCH) {
                        dismiss();
                        return true;
                    } else {
                        return false;
                    }
                }
            });
        }
    }

    @Override
    public void setMessage(String... msg) {
        loadtext.setVisibility(View.VISIBLE);
        if (msg != null && msg.length != 0) {
            loadtext.setText(msg[0]);
        }
    }


}