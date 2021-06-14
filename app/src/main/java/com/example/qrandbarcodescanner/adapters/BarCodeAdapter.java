package com.example.qrandbarcodescanner.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.qrandbarcodescanner.databinding.SingleBarcodeBinding;
import com.example.qrandbarcodescanner.models.BarCodeDataModel;
import java.util.List;

public class BarCodeAdapter extends RecyclerView.Adapter<BarCodeAdapter.BarCodeViewHolder> {
    List<BarCodeDataModel>barCodeList;

    public BarCodeAdapter(List<BarCodeDataModel> barCodeList) {
        this.barCodeList = barCodeList;
    }

    @NonNull
    @Override
    public BarCodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SingleBarcodeBinding binding=SingleBarcodeBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new BarCodeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BarCodeViewHolder holder, int position) {
        BarCodeDataModel data=barCodeList.get(position);
        holder.barcodeBinding.setBarCodeObj(data);

    }

    @Override
    public int getItemCount() {
        return barCodeList.size();
    }

    class BarCodeViewHolder extends RecyclerView.ViewHolder{
        SingleBarcodeBinding barcodeBinding;

        public BarCodeViewHolder(@NonNull SingleBarcodeBinding binding) {
            super(binding.getRoot());
            barcodeBinding=binding;
        }
    }
}
