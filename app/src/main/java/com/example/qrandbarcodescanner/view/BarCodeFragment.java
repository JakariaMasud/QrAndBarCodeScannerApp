package com.example.qrandbarcodescanner.view;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.qrandbarcodescanner.Utility;
import com.example.qrandbarcodescanner.databinding.FragmentBarCodeBinding;
import com.example.qrandbarcodescanner.models.BarCodeDataModel;
import com.example.qrandbarcodescanner.viewmodels.BarCodeViewModel;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;


public class BarCodeFragment extends Fragment {
    FragmentBarCodeBinding barCodeBinding;
    IntentIntegrator integrator;
    BarCodeViewModel barCodeViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        barCodeBinding = FragmentBarCodeBinding.inflate(inflater, container, false);
        return barCodeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        barCodeViewModel = new ViewModelProvider(requireActivity()).get(BarCodeViewModel.class);
        barCodeBinding.scanBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                integrator = IntentIntegrator.forSupportFragment(BarCodeFragment.this);
                integrator.setBeepEnabled(true);
                integrator.setOrientationLocked(true);
                integrator.setCaptureActivity(Capture.class);
                checkPermission();

            }
        });
        barCodeViewModel.getInsertProcessLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSuccessful) {
                if (isSuccessful) {
                    Toast.makeText(getContext(), "bar code Data saved successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Failed to save Bar code Data", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result.getContents() != null) {
            Toast.makeText(getContext(), "bar code is being Saved into Remote Database", Toast.LENGTH_SHORT).show();
            long currentTime = System.currentTimeMillis();
            String dateTimeString = Utility.getDataTimeFromLong(currentTime);
            barCodeViewModel.insertBarCodeData(new BarCodeDataModel(result.getContents(), dateTimeString));


        } else {

            Toast.makeText(getContext(), "you did not scan anything", Toast.LENGTH_SHORT).show();
        }

    }

    private void checkPermission() {
        Dexter.withContext(getContext())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Log.e("info", "Permission granted called");
                        integrator.initiateScan();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);

                    }

                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        new AlertDialog.Builder(getContext()).setTitle("You need Permission")
                                .setMessage("Please allow this permission to Use Bar Code feature")
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        permissionToken.continuePermissionRequest();
                                        dialog.dismiss();
                                    }
                                })
                                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        permissionToken.cancelPermissionRequest();
                                        dialog.dismiss();
                                    }
                                })
                                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {
                                        permissionToken.cancelPermissionRequest();

                                    }
                                }).show();

                    }
                }).check();
    }
}
