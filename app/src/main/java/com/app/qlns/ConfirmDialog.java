package com.app.qlns;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class ConfirmDialog extends Dialog {
    public TextView tvTitle, tvName;
    public Button btnYes, btnNo;

    public ConfirmDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.comfirm_delete);
        customWindow();
        super.onCreate(savedInstanceState);
        tvTitle = findViewById(R.id.tvConfirmDelete);
        tvName = findViewById(R.id.tvName);
        btnYes = findViewById(R.id.btnDel);
        btnNo = findViewById(R.id.btnCancel);
        btnNo.setOnClickListener(v -> {
            dismiss();
        });
    }

    public Button getBtnNo() {
        return btnNo;
    }

    public TextView getTvName() {
        return tvName;
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    void customWindow() {
        this.setCanceledOnTouchOutside(true);
        Window window = this.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}