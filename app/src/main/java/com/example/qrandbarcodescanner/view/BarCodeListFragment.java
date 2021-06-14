package com.example.qrandbarcodescanner.view;

import android.app.FragmentBreadCrumbs;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qrandbarcodescanner.R;
import com.example.qrandbarcodescanner.adapters.BarCodeAdapter;
import com.example.qrandbarcodescanner.databinding.FragmentBarCodeListBinding;
import com.example.qrandbarcodescanner.models.BarCodeDataModel;
import com.example.qrandbarcodescanner.viewmodels.BarCodeViewModel;

import java.util.List;


public class BarCodeListFragment extends Fragment {
    FragmentBarCodeListBinding binding;
    BarCodeViewModel barCodeViewModel;
    BarCodeAdapter adapter;
    RecyclerView.LayoutManager layoutManager;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentBarCodeListBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        barCodeViewModel=new ViewModelProvider(requireActivity()).get(BarCodeViewModel.class);
        layoutManager = new LinearLayoutManager(getActivity());
        binding.barcodeRV.setLayoutManager(layoutManager);
        binding.barcodeRV.setHasFixedSize(true);
        barCodeViewModel.getBarCodeListLiveData().observe(getViewLifecycleOwner(), new Observer<List<BarCodeDataModel>>() {
            @Override
            public void onChanged(List<BarCodeDataModel> barCodeList) {
                    binding.noItemTV.setVisibility(View.GONE);
                    adapter=new BarCodeAdapter(barCodeList);
                    binding.barcodeRV.setAdapter(adapter);


            }
        });
    }
}