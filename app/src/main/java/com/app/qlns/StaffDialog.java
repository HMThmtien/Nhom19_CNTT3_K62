package com.app.qlns;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.app.qlns.db.StaffDAO;
import com.app.qlns.listener.UpdateList;
import com.app.qlns.model.Staff;

public class StaffDialog extends Dialog {
    EditText edtName, edtPhone;
    Button btnCancel, btnAdd;
    Staff staff;
    UpdateList updateList;
    StaffDAO staffDAO;

    public StaffDialog(@NonNull Context context, UpdateList updateList) {
        super(context);
        staffDAO = new StaffDAO(context);
        this.updateList = updateList;
    }

    public StaffDialog(@NonNull Context context, Staff staff, UpdateList updateList) {
        super(context);
        this.staff = staff;
        staffDAO = new StaffDAO(context);
        this.updateList = updateList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.staff_dialog);
        customWindow();
        super.onCreate(savedInstanceState);
        edtName = findViewById(R.id.edtStaffName);
        edtPhone = findViewById(R.id.edtPhoneNumber);
        btnCancel = findViewById(R.id.btnCancel);
        btnAdd = findViewById(R.id.btnAdd);
        btnCancel.setOnClickListener(v -> {
            dismiss();
        });
        btnAdd.setOnClickListener(v -> {
            addStaff();
        });
        if(staff != null) {
            setStaff(staff);
            btnAdd.setText("Cập nhật");
            btnAdd.setOnClickListener(v -> {
                updateStaff();
            });
        }
    }
    void setStaff(Staff staff) {
        edtName.setText(staff.getName());
        edtPhone.setText(staff.getPhone());
    }
    void updateStaff() {
        if (validate()) {
            staff.setName(edtName.getText().toString());
            staff.setPhone(edtPhone.getText().toString());
            staffDAO.updateStaff(staff.getId(), staff.getName(), staff.getPhone());
            dismiss();
            updateList.onUpdate();
        }
    }
    void addStaff() {
        if (validate()) {
            staffDAO.addStaff(new Staff(edtName.getText().toString(), edtPhone.getText().toString()));
            dismiss();
            updateList.onUpdate();
        }
    }

    boolean validate() {
        if (edtName.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Tên không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edtPhone.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Số điện thoại không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    void customWindow() {
        this.setCanceledOnTouchOutside(true);
        Window window = this.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}