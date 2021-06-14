package com.example.qrandbarcodescanner.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.qrandbarcodescanner.SingleLiveEvent;
import com.example.qrandbarcodescanner.models.BarCodeDataModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.installations.FirebaseInstallations;


import java.util.ArrayList;
import java.util.List;

public class BarCodeRepository {
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private  MutableLiveData<List<BarCodeDataModel>> barCodeListLiveData=new MutableLiveData<>();
    private  SingleLiveEvent<Boolean>insertProcessLiveData=new SingleLiveEvent<Boolean>();

    public LiveData<List<BarCodeDataModel>> getBarCodeListLiveData() {
        return barCodeListLiveData;
    }

    public LiveData<Boolean> getInsertProcessLiveData() {
        return insertProcessLiveData;
    }

    public BarCodeRepository() {
        subscribeForBarCodeList();
    }

    private void subscribeForBarCodeList() {
        FirebaseInstallations.getInstance().getId().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if(task.isSuccessful()){
                    //got the installation ID
                    database.child("users").child(task.getResult()).child("BarcodeList").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                List<BarCodeDataModel>dataList=new ArrayList<>();
                                for(DataSnapshot barCodeSnapshot:snapshot.getChildren()){
                                    BarCodeDataModel data=barCodeSnapshot.getValue(BarCodeDataModel.class);
                                    dataList.add(data);
                                }
                                barCodeListLiveData.postValue(dataList);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else{
                    //failed to retrieve installationId
                }
            }
        });
    }

    public  void insertBarCodeData(BarCodeDataModel barCodeData){
        FirebaseInstallations.getInstance().getId()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (task.isSuccessful()) {
                            Log.d("Installations", "Installation ID: " + task.getResult());
                            database.child("users").child(task.getResult()).child("BarcodeList").push().setValue(barCodeData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        insertProcessLiveData.postValue(true);

                                    }else {
                                        insertProcessLiveData.postValue(false);
                                    }
                                }
                            });
                        } else {
                            Log.e("Installations", "Unable to get Installation ID");
                        }
                    }
                });

    }

}
