package com.app.qlns.ui.home;

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

import com.app.qlns.R;
import com.app.qlns.adapter.HistoryLoginAdapter;
import com.app.qlns.databinding.FragmentHomeBinding;
import com.app.qlns.db.ProductDAO;
import com.app.qlns.db.StaffDAO;
import com.app.qlns.db.UserDAO;
import com.app.qlns.model.HistoryLogin;
import com.app.qlns.model.Product;
import com.app.qlns.model.Staff;

public class HomeFragment extends Fragment {
    List<HistoryLogin> historyLoginList = new ArrayList<>();
    UserDAO userDAO;
    List<Staff> listStaff = new ArrayList<>();
    List<Product> listProduct = new ArrayList<>();
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userDAO = new UserDAO(getContext());
        createRecyclerView();

        StaffDAO staffDAO = new StaffDAO(this.getContext());
        ProductDAO productDAO = new ProductDAO(this.getContext());
        listStaff = staffDAO.getAllStaff();
        listProduct = productDAO.getAllProduct();
        binding.tvStaffCount.setText(listStaff.size()+"");
        binding.tvProductCount.setText(listProduct.size()+"");
    }

    void createRecyclerView() {
        historyLoginList = userDAO.getAllHistoryLogin();
        Collections.reverse(historyLoginList);
        binding.rvStaffHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvStaffHistory.setAdapter(new HistoryLoginAdapter(getContext(), historyLoginList));
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}