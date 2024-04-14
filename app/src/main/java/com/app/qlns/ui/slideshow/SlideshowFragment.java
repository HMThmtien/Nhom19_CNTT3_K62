package com.app.qlns.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.app.qlns.ProductDialog;
import com.app.qlns.adapter.ProductAdapter;
import com.app.qlns.databinding.FragmentGalleryBinding;
import com.app.qlns.databinding.FragmentSlideshowBinding;
import com.app.qlns.db.ProductDAO;
import com.app.qlns.listener.ModifyItem;
import com.app.qlns.listener.UpdateList;

import com.app.qlns.model.Product;

public class SlideshowFragment extends Fragment implements ModifyItem, UpdateList {
    ProductDialog dialog;
    ProductAdapter adapter;
    private FragmentSlideshowBinding binding;
    ProductDAO productDAO;
    List<Product> listProduct = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productDAO = new ProductDAO(getContext());
        createRecyclerView();
        dialog = new ProductDialog(getContext(), this);
        binding.btnAddProduct.setOnClickListener(v -> {
            dialog = new ProductDialog(getContext(), this);
            dialog.show();
        });

    }

    void createRecyclerView() {
        listProduct = productDAO.getAllProduct();
        Collections.reverse(listProduct);
        // code
        adapter = new ProductAdapter(this.getContext(), listProduct, this);
        binding.tvProduct.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        binding.tvProduct.setAdapter(adapter);
    }

    @Override
    public void onEdit(int position) {
        createRecyclerView();
    }

    @Override
    public void onDelete(int position) {
        productDAO.deleteProduct(listProduct.get(position).getId());
        createRecyclerView();
    }

    @Override
    public void onUpdate() {
        createRecyclerView();
    }
}