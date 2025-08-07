package com.demo.androidmvparchitecture.base;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.demo.androidmvparchitecture.R;

public abstract class BaseFragment<DB extends ViewDataBinding> extends Fragment {

    protected DB binding;
    private AlertDialog loadingDialog;

    @LayoutRes
    protected abstract int getLayoutResId();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false);
        initializeLoadingDialog();
        return binding.getRoot();
    }

    private void initializeLoadingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(R.layout.dialog_custom_loading);
        builder.setCancelable(false);
        loadingDialog = builder.create();
    }

    protected void showLoading() {
        requireActivity().runOnUiThread(() -> {
        if (loadingDialog != null && !loadingDialog.isShowing()) {
            loadingDialog.show();
        }
        });
    }

    protected void hideLoading() {
        requireActivity().runOnUiThread(() -> {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
        });
    }

    public void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
