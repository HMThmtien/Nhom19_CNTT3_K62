package com.app.qlns.ui.gallery;

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

import com.app.qlns.StaffDialog;
import com.app.qlns.adapter.StaffAdapter;
import com.app.qlns.databinding.FragmentGalleryBinding;
import com.app.qlns.db.StaffDAO;
import com.app.qlns.listener.ModifyItem;
import com.app.qlns.listener.UpdateList;
import com.app.qlns.model.Staff;

public class GalleryFragment extends Fragment implements ModifyItem {
    private FragmentGalleryBinding binding;
    StaffDAO staffDAO;
    StaffAdapter adapter;
    List<Staff> listStaff = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        staffDAO = new StaffDAO(getContext());
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
        createRecyclerView();
        binding.btnAddStaff.setOnClickListener(v -> {
            StaffDialog dialog = new StaffDialog(getContext(), new UpdateList() {
                @Override
                public void onUpdate() {
                    createRecyclerView();
                }
            });
            dialog.show();
        });
    }

    void createRecyclerView() {
        listStaff = staffDAO.getAllStaff();
        Collections.reverse(listStaff);
        // code
        adapter = new StaffAdapter(this.getContext(), listStaff, this);
        binding.rvStaff.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.rvStaff.setAdapter(adapter);
        // code
    }

    @Override
    public void onEdit(int position) {
        createRecyclerView();
    }

    @Override
    public void onDelete(int position) {
        staffDAO.deleteStaff(listStaff.get(position).getId());
        createRecyclerView();
    }
}