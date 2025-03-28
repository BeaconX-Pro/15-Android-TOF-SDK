package com.moko.tofsensortest.view;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.moko.tofsensortest.R;
import com.moko.tofsensortest.databinding.TofDialogLoadingBinding;

import androidx.core.content.ContextCompat;

public class LoadingDialog extends MokoBaseDialog<TofDialogLoadingBinding> {
    public static final String TAG = LoadingDialog.class.getSimpleName();

    @Override
    protected TofDialogLoadingBinding getViewBind(LayoutInflater inflater, ViewGroup container) {
        return TofDialogLoadingBinding.inflate(inflater, container, false);
    }

    @Override
    protected void onCreateView() {
        ProgressDrawable progressDrawable = new ProgressDrawable();
        progressDrawable.setColor(ContextCompat.getColor(getContext(), R.color.text_black_4d4d4d));
        mBind.ivLoading.setImageDrawable(progressDrawable);
        progressDrawable.start();
    }

    @Override
    public int getDialogStyle() {
        return R.style.CenterDialog;
    }

    @Override
    public int getGravity() {
        return Gravity.CENTER;
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public float getDimAmount() {
        return 0.7f;
    }

    @Override
    public boolean getCancelOutside() {
        return false;
    }

    @Override
    public boolean getCancellable() {
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((ProgressDrawable) mBind.ivLoading.getDrawable()).stop();
    }
}
