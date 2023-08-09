package com.task.wifitask.ui.BaseF;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.task.wifitask.R;
import com.task.wifitask.Entity.WiFiInfo;
import com.task.wifitask.databinding.FragmentGalleryBinding;

public class BaseFragment extends Fragment {

    private Context context_;

    private FragmentGalleryBinding binding_;

    private BaseContract.View<WiFiInfo> view_;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding_ = FragmentGalleryBinding.inflate(inflater, container, false);

        new DecoratorBaseFragment(
                requireActivity().findViewById(R.id.toolbar),
                binding_.scrollView33,
                binding_.loadBase);

        view_ = new BaseView(binding_.containerBase, binding_.loadBase, context_);

        return binding_.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        context_ = context;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view_.onDestroy();
        binding_ = null;
    }
}