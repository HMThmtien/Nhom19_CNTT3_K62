package com.app.qlns.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.app.qlns.model.HistoryLogin;
import com.app.qlns.R;

public class HistoryLoginAdapter extends RecyclerView.Adapter<HistoryLoginAdapter.ViewHolder> {

    private Context context;
    private List<HistoryLogin> historyLoginList;

    public HistoryLoginAdapter(Context context, List<HistoryLogin> historyLoginList) {
        this.context = context;
        this.historyLoginList = historyLoginList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.staff_history_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoryLogin historyLogin = historyLoginList.get(position);
        Log.d("HistoryLoginAdapter", "onBindViewHolder: " + historyLogin.getUsername() + " " + historyLogin.getTime());
        DateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String outputDateStr = "";
        // Định dạng của chuỗi đầu ra
        DateFormat outputDateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault());
        try {
            Date date = inputDateFormat.parse(historyLogin.getTime());
            outputDateStr = outputDateFormat.format(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        holder.tvStaffName.setText(historyLogin.getUsername());
        holder.tvTimeLogin.setText(outputDateStr);
    }

    @Override
    public int getItemCount() {
        return historyLoginList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvStaffName;
        TextView tvTimeLogin;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStaffName = itemView.findViewById(R.id.tvStaffName);
            tvTimeLogin = itemView.findViewById(R.id.tvTimeLogin);
        }
    }
}
