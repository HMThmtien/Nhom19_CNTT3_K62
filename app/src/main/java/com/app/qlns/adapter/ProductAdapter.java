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
import com.app.qlns.ProductDialog;
import com.app.qlns.R;
import com.app.qlns.listener.ModifyItem;
import com.app.qlns.listener.UpdateList;
import com.app.qlns.model.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private Context context;
    private List<Product> products;
    ModifyItem modifyItem;

    public ProductAdapter(Context context, List<Product> products, ModifyItem modifyItem) {
        this.context = context;
        this.products = products;
        this.modifyItem = modifyItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prodcut_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Product product = products.get(position);
        holder.tvProductName.setText(product.getName());
        holder.tvPrice.setText(String.valueOf(product.getPrice()));
        holder.tvStock.setText(String.valueOf(product.getStock()));
        // delete product
        holder.btnDelete.setOnClickListener(v -> {
            ConfirmDialog confirmDialog = new ConfirmDialog(context);
            confirmDialog.show();
            confirmDialog.btnYes.setOnClickListener(v1 -> {
               modifyItem.onDelete(position);
                confirmDialog.dismiss();
            });
        });
        holder.btnEdit.setOnClickListener(v -> {
            ProductDialog productDialog = new ProductDialog(context, product, new UpdateList() {
                @Override
                public void onUpdate() {
                    modifyItem.onEdit(position);
                }
            });
            productDialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvProductName, tvPrice, tvStock;
        ImageButton btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDel);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvPrice = itemView.findViewById(R.id.tvProductPrice);
            tvStock = itemView.findViewById(R.id.tvProductCount);
        }
    }
}
