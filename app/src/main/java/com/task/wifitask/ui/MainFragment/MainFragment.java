package com.task.wifitask.ui.MainFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.task.wifitask.Entity.WiFiInfo;
import com.task.wifitask.databinding.FragmentHomeBinding;

public class MainFragment extends Fragment {

    private FragmentHomeBinding binding_;

    private Context context_;

    private MainContract.View<WiFiInfo> view_;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             @NonNull ViewGroup container,
                             @NonNull Bundle savedInstanceState) {

        binding_ = FragmentHomeBinding.inflate(inflater, container, false);

        view_ = new MainView(binding_.container,context_);

        return binding_.getRoot();
    }

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