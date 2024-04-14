package com.app.qlns.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.app.qlns.ConfirmDialog;
import com.app.qlns.R;
import com.app.qlns.StaffDialog;
import com.app.qlns.listener.ModifyItem;
import com.app.qlns.listener.UpdateList;
import com.app.qlns.model.Staff;

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.ViewHolder> {

    private Context context;
    private List<Staff> staffs;
    ModifyItem modifyItem;

    public StaffAdapter(Context context, List<Staff> staffs, ModifyItem modifyItem) {
        this.context = context;
        this.staffs = staffs;
        this.modifyItem = modifyItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.staff_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Staff staff = staffs.get(position);
        holder.tvName.setText(staff.getName());
        holder.tvPhoneNumber.setText(staff.getPhone());
        holder.btnDelete.setOnClickListener(v -> {
            ConfirmDialog confirmDialog = new ConfirmDialog(context);
            confirmDialog.create();
            confirmDialog.getTvName().setText(staff.getName());
            confirmDialog.getTvTitle().setText("Xác nhận xóa nhân viên");
            confirmDialog.show();
            confirmDialog.btnYes.setOnClickListener(v1 -> {
               modifyItem.onDelete(position);
                confirmDialog.dismiss();
            });
        });
        // edit staff
        holder.btnEdit.setOnClickListener(v -> {
            StaffDialog dialog = new StaffDialog(context, staff, new UpdateList() {
                @Override
                public void onUpdate() {
                    modifyItem.onEdit(position);
                }
            });
            dialog.show();
        });

    }

    @Override
    public int getItemCount() {
        return staffs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName,tvPhoneNumber;
        ImageButton btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPhoneNumber = itemView.findViewById(R.id.tvPhoneNumber);
            btnDelete = itemView.findViewById(R.id.btnDel);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }
    }
}
