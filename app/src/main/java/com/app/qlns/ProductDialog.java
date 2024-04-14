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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.app.qlns.db.ProductDAO;
import com.app.qlns.listener.UpdateList;
import com.app.qlns.model.Product;

public class ProductDialog extends Dialog {
    Product product;
    public EditText edtName, edtPrice, edtDescription, edtStock;
    public TextView tvTitle;
    public Button btnAdd, btnCancel;
    ProductDAO productDAO;
    UpdateList updateList;

    public ProductDialog(@NonNull Context context, UpdateList updateList) {
        super(context);
        this.updateList = updateList;
    }

    public ProductDialog(@NonNull Context context, Product product, UpdateList updateList) {
        super(context);
        this.product = product;
        this.updateList = updateList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        productDAO = new ProductDAO(this.getContext());
        setContentView(R.layout.product_dialog);
        customWindow();
        super.onCreate(savedInstanceState);
        edtName = findViewById(R.id.edtProductName);
        edtPrice = findViewById(R.id.edtPrice);
        edtDescription = findViewById(R.id.edtDescription);
        edtStock = findViewById(R.id.edtStock);
        tvTitle = findViewById(R.id.tvTitle);
        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);
        setClick();
        if (product != null) {
            tvTitle.setText("Cập nhật sản phẩm"+product.getName());
            edtName.setText(product.getName());
            edtPrice.setText(String.valueOf(product.getPrice()));
            edtDescription.setText(product.getDescription());
            edtStock.setText(String.valueOf(product.getStock()));
        }
        btnAdd.setOnClickListener(v -> addProduct());
    }

    void addProduct() {
        if (validateForm()) {
            if (product == null) {
                product = new Product(edtName.getText().toString(), Integer.parseInt(edtPrice.getText().toString()), edtDescription.getText().toString(), Integer.parseInt(edtStock.getText().toString()));
                if (productDAO.addProduct(product)) {
                    Toast.makeText(this.getContext(), "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                    dismiss();
                    updateList.onUpdate();
                } else {
                    Toast.makeText(this.getContext(), "Thêm sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                }
            } else {
                product.setName(edtName.getText().toString());
                product.setPrice(Integer.parseInt(edtPrice.getText().toString()));
                product.setDescription(edtDescription.getText().toString());
                product.setStock(Integer.parseInt(edtStock.getText().toString()));
                if (productDAO.updateProduct(product.getId(), product.getName(), product.getPrice(), product.getDescription(), product.getStock())) {
                    Toast.makeText(this.getContext(), "Cập nhật sản phẩm thành công", Toast.LENGTH_SHORT).show();
                    updateList.onUpdate();
                    dismiss();
                } else {
                    Toast.makeText(this.getContext(), "Cập nhật sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public boolean validateForm() {
        if (edtName.getText().toString().isEmpty()) {
            Toast.makeText(this.getContext(), "Vui lòng điền tên sản phẩm ", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edtPrice.getText().toString().isEmpty()) {
            Toast.makeText(this.getContext(), "Vui lòng điền giá sản phẩm ", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edtDescription.getText().toString().isEmpty()) {
            Toast.makeText(this.getContext(), "Vui lòng điền mô tả sản phẩm ", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edtStock.getText().toString().isEmpty()) {
            Toast.makeText(this.getContext(), "Vui lòng điền số lượng sản phẩm ", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    void setClick() {
        btnCancel.setOnClickListener(v -> dismiss());
    }

    void customWindow() {
        this.setCanceledOnTouchOutside(true);
        Window window = this.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}