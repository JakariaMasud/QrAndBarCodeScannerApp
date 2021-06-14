package com.example.qrandbarcodescanner.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.qrandbarcodescanner.models.BarCodeDataModel;
import com.example.qrandbarcodescanner.repositories.BarCodeRepository;

import java.util.List;

public class BarCodeViewModel extends ViewModel {
    BarCodeRepository repository;

    public BarCodeViewModel() {
        repository=new BarCodeRepository();
    }


    public void insertBarCodeData(BarCodeDataModel model){
        repository.insertBarCodeData(model);

    }
    public  LiveData<List<BarCodeDataModel>> getBarCodeListLiveData(){
        return  repository.getBarCodeListLiveData();
    }
    public LiveData<Boolean>getInsertProcessLiveData(){
        return  repository.getInsertProcessLiveData();
    }

}
